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
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.ShelterService;
import pro.sky.animalizer.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, уведомляемый о событии. <br>
 * Он должен быть зарегистрирован источником событий
 * и реализовывать методы для получения и обработки уведомлений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Pattern pattern = Pattern.compile("(^[А-я]+)\\s+([А-я]+)\\s+(\\d{11}$)");
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final ShelterService shelterService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UserService userService, ShelterService shelterService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.shelterService = shelterService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                if (update.message() != null) {
                    sendStartMessage(update);
                }
                createButtonClick(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMessage(Update update) {
        Message message = update.message();
        Long chatId = message.from().id();
        String text = message.text();
        String firstName = update.message().from().firstName();
        String userName = update.message().from().username();
        long telegramId = update.message().from().id();
        if ("/start".equalsIgnoreCase(text)) {
            User user = userService.findUserByTelegramId(telegramId);
            if (user == null) {
                telegramBot.execute(
                        new SendMessage(chatId, "Привет, " + firstName + "! Приветствую тебя в меню приюта для животных!")
                );
                User newUser = new User(telegramId, userName);
                userService.createUser(newUser);
                getMenuWithShelterPicking(chatId);
            } else {
                telegramBot.execute(new SendMessage(chatId, "Рад видеть тебя снова, " + firstName));
                getMenuWithShelterPicking(chatId);
            }
        } else {
            updateUser(update);
        }
    }

    /**
     * Метод, обрабатывающий резултаты нажатия на кнопки меню.
     */
    private void createButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            String data = callbackQuery.data();
            Shelter catsShelter = shelterService.getAllShelters().stream()
                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                    .orElseThrow(ShelterNotFoundException::new);
            Shelter dogsShelter = shelterService.getAllShelters().stream()
                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                    .orElseThrow(ShelterNotFoundException::new);
            switch (data) {
                case "cat's shelter":
                    getMenuAfterCatsShelterPicking(chatId);
                    break;
                case "dog's shelter":
                    getMenuAfterDogsShelterPicking(chatId);
                    break;
                case "cat's shelter info":
                    getMenuWithCatsShelterOptions(chatId);
                    break;
                case "dog's shelter info":
                    getMenuWithDogsShelterOptions(chatId);
                    break;
                case "общая информация о кошачем приюте":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Some cat's info"));
                    break;
                case "общая информация о собачем приюте":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Some dog's info"));
                    break;
                case "cat's shelter schedule":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSchedule()));
                    break;
                case "dog's shelter schedule":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSchedule()));
                    break;
                case "cat's shelter address":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getAddress()));
                    break;
                case "dog's shelter address":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getAddress()));
                    break;
                case "cat's direction path":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getDirectionPathFile()));
                    break;
                case "dog's direction path":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getDirectionPathFile()));
                    break;
                case "cat's security contact":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSecurityPhoneNumber()));
                    break;
                case "dog's security contact":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSecurityPhoneNumber()));
                    break;
                case "cat's safety measures":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), catsShelter.getSafetyMeasures()));
                    break;
                case "dog's safety measures":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dogsShelter.getSafetyMeasures()));
                    break;
                case "get personal info":
                    telegramBot.execute(
                            new SendMessage(chatId,
                                    "Напишите через пробел свое имя, фамилию и номер телефона с кодом страны (без плюса)")
                    );
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
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "Напиши сообщение волонтеру"));
                    break;
            }
        }
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
                new SendMessage(chatId, "Пожалуйста, выбери интересующий тебя приют:");
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
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для кошек").callbackData("cat's shelter"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для собак").callbackData("dog's shelter"));
        return inlineKeyboardMarkup;
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
                new SendMessage(chatId, "Ты выбрал приют для собак.\n" + "Пожалуйста, выбери интересующую тебя опцию:");
        sendMessage.replyMarkup(createMenuAfterDogsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
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
                new InlineKeyboardButton("Информация о приюте").callbackData("cat's shelter info"),
                new InlineKeyboardButton("Как усыновить кошку").callbackData("cat adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Отправить отчет о питомце").callbackData("report sending"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
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
                new SendMessage(chatId, "Ты выбрал приют для кошек.\n" + "Пожалуйста, выбери интересующую тебя опцию:");
        sendMessage.replyMarkup(createMenuAfterCatsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
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
                new InlineKeyboardButton("Информация о приюте").callbackData("dog's shelter info"),
                new InlineKeyboardButton("Как усыновить собаку").callbackData("dog adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Отправить отчет о питомце").callbackData("report sending"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    private void getMenuWithCatsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Что именно ты хочешь узнать: ");
        sendMessage.replyMarkup(createMenuWithCatsShelterOption());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private InlineKeyboardMarkup createMenuWithCatsShelterOption() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("О приюте").callbackData("общая информация о кошачем приюте"),
                new InlineKeyboardButton("Расписание работы приюта").callbackData("cat's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Адрес приюта").callbackData("cat's shelter address"),
                new InlineKeyboardButton("Схема проезда").callbackData("cat's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Телефон охраны").callbackData("cat's security contact"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные").callbackData("get personal info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Техника безопасности на территории приюта").callbackData("cat's safety measures"));
        return inlineKeyboardMarkup;
    }

    private void getMenuWithDogsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Что именно ты хочешь узнать: ");
        sendMessage.replyMarkup(createMenuWithDogsShelterOptions());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private InlineKeyboardMarkup createMenuWithDogsShelterOptions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("О приюте").callbackData("общая информация о собачем приюте"),
                new InlineKeyboardButton("Расписание работы приюта").callbackData("dog's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Адрес приюта").callbackData("dog's shelter address"),
                new InlineKeyboardButton("Схема проезда").callbackData("dog's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Телефон охраны").callbackData("cat's security contact"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные").callbackData("get personal info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Техника безопасности на территории приюта").callbackData("cat's safety measures"));
        return inlineKeyboardMarkup;
    }

    private void updateUser(Update update) {
        Message message = update.message();
        long chatId = message.chat().id();
        String text = message.text();
        long telegramId = message.from().id();
        String telegramNick = message.from().username();
        String fullName;
        String phoneNumber;
        Matcher matcher = pattern.matcher(text);
        User userByTelegramId = userService.findUserByTelegramId(telegramId);
        if (!matcher.find()) {
            telegramBot.execute(new SendMessage(chatId, "Incorrect output"));
        }
        fullName = matcher.group(1) + " " + matcher.group(2);
        phoneNumber = matcher.group(3);
        if (userByTelegramId != null) {
            Long userId = userByTelegramId.getId();
            User updatedUser = new User(telegramId, telegramNick, fullName, phoneNumber);
            userService.editUser(userId, updatedUser);
            telegramBot.execute(new SendMessage(chatId, updatedUser.toString()));
        } else {
            User newUser = new User(telegramId, telegramNick, fullName, phoneNumber);
            userService.createUser(newUser);
            telegramBot.execute(new SendMessage(chatId, newUser.toString()));
        }
    }
}