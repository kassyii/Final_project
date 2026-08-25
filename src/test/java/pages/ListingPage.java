package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ListingPage {

    // Кнопка "Удалить" на странице объявления
    private final SelenideElement deleteButton = $(byText("Удалить"));

    public void checkDeleteButtonIsVisible() {
        deleteButton.shouldBe(visible);
    }

    public void clickDeleteButton() {
        deleteButton.shouldBe(visible).click();
    }
}