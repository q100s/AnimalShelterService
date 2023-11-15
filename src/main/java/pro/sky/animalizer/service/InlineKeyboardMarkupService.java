package pro.sky.animalizer.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

/**
 * Класс для создания привязанных кнопок под сообщениями.
 */
@Component
public class InlineKeyboardMarkupService {


    /**
     * Метод, генерирующий клавиатуру для выбора приюта.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuWithShelterPicking() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для кошек")
                .callbackData("cat's shelter"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Приют для собак")
                .callbackData("dog's shelter"));
        return inlineKeyboardMarkup;
    }


    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для собак.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuAfterDogsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Информация о приюте")
                        .callbackData("dog's shelter info"),
                new InlineKeyboardButton("Как усыновить собаку")
                        .callbackData("усыновление собаки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Отправить отчет о питомце")
                        .callbackData("report sending"),
                new InlineKeyboardButton("Позвать волонтера")
                        .callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для выбора действия внутри меню приюта для кошек.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuAfterCatsShelterPick() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Информация о приюте")
                        .callbackData("cat's shelter info"),
                new InlineKeyboardButton("Как усыновить кошку")
                        .callbackData("усыновление кошки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Отправить отчет о питомце")
                        .callbackData("report sending"),
                new InlineKeyboardButton("Позвать волонтера")
                        .callbackData("volunteer calling"));


        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для предоставлении информации о приюте для кошек.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuWithCatsShelterOption() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("О приюте")
                        .callbackData("общая информация о кошачем приюте"),
                new InlineKeyboardButton("Расписание работы приюта")
                        .callbackData("cat's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Адрес приюта")
                        .callbackData("cat's shelter address"),
                new InlineKeyboardButton("Схема проезда")
                        .callbackData("cat's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Телефон охраны")
                        .callbackData("cat's security contact"),
                new InlineKeyboardButton("Позвать волонтера")
                        .callbackData("volunteer calling"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Техника безопасности на территории приюта")
                        .callbackData("cat's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные")
                        .callbackData("get personal info"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для предоставлении информации о приюте для собак.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuWithDogsShelterOptions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("О приюте")
                        .callbackData("общая информация о собачем приюте"),
                new InlineKeyboardButton("Расписание работы приюта")
                        .callbackData("dog's shelter schedule"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Адрес приюта")
                        .callbackData("dog's shelter address"),
                new InlineKeyboardButton("Схема проезда")
                        .callbackData("dog's direction path"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Телефон охраны")
                        .callbackData("dog's security contact"),
                new InlineKeyboardButton("Позвать волонтера")
                        .callbackData("volunteer calling"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Техника безопасности на территории приюта")
                        .callbackData("dog's safety measures"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные")
                        .callbackData("get personal info"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для предоставлении информации об усыновлении собаки.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuWithDogsAdoptionInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как знакомиться с собакой до усыновления")
                        .callbackData("правила знакомства с собакой"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Список документов, необходимых для усыновления собаки")
                        .callbackData("документы для усыновления собаки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как перевозить собаку")
                        .callbackData("транспортировка собаки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для щенка")
                        .callbackData("дом для щенка"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для взрослой собаки")
                        .callbackData("дом для взрослой собаки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для собаки с ограниченными возможностями")
                        .callbackData("дом для собаки с изъянами"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Советы кинолога по первичному общению с собакой")
                        .callbackData("первичное общение с собакой"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Список проверенных кинологов")
                        .callbackData("проверенные кинологи"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Список причин для отказа в усыновлении собаки")
                        .callbackData("причины отказа в усыновлении собы"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные").callbackData("get personal info"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }

    /**
     * Метод, генерирующий клавиатуру для предоставлении информации об усыновлении кошки.<br>
     * #{@link InlineKeyboardMarkup#addRow(InlineKeyboardButton...)} <br>
     *
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup createMenuWithCatsAdoptionInfo() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как знакомиться с кошкой до усыновления")
                        .callbackData("правила знакомства с кошкой"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Список документов, необходимых для усыновления кошки")
                        .callbackData("документы для усыновления кошки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как перевозить кошку")
                        .callbackData("транспортировка кошки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для котенка")
                        .callbackData("обустройство дома для котенка"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для взрослой кошки")
                        .callbackData("дом для взрослой кошки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Как обустроить дом для кошки с ограниченными возможностями")
                        .callbackData("дом для кошки с изъянами"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Список причин для отказа в усыновлении кошки")
                        .callbackData("причины отказа в усыновлении кошки"));
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Передать контактные данные").callbackData("get personal info"),
                new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer calling"));
        return inlineKeyboardMarkup;
    }
}