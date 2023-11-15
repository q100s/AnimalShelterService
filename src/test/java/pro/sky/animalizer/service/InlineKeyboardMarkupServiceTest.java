package pro.sky.animalizer.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InlineKeyboardMarkupServiceTest {
    @Mock
    private PetService petService;

    @InjectMocks
    private InlineKeyboardMarkupService service;
    @Test
    public void testCreateMenuWithShelterPicking() {
        InlineKeyboardMarkup markup = service.createMenuWithShelterPicking();
        assertNotNull(markup);
        assertEquals(2, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Приют для кошек", button1.text());
        assertEquals("cat's shelter", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Приют для собак", button2.text());
        assertEquals("dog's shelter", button2.callbackData());
    }

    @Test
    public void testCreateMenuAfterDogsShelterPick() {
        InlineKeyboardMarkup markup = service.createMenuAfterDogsShelterPick();
        assertNotNull(markup);
        assertEquals(3, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Информация о приюте", button1.text());
        assertEquals("dog's shelter info", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[0][1];
        assertNotNull(button2);
        assertEquals("Как усыновить собаку", button2.text());
        assertEquals("усыновление собаки", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[1][0];
        assertNotNull(button3);
        assertEquals("Отправить отчет о питомце", button3.text());
        assertEquals("report sending", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[1][1];
        assertNotNull(button4);
        assertEquals("Позвать волонтера", button4.text());
        assertEquals("volunteer calling", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[2][0];
        assertNotNull(button5);
        assertEquals("Посмотреть всех собак", button5.text());
        assertEquals("все собаки", button5.callbackData());
    }

    @Test
    public void testCreateMenuAfterCatsShelterPick() {
        InlineKeyboardMarkup markup = service.createMenuAfterCatsShelterPick();
        assertNotNull(markup);
        assertEquals(3, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Информация о приюте", button1.text());
        assertEquals("cat's shelter info", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[0][1];
        assertNotNull(button2);
        assertEquals("Как усыновить кошку", button2.text());
        assertEquals("усыновление кошки", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[1][0];
        assertNotNull(button3);
        assertEquals("Отправить отчет о питомце", button3.text());
        assertEquals("report sending", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[1][1];
        assertNotNull(button4);
        assertEquals("Позвать волонтера", button4.text());
        assertEquals("volunteer calling", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[2][0];
        assertNotNull(button5);
        assertEquals("Посмотреть всех котиков", button5.text());
        assertEquals("все котики", button5.callbackData());
    }

    @Test
    public void testCreateMenuWithDogsShelterOption() {
        InlineKeyboardMarkup markup = service.createMenuWithDogsShelterOptions();
        assertNotNull(markup);
        assertEquals(5, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("О приюте", button1.text());
        assertEquals("общая информация о собачем приюте", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[0][1];
        assertNotNull(button2);
        assertEquals("Расписание работы приюта", button2.text());
        assertEquals("dog's shelter schedule", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[1][0];
        assertNotNull(button3);
        assertEquals("Адрес приюта", button3.text());
        assertEquals("dog's shelter address", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[1][1];
        assertNotNull(button4);
        assertEquals("Схема проезда", button4.text());
        assertEquals("dog's direction path", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[2][0];
        assertNotNull(button5);
        assertEquals("Телефон охраны", button5.text());
        assertEquals("dog's security contact", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[2][1];
        assertNotNull(button6);
        assertEquals("Позвать волонтера", button6.text());
        assertEquals("volunteer calling", button6.callbackData());

        InlineKeyboardButton button7 = markup.inlineKeyboard()[3][0];
        assertNotNull(button7);
        assertEquals("Техника безопасности на территории приюта", button7.text());
        assertEquals("dog's safety measures", button7.callbackData());

        InlineKeyboardButton button8 = markup.inlineKeyboard()[4][0];
        assertNotNull(button8);
        assertEquals("Передать контактные данные", button8.text());
        assertEquals("get personal info", button8.callbackData());
    }

    @Test
    public void testCreateMenuWithCatsShelterOption() {
        InlineKeyboardMarkup markup = service.createMenuWithCatsShelterOption();
        assertNotNull(markup);
        assertEquals(5, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("О приюте", button1.text());
        assertEquals("общая информация о кошачем приюте", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[0][1];
        assertNotNull(button2);
        assertEquals("Расписание работы приюта", button2.text());
        assertEquals("cat's shelter schedule", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[1][0];
        assertNotNull(button3);
        assertEquals("Адрес приюта", button3.text());
        assertEquals("cat's shelter address", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[1][1];
        assertNotNull(button4);
        assertEquals("Схема проезда", button4.text());
        assertEquals("cat's direction path", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[2][0];
        assertNotNull(button5);
        assertEquals("Телефон охраны", button5.text());
        assertEquals("cat's security contact", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[2][1];
        assertNotNull(button6);
        assertEquals("Позвать волонтера", button6.text());
        assertEquals("volunteer calling", button6.callbackData());

        InlineKeyboardButton button7 = markup.inlineKeyboard()[3][0];
        assertNotNull(button7);
        assertEquals("Техника безопасности на территории приюта", button7.text());
        assertEquals("cat's safety measures", button7.callbackData());

        InlineKeyboardButton button8 = markup.inlineKeyboard()[4][0];
        assertNotNull(button8);
        assertEquals("Передать контактные данные", button8.text());
        assertEquals("get personal info", button8.callbackData());
    }
    @Test
    public void testCreateMenuWithDogsAdoptionInfo() {
        InlineKeyboardMarkup markup = service.createMenuWithDogsAdoptionInfo();
        assertNotNull(markup);
        assertEquals(10, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Как знакомиться с собакой до усыновления", button1.text());
        assertEquals("правила знакомства с собакой", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Список документов, необходимых для усыновления собаки", button2.text());
        assertEquals("документы для усыновления собаки", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Как перевозить собаку", button3.text());
        assertEquals("транспортировка собаки", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Как обустроить дом для щенка", button4.text());
        assertEquals("дом для щенка", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Как обустроить дом для взрослой собаки", button5.text());
        assertEquals("дом для взрослой собаки", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Как обустроить дом для собаки с ограниченными возможностями", button6.text());
        assertEquals("дом для собаки с изъянами", button6.callbackData());

        InlineKeyboardButton button7 = markup.inlineKeyboard()[6][0];
        assertNotNull(button7);
        assertEquals("Советы кинолога по первичному общению с собакой", button7.text());
        assertEquals("первичное общение с собакой", button7.callbackData());

        InlineKeyboardButton button8 = markup.inlineKeyboard()[7][0];
        assertNotNull(button8);
        assertEquals("Список проверенных кинологов", button8.text());
        assertEquals("проверенные кинологи", button8.callbackData());

        InlineKeyboardButton button9 = markup.inlineKeyboard()[8][0];
        assertNotNull(button9);
        assertEquals("Список причин для отказа в усыновлении собаки", button9.text());
        assertEquals("причины отказа в усыновлении собы", button9.callbackData());

        InlineKeyboardButton button10 = markup.inlineKeyboard()[9][0];
        assertNotNull(button10);
        assertEquals("Передать контактные данные", button10.text());
        assertEquals("get personal info", button10.callbackData());

        InlineKeyboardButton button11 = markup.inlineKeyboard()[9][1];
        assertNotNull(button11);
        assertEquals("Позвать волонтера", button11.text());
        assertEquals("volunteer calling", button11.callbackData());
    }

    @Test
    public void testCreateMenuWithCatsAdoptionInfo() {
        InlineKeyboardMarkup markup = service.createMenuWithCatsAdoptionInfo();
        assertNotNull(markup);
        assertEquals(8, markup.inlineKeyboard().length);

        InlineKeyboardButton button1 = markup.inlineKeyboard()[0][0];
        assertNotNull(button1);
        assertEquals("Как знакомиться с кошкой до усыновления", button1.text());
        assertEquals("правила знакомства с кошкой", button1.callbackData());

        InlineKeyboardButton button2 = markup.inlineKeyboard()[1][0];
        assertNotNull(button2);
        assertEquals("Список документов, необходимых для усыновления кошки", button2.text());
        assertEquals("документы для усыновления кошки", button2.callbackData());

        InlineKeyboardButton button3 = markup.inlineKeyboard()[2][0];
        assertNotNull(button3);
        assertEquals("Как перевозить кошку", button3.text());
        assertEquals("транспортировка кошки", button3.callbackData());

        InlineKeyboardButton button4 = markup.inlineKeyboard()[3][0];
        assertNotNull(button4);
        assertEquals("Как обустроить дом для котенка", button4.text());
        assertEquals("обустройство дома для котенка", button4.callbackData());

        InlineKeyboardButton button5 = markup.inlineKeyboard()[4][0];
        assertNotNull(button5);
        assertEquals("Как обустроить дом для взрослой кошки", button5.text());
        assertEquals("дом для взрослой кошки", button5.callbackData());

        InlineKeyboardButton button6 = markup.inlineKeyboard()[5][0];
        assertNotNull(button6);
        assertEquals("Как обустроить дом для кошки с ограниченными возможностями", button6.text());
        assertEquals("дом для кошки с изъянами", button6.callbackData());

        InlineKeyboardButton button9 = markup.inlineKeyboard()[6][0];
        assertNotNull(button9);
        assertEquals("Список причин для отказа в усыновлении кошки", button9.text());
        assertEquals("причины отказа в усыновлении кошки", button9.callbackData());

        InlineKeyboardButton button10 = markup.inlineKeyboard()[7][0];
        assertNotNull(button10);
        assertEquals("Передать контактные данные", button10.text());
        assertEquals("get personal info", button10.callbackData());

        InlineKeyboardButton button11 = markup.inlineKeyboard()[7][1];
        assertNotNull(button11);
        assertEquals("Позвать волонтера", button11.text());
        assertEquals("volunteer calling", button11.callbackData());
    }
}
