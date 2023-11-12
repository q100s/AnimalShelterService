package pro.sky.animalizer.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.User;

/**
 * Класс для обработки запросов от пользователей.
 */
@Component
public class UserRequestService {
    private final Logger logger = LoggerFactory.getLogger(UserRequestService.class);
    private final InlineKeyboardMarkupService inlineKeyboardMarkupService;
    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final UserService userService;

    public UserRequestService(InlineKeyboardMarkupService inlineKeyboardMarkupService,
                              TelegramBot telegramBot,
                              ShelterService shelterService, UserService userService) {
        this.inlineKeyboardMarkupService = inlineKeyboardMarkupService;
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.userService = userService;
    }

    /**
     * Метод, генерирующий приветственное сообщение и меню c выбором приюта для нового пользователя.<br>
     * #{@link InlineKeyboardMarkupService#createMenuWithShelterPicking()}. <br>
     * #{@link TelegramBot#execute(BaseRequest)}.
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuWithShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Пожалуйста, выбери интересующий тебя приют:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithShelterPicking());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для приюта для кошек.<br>
     * #{@link InlineKeyboardMarkupService#createMenuAfterCatsShelterPick()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuAfterCatsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Ты выбрал приют для кошек.\n" +
                        "Пожалуйста, выбери интересующую тебя опцию:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuAfterCatsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для приюта для собак.<br>
     * #{@link InlineKeyboardMarkupService#createMenuAfterDogsShelterPick()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuAfterDogsShelterPicking(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Ты выбрал приют для собак.\n" +
                        "Пожалуйста, выбери интересующую тебя опцию:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuAfterDogsShelterPick());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для получения информации о приюте для кошек.<br>
     * #{@link InlineKeyboardMarkupService#createMenuWithCatsShelterOption()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuWithCatsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Что именно ты хочешь узнать: ");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithCatsShelterOption());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для получения информации о приюте для собак.<br>
     * #{@link InlineKeyboardMarkupService#createMenuWithDogsShelterOptions()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuWithDogsShelterOptions(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Что именно ты хочешь узнать: ");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithDogsShelterOptions());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для получения информации об усыновлении собаки.<br>
     * #{@link InlineKeyboardMarkupService#createMenuWithDogsAdoptionInfo()} <br>
     * #{@link TelegramBot#execute(BaseRequest)}<br>
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuWithDogsAdoptionInfo(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Ниже представлена ионформация для усыновления собаки: ");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithDogsAdoptionInfo());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, генерирующий меню c выбором действий для получения информации об усыновлении кошки.
     * #{@link InlineKeyboardMarkupService#createMenuWithCatsAdoptionInfo()} )} <br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param chatId идентификатор чата, для которого генерируется меню.
     */
    public void getMenuWithCatsAdoptionInfo(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Ниже представлена информация для усыновления кошки: ");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithCatsAdoptionInfo());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод, обновляющий поля пользователя в базе данных.<br>
     *
     * #{@link UserService#findUserByTelegramId(Long)}<br>
     * #{@link UserService#editUser(Long, User)}<br>
     * #{@link UserService#createUser(User)}<br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param update апдейт, приходящий из telegram чата с пользователем.
     * @param fullName имя и фамилия пользователя (вычисляется из базы данных, либо передаётся пользователем).
     * @param phoneNumber мобильный телефон пользователя (вычисляется из базы данных, либо передаётся пользователем).
     */
    public void updateUser(Update update, String fullName, String phoneNumber) {
        Message message = update.message();
        long chatId = message.chat().id();
        long telegramId = message.from().id();
        String telegramNick = message.from().username();
        User userByTelegramId = userService.findUserByTelegramId(telegramId);
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

    /**
     * Метод, обрабатывающий резултаты нажатия на кнопки меню.
     */
    public void createButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            String data = callbackQuery.data();
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
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSchedule()));
                    break;
                case "dog's shelter schedule":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSchedule()));
                    break;
                case "cat's shelter address":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getAddress()));
                    break;
                case "dog's shelter address":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getAddress()));
                    break;
                case "cat's direction path":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getDirectionPathFile()));
                    break;
                case "dog's direction path":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getDirectionPathFile()));
                    break;
                case "cat's security contact":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSecurityPhoneNumber()));
                    break;
                case "dog's security contact":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSecurityPhoneNumber()));
                    break;
                case "cat's safety measures":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("cat")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSafetyMeasures()));
                    break;
                case "dog's safety measures":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            shelterService.getAllShelters().stream()
                                    .filter(shelter -> shelter.getShelterType().equals("dog")).findFirst()
                                    .orElseThrow(ShelterNotFoundException::new).getSafetyMeasures()));
                    break;
                case "get personal info":
                    telegramBot.execute(
                            new SendMessage(chatId,
                                    "Напишите через пробел свое имя, фамилию и номер телефона с кодом страны (без плюса)")
                    );
                    break;
                case "усыновление кошки":
                    getMenuWithCatsAdoptionInfo(chatId);
                    break;
                case "усыновление собаки":
                    getMenuWithDogsAdoptionInfo(chatId);
                    break;
                case "report sending":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), "Report taker"));
                    break;
                case "volunteer calling":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "Напиши сообщение волонтеру"));
                    break;
                case "правила знакомства с собакой":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "правила знакомства с собакой"));
                    break;
                case "документы для усыновления собаки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "документы для усыновления собаки"));
                    break;
                case "транспортировка собаки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "транспортировка собаки"));
                    break;
                case "дома для щенка":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "дома для щенка"));
                    break;
                case "дом для взрослой собаки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "дом для взрослой собаки"));
                    break;
                case "дом для собаки с изъянами":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "дом для собаки с изъянами"));
                    break;
                case "первичное общение с собакой":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "первичное общение с собакой"));
                    break;
                case "проверенные кинологи":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "проверенные кинологи"));
                    break;
                case "причины отказа в усыновлении собы":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "причины отказа в усыновлении собы"));
                    break;
                case "правила знакомства с кошкой":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "правила знакомства с кошкой"));
                    break;
                case "документы для усыновления кошки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "документы для усыновления кошки"));
                    break;
                case "транспортировка кошки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "транспортировка кошки"));
                    break;
                case "обустройство дома для котенка":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "обустройство дома для котенка"));
                    break;
                case "дом для взрослой кошки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "дом для взрослой кошки"));
                    break;
                case "дом для кошки с изъянами":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "дом для кошки с изъянами"));
                    break;
                case "причины отказа в усыновлении кошки":
                    telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),
                            "причины отказа в усыновлении кошки"));
                    break;

            }
        }
    }
}
