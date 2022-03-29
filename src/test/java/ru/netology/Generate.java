package ru.netology;

import com.github.javafaker.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Generate {

    public static String generateCity(){
        Faker faker = new Faker(new Locale("ru"));
        return faker.options().option("Москва", "Санкт-Петербург", "Ульяновск", "Тула", "Липецк", "Казань", "Орёл", "Саранск", "Чебоксары", "Краснодар", "Екатеринбург");
    }

    public static String generateCityEnglish(){
        Faker faker = new Faker(new Locale("en","GB"));
        return faker.address().cityName();
    }

    public static String generateTwhoSymbol(){
        Faker faker = new Faker(new Locale("ru"));
        return faker.address().cityName().substring(0,2);
    }

    public static String generateDate(int plusDay) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateFullName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }
    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().name();
    }

    public static String generateFirstName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName();
    }

    public static String generateLatName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName();
    }

    public static String generateNameEnglish() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String generateNameHyphen() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " "
                + faker.name().lastName() + "-"
                + faker.name().lastName();
    }
    public static String generateNameUnderscore() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " "
                + faker.name().lastName() + "_"
                + faker.name().lastName();
    }

    public static String generateNameSymbol() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " "
                + faker.name().lastName() + "-"
                + faker.name().lastName() + "$";
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale("ru"));
        return "+7" + faker.phoneNumber().subscriberNumber(10);
    }

    public static String generatePhoneEror() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().subscriberNumber(9);
    }

    public static String generatePhoneErorNotPluss() {
        Faker faker = new Faker(new Locale("ru"));
        return "7" + faker.phoneNumber().subscriberNumber(10);
    }

}