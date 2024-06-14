package demoqa.tests;

import demoqa.models.AddListOfBookRequestBodyModel;
import demoqa.models.AuthRequestBodyModel;
import demoqa.models.AuthResponseBodyModel;
import demoqa.pages.ProfilePage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.specs.DemoqaSpec.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoqaTestSteps {

    DemoqaTestData testData = new DemoqaTestData();
    ProfilePage steps = new ProfilePage();


    @Step("Авторизация пользователя")
    public AuthResponseBodyModel authorization() {
        AuthRequestBodyModel authData = new AuthRequestBodyModel();
        authData.setUserName(testData.login);
        authData.setPassword(testData.password);

        return (given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(demoqaResponseSpec200)
                .extract().as(AuthResponseBodyModel.class));

    }

    @Step("Добавление книги в список")
    public void addListOfBook(AuthResponseBodyModel response) {
        AddListOfBookRequestBodyModel bookData = new AddListOfBookRequestBodyModel();
        bookData.setUserId(response.getUserId());
        bookData.setIsbnValue(testData.isbn);

        Response addBookResponse = given(demoqaRequestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + response.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(demoqaResponseSpec201)
                .extract().response();
        assertEquals(bookData.getCollectionOfIsbns().get(0).getIsbn(), addBookResponse.path("books[0].isbn"));
    }

    @Step("Авторизация в браузере с помощью cookie")
    public void setCookiesInBrowser(AuthResponseBodyModel authResponse) {
        open("/images/gplaypattern.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
    }

    @Step("Удаление книги из списка")
    public void deleteBookFromListBooks() {
        steps.openPage()
                .deleteBook()
                .approveDelete()
                .CheckingTheAbsenceOfBooksInTheList();
    }
}
