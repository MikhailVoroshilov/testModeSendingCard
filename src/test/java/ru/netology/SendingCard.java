package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SendingCard {
    private final String message = "Встреча успешно запланирована на ";
    private final SelenideElement city = $("[data-test-id='city'] input");
    private final SelenideElement cityXpath = $x("//input[@placeholder='Город']");
    private final SelenideElement cityXpathVartwo = $x("//*[@data-test-id = 'city']//input");
    private final SelenideElement date = $("[data-test-id=date] .input__control");
    private final SelenideElement name = $("[data-test-id='name'] input");
    private final SelenideElement phone = $("[data-test-id='phone'] input");
    private final SelenideElement checkBox = $("[data-test-id='agreement']");
    private final SelenideElement plan = $(".button");
    private final SelenideElement test = $("[data-test-id=success-notification] .notification__content");
    private final SelenideElement testInvalidCity = $("[data-test-id=city].input_invalid .input__sub");
    private final SelenideElement testInvalidDate = $("[data-test-id=date] .input_invalid .input__sub");
    private final SelenideElement testInvalidName = $("[data-test-id=name].input_invalid .input__sub");
    private final SelenideElement replan = $("[data-test-id=replan-notification] .notification__title");

    @BeforeEach
    public void setUp() {
//        Configuration.holdBrowserOpen = true; //не закрывает браузер по оканчанию теста
//        Configuration.browserSize = "800x750"; //размер открывающегося окна
        open("http://localhost:9999/");
    }

    @Test
    public void shouldCorrectFillingForm() {
        String date = Generate.generateDate(3);
        this.city.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(date);
        this.name.setValue(Generate.generateNameHyphen());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.test.shouldBe(text(this.message), Duration.ofSeconds(15)).shouldBe(text(date));
    }

    @Test
    public void shouldTestIncorrectEnterCity() {
        this.cityXpath.setValue(Generate.generateCityEnglish());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateName());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidCity.shouldBe(visible).should(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldTestNullEnterCity() {
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateName());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidCity.shouldBe(visible).should(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldIncorrectEnterDate() {
        this.city.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(2));
        this.name.setValue(Generate.generateFirstName());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidDate.shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldIncorrectEnterDateWeek() {
        this.city.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(7));
        this.name.setValue(Generate.generateLatName());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.test.shouldBe(visible, Duration.ofSeconds(15)).shouldBe(text(this.message));
    }

    @Test
    public void shouldCorrectCityTwhoSymbol() {
        this.cityXpath.setValue(Generate.generateTwhoSymbol()).sendKeys(Keys.DOWN, Keys.ENTER);
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateLatName());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }

    @Test
    public void shouldTestIncorrectEnterNameUnderscore() {
        this.cityXpathVartwo.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateNameUnderscore());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidName.shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterNameEnglish() {
        this.cityXpathVartwo.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateNameEnglish());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidName.shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterNameSymbol() {
        this.cityXpathVartwo.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateNameSymbol());
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();

        this.testInvalidName.shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

//    @Test
//    public void shouldTestIncorrectEnterPhone() {
//        $x("//*[@data-test-id = 'city']//input").setValue(Generate.generateCity());
//        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
//        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
//        $("[data-test-id='name'] input").setValue(Generate.generateFullName());
//        $("[data-test-id='phone'] input").setValue(Generate.generatePhoneEror());
//        $("[data-test-id='agreement']").click();
//        $$(".button").filter(visible).first().click();
//
//        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
//    }

    @Test
    public void shouldTestIncorrectEnterPhoneNotPlus() {
        this.cityXpathVartwo.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(3));
        this.name.setValue(Generate.generateFullName());
        this.phone.setValue(Generate.generatePhoneErorNotPluss());
        this.checkBox.click();
        this.plan.click();

        this.test.shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }

    @Test
    public void shouldCorrectFillingFormDouble() {
        String date = Generate.generateDate(3);
        String name = Generate.generateNameHyphen();

        this.city.setValue(Generate.generateCity());
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(date);
        this.name.setValue(name);
        this.phone.setValue(Generate.generatePhone());
        this.checkBox.click();
        this.plan.click();
        this.test.shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
        this.date.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        this.date.setValue(Generate.generateDate(10));
        this.plan.click();
        this.replan.shouldBe(visible, Duration.ofSeconds(15)).shouldBe(text("Необходимо подтверждение"));
        $x("//*[@data-test-id='replan-notification']//*[@class='button__text']").click();
        this.test.shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }
}
