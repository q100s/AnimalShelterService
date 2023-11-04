package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.Request;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.RequestRepository;
import pro.sky.animalizer.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, уведомляемый о событии. <br>
 * Он должен быть зарегистрирован источником событий
 * и реализовывать методы для получения и обработки уведомлений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final UserService userService;
    private final RequestRepository requestRepository;
    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(UserService userService, RequestRepository requestRepository, TelegramBot telegramBot) {
        this.userService = userService;
        this.requestRepository = requestRepository;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                Message message = update.message();
                LocalDateTime dateTime = LocalDateTime.now(); // получаем текущую дату
                String text;
                Long chatId;
                String fullName= null;
                String phoneNumber= null;
                logger.info("Processing update: {}", update);
                if (update.message() != null) {
                    text = message.text();
                    chatId = message.chat().id();
                } else if (update.callbackQuery() != null) {
                    text = update.callbackQuery().data();
                    chatId = update.callbackQuery().message().chat().id();
                } else {
                    return;
                }
                if ("/start".equalsIgnoreCase(text)) {

                    /*Выполняется проверка предыдущих обращений пользователя к боту.
                    Первый визит (еще нет chatId в таблице БД Request)- регистрация пользователя
                    в таблице Users по никнейму телеграм и номер телефона,если не скрыт
                    (иначе номер телефона "00000000000").

                    ЕЩЕ РАЗ НАЖИМАЕШЬ /start

                    Повторный визит(есть chatId в таблице БД Request) - кнопки с выбором приюта.

                     */
                     if(requestRepository.findAllRequestsByChatId(chatId).stream()
                             .map(Request::getChatId).collect(Collectors.toCollection(ArrayList::new)).contains(chatId)){

                         getMenuWithShelterPicking(chatId);
                     }else {
                         fullName = update.message().chat().username();
                         if(update.message().contact()==null){
                             phoneNumber = "00000000000";
                         }else{
                             phoneNumber = update.message().contact().phoneNumber();
                         }
                         requestRepository.save(new Request (chatId,dateTime,message.toString()));
                         userService.createUser(new User(fullName,phoneNumber));
                     }
                }
                createClickOnShelterPickingButton(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод, генерирующий приветственное сообщение и меню c выбором приюта для нового пользователя.
     * #{@link TelegramBotUpdatesListener#createMenuWithShelterPicking()}. <br>
     * #{@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuWithShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Hello! Pick, please, the shelter!");
        sendMessage.replyMarkup(createMenuWithShelterPicking());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий клавиатуру для выбора приюта.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMenuWithShelterPicking() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Cat's shelter").callbackData("cat's shelter"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Dog's shelter").callbackData("dog's shelter"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, обрабатывающий результаты нажатия на кнопки меню выбора приюта. <br>
     * #{@link TelegramBotUpdatesListener#getMenuAfterCatsShelterPicking(Long)} <br>
     * #{@link TelegramBotUpdatesListener#getMenuAfterDogsShelterPicking(Long)} <br>
     * #{@link TelegramBotUpdatesListener#createClickOnShelterMenu(Update)} <br>
     */
    private void createClickOnShelterPickingButton(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            String data = callbackQuery.data();
            switch (data) {
                case "cat's shelter":
                    getMenuAfterCatsShelterPicking(update.callbackQuery().from().id());
                    break;
                case "dog's shelter":
                    getMenuAfterDogsShelterPicking(update.callbackQuery().from().id());
                    break;
            }
            createClickOnShelterMenu(update);
        }
    }

    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для кошек.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMenuAfterCatsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get shelter's info").callbackData("shelter's info"),
                new InlineKeyboardButton("How to adopt a cat").callbackData("cat adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Send a report").callbackData("report sending"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для собак.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMenuAfterDogsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get shelter's info").callbackData("shelter's info"),
                new InlineKeyboardButton("How to adopt a dog").callbackData("dog adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Send a report").callbackData("report sending"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий меню c выбором действий для приюта для кошек.
     * #{@link TelegramBotUpdatesListener#createMenuAfterCatsShelterPick()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuAfterCatsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "You've picked cat's shelter. Pick the action, please:");
        sendMessage.replyMarkup(createMenuAfterCatsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для приюта для собак.
     * #{@link TelegramBotUpdatesListener#createMenuAfterDogsShelterPick()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuAfterDogsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "You've picked dog's shelter. Pick the action, please:");
        sendMessage.replyMarkup(createMenuAfterDogsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }




    /**
     * Метод, обрабатывающий резултаты нажатия на кнопки внутри меню приютов. <br>
     * На данный момент результаты нажатия заглушены. <br>
     */
    private void createClickOnShelterMenu(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            String data = callbackQuery.data();
            switch (data) {
                case "shelter's info":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "ShelterInfo"));
                    break;
                case "cat adoption info":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "CatAdoptionInfo"));
                    break;
                case "dog adoption info":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "DogAdoptionInfo"));
                    break;
                case "report sending":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Report taker"));
                    break;
                case "volunteer calling":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Volunteer caller"));
                    break;
            }
        }
    }
}