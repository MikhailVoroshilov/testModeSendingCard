package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.*;

public class SendingCard {
    private final String message = "Встреча успешно запланирована на ";

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true; //не закрывает браузер по оканчанию теста
        Configuration.browserSize = "800x800"; //размер открывающегося окна
        open("http://localhost:9999/");
    }

    @Test
    public void shouldCorrectFillingForm() {
        String date = Generate.generateDate(3);
        $("[data-test-id='city'] input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id='name'] input").setValue(Generate.generateNameHyphen());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=success-notification] .notification__content").shouldBe(text(this.message), Duration.ofSeconds(15)).shouldBe(text(date));
    }

    @Test
    public void shouldTestIncorrectEnterCity() {
        $$x("//input[@placeholder='Город']").exclude(hidden).first().setValue(Generate.generateCityEnglish());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id=agreement]").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).should(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldTestNullEnterCity() {
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id=agreement]").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).should(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldIncorrectEnterDate() {
        $("[data-test-id='city'] input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(2));
        $("[data-test-id='name'] input").setValue(Generate.generateFirstName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldIncorrectEnterDateWeek() {
        $("[data-test-id='city'] input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(7));
        $("[data-test-id='name'] input").setValue(Generate.generateLatName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(text(this.message));
    }

    @Test
    public void shouldCorrectCityTwhoSymbol() {
        $x("//input[@placeholder='Город']").setValue(Generate.generateTwhoSymbol()).sendKeys(Keys.DOWN, Keys.ENTER);
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateLatName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }

    @Test
    public void shouldTestIncorrectEnterNameUnderscore() {
        $x("//*[@data-test-id = 'city']//input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateNameUnderscore());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterNameEnglish() {
        $x("//*[@data-test-id = 'city']//input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateNameEnglish());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterNameSymbol() {
        $x("//*[@data-test-id = 'city']//input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateNameSymbol());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
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
        $x("//*[@data-test-id = 'city']//input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(Generate.generateDate(3));
        $("[data-test-id='name'] input").setValue(Generate.generateFullName());
        $("[data-test-id='phone'] input").setValue(Generate.generatePhoneErorNotPluss());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();

        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }

    @Test
    public void shouldCorrectFillingFormDouble() {
        String date = Generate.generateDate(3);
        String name = Generate.generateNameHyphen();

        $("[data-test-id='city'] input").setValue(Generate.generateCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(Generate.generatePhone());
        $("[data-test-id='agreement']").click();
        $$(".button").filter(visible).first().click();
        Duration.ofSeconds(15);
        $x("//*/div/form/fieldset/div[6]/div[2]/div/button/span/span[2]").click();
        $("[data-test-id=replan-notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(text("Необходимо подтверждение"));
        $x("//*[@data-test-id='replan-notification']//*[@class='button__text']").click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).should(text(this.message));
    }
}
