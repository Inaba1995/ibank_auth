package ru.netology.test;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generate(String status) {
            Faker faker = new Faker();

            return new RegistrationDto(
                    faker.name().firstName(), faker.internet().password(),
                    status
            );
        }

        public static String randStr() {
            Faker faker = new Faker();
            return faker.letterify("?????");
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();


        static void setUpUserActive(RegistrationDto userActive) {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(userActive)  // передаём в теле объект активный пользователь, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK

        }

        static void setUpUserBlocked(RegistrationDto userBlocked) {
            given()
                    .spec(requestSpec)
                    .body(userBlocked)  // заблокированный пользователь
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }
    }
}
