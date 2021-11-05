package com.gb;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;


public class ImgurApiTest {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL + "/" + ImgurApiParams.API_VERSION;
    }

    @DisplayName("Тест на получение базовой информации об аккаунте")
    @Test
    @Order(1)
    void testAccountBase() {
        String url = "account/" + "testertest100";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.reputation", is(7))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест обновления информации о картинке")
    @Test
    @Order(2)
    void testUpdateImageInfo() {
        String imageHash = "t4ZAkqp";
        String url = "image/" + imageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Осень")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url);
    }
    @DisplayName("Тест получение аватара аккаунта")
    @Test
    @Order(3)
    void getAccountAvatarTest() {
        String imageHash = "t4ZAkqp";
        String url = "account/" + "me/"+ imageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("avatar_name", "flavor/cat-2")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }
    @DisplayName("Тест получение представленных изобажений аккаунта")
    @Test
    @Order(4)
    void getAccountSubmissionsTest() {
        String pageHash = "0";
        String url = "account/" + "testertest100/"+"/submissions/" + pageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("id", "t4ZAkqp")
                .formParam("title", "Осень")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }

    @DisplayName("Тест получение профиля аккаунта")
    @Test
    @Order(5)
    void getAccountGalaryProfileTest() {

        String url = "account/" + "testertest100/"+"/settings";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("email", "testertest100@mail.ru")
                .formParam("avatar", "flavor/cat-2")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }

    @DisplayName("Тест получение настроек аккаунта")
    @Test
    @Order(6)
    void getAccountSettingsTest() {
        String Hash = "settings";

        String url = "account/" + "me/"+ Hash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("email", "testertest100@mail.ru")
                .formParam("album_privacy", "public")
                .formParam("show_mature", "false")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }


}
