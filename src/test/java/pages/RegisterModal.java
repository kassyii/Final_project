package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegisterModal {

    private final SelenideElement emailInput = $("input[name='email']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitPasswordInput = $("input[name='submitPassword']");
    private final SelenideElement createAccountButton = $(byText("Создать аккаунт"));
    private final SelenideElement errorMessage = $("[class*='input_span']");

    public void fillRegistrationForm(String email, String password) {
        emailInput.clear();
        emailInput.setValue(email);

        passwordInput.clear();
        passwordInput.setValue(password);

        submitPasswordInput.clear();
        submitPasswordInput.setValue(password);
    }

    public void clickCreateAccount() {
        createAccountButton.click();
    }

    public void checkErrorMessageContains(String text) {
        errorMessage.shouldBe(visible).shouldHave(text(text));
    }

    public void checkErrorMessageIsDisplayed() {
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка"));
    }
}