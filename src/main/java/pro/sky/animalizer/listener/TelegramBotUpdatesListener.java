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
    private final Pattern pattern = Pattern.compile("(^[А-я]+)\\s+(\\d{11})");
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
            telegramBot.execute(new SendMessage(chatId, "Hello, " + firstName + "! Greetings in animal shelter bot menu!"));
            getMenuWithShelterPicking(chatId);
//            User user = userService.findUserByTelegramId(telegramId);
//            if (user == null) {
//                telegramBot.execute(new SendMessage(chatId, "Hello, " + firstName + "! Greetings in animal shelter bot menu!"));
//                User newUser = new User(telegramId, userName);
//                userService.createUser(newUser);
//                getMenuWithShelterPicking(chatId);
//            } else {
//                telegramBot.execute(new SendMessage(chatId, "Nice to see you again, " + firstName));
//                getMenuWithShelterPicking(chatId);
//            }
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
                case "cat's shelter information":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Some cat's info"));
                    break;
                case "dog's shelter information":
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
                    telegramBot.execute(new SendMessage(chatId, "Send your name and phone number with country code (without plus), please"));
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

    /**
     * Метод, генерирующий приветственное сообщение и меню c выбором приюта для нового пользователя.
     * #{@link TelegramBotUpdatesListener#createMenuWithShelterPicking()}. <br>
     * #{@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    private void getMenuWithShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Pick, please, the shelter!");
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
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для кошек.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMenuAfterCatsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's shelter info").callbackData("cat's shelter info"),
                new InlineKeyboardButton("How to adopt a cat").callbackData("cat adoption info"));
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
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для собак.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMenuAfterDogsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's shelter info").callbackData("dog's shelter info"),
                new InlineKeyboardButton("How to adopt a dog").callbackData("dog adoption info"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Send a report").callbackData("report sending"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    private void getMenuWithCatsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Pick the cat's shelter option, please: ");
        sendMessage.replyMarkup(createMenuWithCatsShelterOption());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private InlineKeyboardMarkup createMenuWithCatsShelterOption() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get general cat's shelter information").callbackData("cat's shelter information"),
                new InlineKeyboardButton("Get cat's shelter schedule").callbackData("cat's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's shelter address").callbackData("cat's shelter address"),
                new InlineKeyboardButton("Get cat's shelter direction path").callbackData("cat's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get cat's security contact").callbackData("cat's security contact"),
                new InlineKeyboardButton("Get cat's safety measures").callbackData("cat's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Give personal info").callbackData("get personal info"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    private void getMenuWithDogsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Pick the dog's shelter option, please: ");
        sendMessage.replyMarkup(createMenuWithDogsShelterOptions());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    private InlineKeyboardMarkup createMenuWithDogsShelterOptions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get general dog's shelter information").callbackData("dog's shelter information"),
                new InlineKeyboardButton("Get dog's shelter schedule").callbackData("dog's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's shelter address").callbackData("dog's shelter address"),
                new InlineKeyboardButton("Get dog's shelter direction path").callbackData("dog's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Get dog's security contact").callbackData("dog's security contact"),
                new InlineKeyboardButton("Get dog's safety measures").callbackData("dog's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Give personal info").callbackData("get personal info"),
                new InlineKeyboardButton("Call the volunteer").callbackData("volunteer calling"));
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
        if (text != null) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                fullName = matcher.group(1);
                phoneNumber = matcher.group(2);
                User user = new User(telegramId, telegramNick, fullName, phoneNumber);
//                userService.createUser(user);
                telegramBot.execute(new SendMessage(chatId, user.toString()));
            } else {
                telegramBot.execute(new SendMessage(chatId, "Incorrect output"));
            }
        }
    }
}