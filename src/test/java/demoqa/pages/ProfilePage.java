package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage {

    private final SelenideElement
            deleteButton = $("#delete-record-undefined"),
            closeSmallModalButton = $("#closeSmallModal-ok"),
            listArea = $(".rt-noData");

    @Step("Открытие страницы /profile")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Удаление книги из списка")
    public ProfilePage deleteBook() {
        deleteButton.click();
        return this;
    }

    @Step("Подтверждение удаления")
    public ProfilePage approveDelete() {
        closeSmallModalButton.click();
        return this;
    }

    @Step("Проверка отсутствия книг в списке")
    public void CheckingTheAbsenceOfBooksInTheList() {
        listArea.shouldHave(text("No rows found"));
    }
}
