package pro.sky.animalizer.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для обработки запросов от пользователей.
 */
@Component
public class UserRequestService {
    private final Logger logger = LoggerFactory.getLogger(UserRequestService.class);
    private Map<Long, Boolean> reportStateByChatId = new HashMap<>();
    private Map<Long, Boolean> updateUserInfoStateByChatId = new HashMap<>();
    private Map<Long, Boolean> questionToVolunteerStateByChatId = new HashMap<>();
    private final Pattern pattern = Pattern.compile("(^[А-я]+)\\s+([А-я]+)\\s+(\\d{11}$)");
    private final InlineKeyboardMarkupService inlineKeyboardMarkupService;
    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final UserService userService;
    private final ReportService reportService;
    private final RequestService requestService;
    private final PetService petService;

    public UserRequestService(InlineKeyboardMarkupService inlineKeyboardMarkupService,
                              TelegramBot telegramBot,
                              ShelterService shelterService,
                              UserService userService,
                              ReportService reportService,
                              RequestService requestService,
                              PetService petService) {
        this.inlineKeyboardMarkupService = inlineKeyboardMarkupService;
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.userService = userService;
        this.reportService = reportService;
        this.requestService = requestService;
        this.petService = petService;
    }

    public void sendMessageWithResult(Update update) {
        Message message = update.message();
        Long chatId = message.chat().id();
        String firstName = update.message().from().firstName();
        String userName = update.message().from().username();
        long telegramId = message.from().id();
        if (Boolean.TRUE.equals(updateUserInfoStateByChatId.get(chatId))) {
            updateUser(update);
        } else if (Boolean.TRUE.equals(reportStateByChatId.get(chatId))) {
            getReportFromUser(update);
        } else if (Boolean.TRUE.equals(questionToVolunteerStateByChatId.get(chatId))) {
            sendMessageWithQuestionToVolunteer(update);
        } else if ("/start".equalsIgnoreCase(message.text())) {
            User user = userService.findUserByTelegramId(telegramId);
            if (user == null) {
                telegramBot.execute(new SendMessage(chatId, "Добро пожаловать в меню приюта для животных"));
                User newUser = new User(telegramId, userName);
                userService.createUser(newUser);
                getMenuWithShelterPicking(chatId);
            } else {
                telegramBot.execute(new SendMessage(chatId, "Рад видеть тебя снова, " + firstName));
                getMenuWithShelterPicking(chatId);
            }
        }
    }

    /**
     * Метод, обновляющий поля пользователя в базе данных.<br>
     * <p>
     * #{@link UserService#findUserByTelegramId(Long)}<br>
     * #{@link UserService#editUser(Long, User)}<br>
     * #{@link UserService#createUser(User)}<br>
     * #{@link TelegramBot#execute(BaseRequest)}
     *
     * @param update апдейт, приходящий из telegram чата с пользователем.
     */

    public void updateUser(Update update) {
        Message message = update.message();
        Matcher matcher = pattern.matcher(message.text());
        long chatId = message.chat().id();
        long telegramId = message.from().id();
        String telegramNick = message.from().username();
        if (matcher.find()) {
            String fullName = matcher.group(1) + " " + matcher.group(2);
            String phoneNumber = matcher.group(3);
            User userByTelegramId = userService.findUserByTelegramId(telegramId);
            if (userByTelegramId != null) {
                Long userId = userByTelegramId.getId();
                User updatedUser = new User(telegramId, telegramNick, fullName, phoneNumber, UserType.DEFAULT);
                userService.editUser(userId, updatedUser);
                telegramBot.execute(new SendMessage(chatId, "Ваши данные успешно сохранены"));
            } else {
                User newUser = new User(telegramId, telegramNick, fullName, phoneNumber, UserType.DEFAULT);
                userService.createUser(newUser);
                telegramBot.execute(new SendMessage(chatId, "Ваши данные успешно сохранены"));
            }
            updateUserInfoStateByChatId.remove(chatId);
        } else {
            telegramBot.execute(new SendMessage(chatId, "Некорректный формат ввода! Попробуй ещё раз"));
        }
    }

    /**
     * Метод, принимающий от пользователя отчет и сохраняющий его в базе данных.
     * <p>
     * #{@link TelegramBot#execute(BaseRequest)} <br>
     * #{@link ReportService#createReport(Report)} <br>
     *
     * @param update апдейт, приходящий из telegram чата с пользователем.
     */
    public void getReportFromUser(Update update) {
        Long chatId = update.message().chat().id();
        Long telegramId = update.message().from().id();
        LocalDate reportDate = LocalDate.now();
        if (update.message().caption() == null || update.message().photo() == null) {
            SendMessage message = new SendMessage(
                    chatId, "Некорректный формат отчета! Попробуй ещё раз");
            telegramBot.execute(message);
        } else {
            String reportText = update.message().caption();
            GetFile getFile = new GetFile(update.message().photo()[update.message().photo().length - 1].fileId());
            GetFileResponse response = telegramBot.execute(getFile);
            String imageUrl = telegramBot.getFullFilePath(response.file());
            Report newReport = new Report(reportDate, imageUrl, reportText, telegramId);
            SendMessage message = new SendMessage(
                    chatId, "Спасибо за отчёт, результат проверки узнаете в течение дня!");
            telegramBot.execute(message);
            reportService.createReport(newReport);
            reportStateByChatId.remove(chatId);
        }
    }

    /**
     * Метод, сохраняющий вопрос от пользователя в базу данных и отправляющий его в чат волонтеру. <br>
     * <p>
     * #{@link TelegramBot#execute(BaseRequest)} <br>
     * #{@link RequestService#saveRequest(Request)} <br>
     * #{@link UserService#getAllUsers()} <br>
     * #{@link User#getUserType()} <br>
     * #{@link User#getTelegramId()}
     *
     * @param update апдейт, приходящий из telegram чата с пользователем.
     */
    public void sendMessageWithQuestionToVolunteer(Update update) {
        Long chatId = update.message().chat().id();
        Long telegramId = update.message().from().id();
        String requestText = update.message().text();
        Request request = new Request(chatId, telegramId, requestText);
        requestService.saveRequest(request);
        telegramBot.execute(new SendMessage(chatId, "Твоё обращение передано волонтеру"));
        questionToVolunteerStateByChatId.remove(chatId);
        String textForVolunteer = "Пользователь с телеграм-id " + request.getTelegramId() + " обратился с вопросом: \n"
                + request.getRequestText();
        Long volunteerChatId = userService.getAllUsers().stream()
                .filter(user -> user.getUserType().equals(UserType.VOLUNTEER))
                .findAny().orElseThrow(UserNotFoundException::new)
                .getTelegramId();
        telegramBot.execute(new SendMessage(volunteerChatId, textForVolunteer));
    }

    /**
     * Метод, обрабатывающий резултаты нажатия на кнопки меню.
     *
     * @param update апдейт, приходящий из telegram чата с пользователем.
     */
    public void createButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            long telegramId = callbackQuery.message().from().id();
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
                    telegramBot.execute(new SendMessage(chatId,
                            "Напишите через пробел свое имя, фамилию и номер телефона с кодом страны (без плюса)"));
                    updateUserInfoStateByChatId.put(chatId, true);
                    break;
                case "усыновление кошки":
                    getMenuWithCatsAdoptionInfo(chatId);
                    break;
                case "усыновление собаки":
                    getMenuWithDogsAdoptionInfo(chatId);
                    break;
                case "report sending":
                    telegramBot.execute(new SendMessage(chatId, """
                            Отправь фотографию животного с информацией в ПОДПИСИ к фотографии. В информации должно быть:
                            - Рацион животного;
                            - Общее самочувствие и привыкание к новому месту;
                            - Изменение в поведении: отказ от старых привычек, приобретение новых."""));
                    reportStateByChatId.put(chatId, true);
                    break;
                case "volunteer calling":
                    telegramBot.execute(new SendMessage(chatId, "Напиши сообщение волонтеру в свободной форме"));
                    questionToVolunteerStateByChatId.put(chatId, true);
                    break;
                case "правила знакомства с собакой":
                    telegramBot.execute(new SendMessage(chatId, "правила знакомства с собакой"));
                    break;
                case "документы для усыновления собаки":
                    telegramBot.execute(new SendMessage(chatId, "документы для усыновления собаки"));
                    break;
                case "транспортировка собаки":
                    telegramBot.execute(new SendMessage(chatId, "транспортировка собаки"));
                    break;
                case "дома для щенка":
                    telegramBot.execute(new SendMessage(chatId, "дом для щенка"));
                    break;
                case "дом для взрослой собаки":
                    telegramBot.execute(new SendMessage(chatId, "дом для взрослой собаки"));
                    break;
                case "дом для собаки с изъянами":
                    telegramBot.execute(new SendMessage(chatId, "дом для собаки с изъянами"));
                    break;
                case "первичное общение с собакой":
                    telegramBot.execute(new SendMessage(chatId, "первичное общение с собакой"));
                    break;
                case "проверенные кинологи":
                    telegramBot.execute(new SendMessage(chatId, "проверенные кинологи"));
                    break;
                case "причины отказа в усыновлении собы":
                    telegramBot.execute(new SendMessage(chatId, "причины отказа в усыновлении собы"));
                    break;
                case "правила знакомства с кошкой":
                    telegramBot.execute(new SendMessage(chatId, "правила знакомства с кошкой"));
                    break;
                case "документы для усыновления кошки":
                    telegramBot.execute(new SendMessage(chatId, "документы для усыновления кошки"));
                    break;
                case "транспортировка кошки":
                    telegramBot.execute(new SendMessage(chatId, "транспортировка кошки"));
                    break;
                case "обустройство дома для котенка":
                    telegramBot.execute(new SendMessage(chatId, "обустройство дома для котенка"));
                    break;
                case "дом для взрослой кошки":
                    telegramBot.execute(new SendMessage(chatId, "дом для взрослой кошки"));
                    break;
                case "дом для кошки с изъянами":
                    telegramBot.execute(new SendMessage(chatId, "дом для кошки с изъянами"));
                    break;
                case "причины отказа в усыновлении кошки":
                    telegramBot.execute(new SendMessage(chatId, "причины отказа в усыновлении кошки"));
                    break;
                case "все котики":
                    getMenuWithCats(chatId);
                    break;
                case "все собаки":
                    getMenuWithDogs(chatId);
                    break;
            }
            Collection<Pet> pets = petService.getAllPetsWithoutAdopter();
            for (Pet pet : pets) {
                if (data.equals(pet.getPetName())) {
                    String photoUrlPath = pet.getPhotoUrlPath();
                    if (photoUrlPath != null) {
                        SendPhoto sendPhoto = new SendPhoto(chatId, photoUrlPath);
                        sendPhoto.caption(pet.getPetName());
                        telegramBot.execute(sendPhoto);
                    } else {
                        telegramBot.execute(new SendMessage(chatId, "у животного нет фотографии"));
                    }
                }
            }
        }

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
                new SendMessage(chatId, "Выбери интересующий тебя вопрос:");
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
                new SendMessage(chatId, "Выбери интересующий тебя вопрос:");
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
                new SendMessage(chatId, "Ниже представлена ионформация для усыновления собаки:");
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
                new SendMessage(chatId, "Ниже представлена информация для усыновления кошки:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithCatsAdoptionInfo());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public void getMenuWithCats(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Выбери кошку, на которую хочешь взглянуть:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithCats());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public void getMenuWithDogs(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Выбери собаку, на которую хочешь взглянуть:");
        sendMessage.replyMarkup(inlineKeyboardMarkupService.createMenuWithDogs());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}