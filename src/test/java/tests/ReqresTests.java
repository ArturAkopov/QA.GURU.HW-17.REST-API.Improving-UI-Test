package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.ReqresSpec.*;


public class ReqresTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void getSingleUserTest() {
        SingleUserResponseModel response = step("Отправляем запрос", () ->
                given(reqresRequestSpec)
                        .get("/users/2")
                        .then()
                        .spec(reqresResponseSpec200)
                        .extract().as(SingleUserResponseModel.class));
        step("Проверяем id в ответе", () ->
                assertEquals("2", response.getData().getId()));
    }

    @Test
    void getSingleUserNotFoundTest() {
        step("Отправляем запрос", () ->
                given(reqresRequestSpec)
                        .get("/users/23")
                        .then()
                        .spec(reqresResponseSpec404));
    }

    @Test
    void deleteSingleUserTest() {
        step("Отправляем запрос", () ->
                given(reqresRequestSpec)
                        .delete("/users/2")
                        .then()
                        .spec(reqresResponseSpec204));
    }

    @Test
    void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");

        RegistrationResponseModel response = step("Отправляем запрос", () ->
                given(reqresRequestSpec)
                        .body(registrationData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(reqresResponseSpec200)
                        .extract().as(RegistrationResponseModel.class));
        step("Проверяем id в ответе", () ->
                assertEquals("4", response.getId()));
        step("Проверяем token в ответе", () ->
                assertNotNull(response.getToken()));
    }

    @Test
    void updateUserTest() {
        UpdateBodyModel updateData = new UpdateBodyModel();
        updateData.setName("morpheus");
        updateData.setJob("zion resident");

        UpdateResponseModel response = step("Отправляем запрос", () ->
                given(reqresRequestSpec)
                        .body(updateData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(reqresResponseSpec200)
                        .extract().as(UpdateResponseModel.class));
        step("Проверяем name в ответе", () ->
                assertEquals("morpheus", response.getName()));
        step("Проверяем job в ответе", () ->
                assertEquals("zion resident", response.getJob()));
    }
}