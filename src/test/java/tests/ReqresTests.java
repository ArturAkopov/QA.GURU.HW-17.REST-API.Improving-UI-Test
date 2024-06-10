package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    @Test
    void getSingleUserTest() {
        SingleUserResponseModel response = step("Отправляем запрос", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .get("https://reqres.in/api/users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(SingleUserResponseModel.class));
        step("Проверяем id в ответе", () ->
                assertEquals("2", response.getData().getId()));
    }

    @Test
    void getSingleUserNotFoundTest() {
        step("Отправляем запрос", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .get("https://reqres.in/api/users/23")
                        .then()
                        .log().all()
                        .statusCode(404));
    }


    @Test
    void deleteSingleUserTest() {
        step("Отправляем запрос", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .delete("https://reqres.in/api/users/2")
                        .then()
                        .log().all()
                        .statusCode(204));
    }

    @Test
    void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");

        RegistrationResponseModel response = step("Отправляем запрос", () ->
                given()
                        .filter(withCustomTemplates())
                        .body(registrationData)
                        .contentType(JSON)
                        .log().all()
                        .when()
                        .post("https://reqres.in/api/register")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(RegistrationResponseModel.class));
        step("Проверяем id в ответе", () ->
                assertEquals("4", response.getId()));
        step("Проверяем token в ответе", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void updateUserTest() {
        UpdateBodyModel updateData = new UpdateBodyModel();
        updateData.setName("morpheus");
        updateData.setJob("zion resident");

        UpdateResponseModel response = step("Отправляем запрос", () ->
                given()
                        .filter(withCustomTemplates())
                        .body(updateData)
                        .contentType(JSON)
                        .log().all()
                        .when()
                        .put("https://reqres.in/api/users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(UpdateResponseModel.class));
        step("Проверяем name в ответе", () ->
                assertEquals("morpheus", response.getName()));
        step("Проверяем job в ответе", () ->
                assertEquals("zion resident", response.getJob()));
    }
}