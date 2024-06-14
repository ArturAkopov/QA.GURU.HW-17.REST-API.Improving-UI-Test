package demoqa.tests;

import demoqa.models.AuthResponseBodyModel;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Тесты для demoqa.com")
public class DemoqaTests extends DemoqaTestBase {
    DemoqaTestSteps step = new DemoqaTestSteps();


    @Test()
    @DisplayName("Удаление книги из списка книг в профиле")
    @Owner("Акопов Артур")
    void deleteBookTest() {

        AuthResponseBodyModel authResponse = step.authorization();
        step.addListOfBook(authResponse);
        step.setCookiesInBrowser(authResponse);
        step.deleteBookFromListBooks();

    }

}