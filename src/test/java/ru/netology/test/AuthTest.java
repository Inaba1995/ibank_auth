package ru.netology.test;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class AuthTest {
    private static RegistrationDto userActive = DataGenerator.Registration.generate("active");
    private static RegistrationDto userBlocked = DataGenerator.Registration.generate("blocked");
    private static RegistrationDto userInvalid = DataGenerator.Registration.generate("active");



    @BeforeAll
    static void setUpAll() {
        DataGenerator.Registration.setUpUserActive(userActive);
        DataGenerator.Registration.setUpUserBlocked(userBlocked);
    }

    @Test
    void shouldValidActiveUser() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(userActive.getLogin());
        $("[data-test-id='password'] input").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading")
                .shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidBlockedUser() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(userBlocked.getLogin());
        $("[data-test-id='password'] input").setValue(userBlocked.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldInvalidUser() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(userInvalid.getLogin());
        $("[data-test-id='password'] input").setValue(userInvalid.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidPasswordWrongLogin() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(userActive.getLogin() + DataGenerator.Registration.randStr());
        $("[data-test-id='password'] input").setValue(userActive.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidLoginWrongPassword() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue(userActive.getLogin());
        $("[data-test-id='password'] input").setValue(userActive.getPassword() + DataGenerator.Registration.randStr());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
