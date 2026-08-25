package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private static final String BASE_URL = "https://qa-desk.education-services.ru/";

    private final SelenideElement profileName = $("[class*='profileText']");
    private final SelenideElement logoutButton = $(byText("Выйти"));
    private final SelenideElement loginButton = $(byText("Вход и регистрация"));

    private final SelenideElement createListingButton = $(byText("Разместить объявление"));
    private final SelenideElement searchInput = $("input[placeholder='Я хочу купить...']");
    private final SelenideElement applySearchButton = $(byText("Применить"));

    public void openMainPage() {
        open(BASE_URL);
    }

    public void clickLoginAndRegister() {
        loginButton.click();
    }

    public void checkUserIsLoggedIn() {
        profileName.shouldBe(visible);
        logoutButton.shouldBe(visible);
    }

    public void clickCreateListing() {
        createListingButton.shouldBe(visible).click();
    }

    public void searchListing(String title) {
        searchInput.shouldBe(visible).setValue(title);
        applySearchButton.shouldBe(visible).click();
    }

    public void checkListingIsFound(String title) {
        $(byText(title)).shouldBe(visible);
    }

    public void clickEditForListing(String title) {
        $$("div[class*='card'], div[class*='item']")
                .findBy(Condition.text(title))
                .shouldBe(visible)
                .$("button")
                .click();
    }

    public void verifyListingPrice(String title, String expectedPrice) {
        SelenideElement updatedCard = $$("div[class*='card'], div[class*='item']")
                .findBy(Condition.text(title))
                .shouldBe(visible, Duration.ofSeconds(10));

        updatedCard.shouldHave(Condition.text(expectedPrice));
    }

    // Добавьте этот метод в MainPage.java для клика по самому объявлению
    public void clickListingByTitle(String title) {
        $$("div[class*='card'], div[class*='item']")
                .findBy(Condition.text(title))
                .shouldBe(visible)
                .click();
    }

    // Добавьте метод проверки отсутствия объявления в результатах поиска
    public void checkListingIsNotFound(String title) {
        $$("div[class*='card'], div[class*='item']")
                .findBy(Condition.text(title))
                .shouldNotBe(visible);
    }
}