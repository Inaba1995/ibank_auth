package ru.netology.test;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {}

    public static class Registration {
        private Registration() {}

        public static RegistrationDto generate(String status) {
            Faker faker = new Faker();

            return new RegistrationDto(
                    faker.name().firstName(), faker.internet().password(),
                    status
            );
        }

        public static String randStr(){
            Faker faker = new Faker();
            return faker.letterify("?????");
        }
    }
}
