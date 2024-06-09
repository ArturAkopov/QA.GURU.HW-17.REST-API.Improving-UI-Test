package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    @Test
    void getSingleUserTest() {
        SingleUserResponseModel response = given()
                .log().all()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(SingleUserResponseModel.class);
        assertEquals("2", response.getData().getId());
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .log().all()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }


    @Test
    void deleteSingleUserTest() {
        given()
                .log().all()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");

        RegistrationResponseModel response =
                given()
                        .body(registrationData)
                        .contentType(JSON)
                        .log().all()
                        .when()
                        .post("https://reqres.in/api/register")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(RegistrationResponseModel.class);
        assertEquals("4", response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void updateUserTest() {
        UpdateBodyModel updateData = new UpdateBodyModel();
        updateData.setName("morpheus");
        updateData.setJob("zion resident");
        UpdateResponseModel response = given()
                .body(updateData)
                .contentType(JSON)
                .log().all()
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(UpdateResponseModel.class);
        assertEquals("morpheus", response.getName());
        assertEquals("zion resident", response.getJob());
    }
}