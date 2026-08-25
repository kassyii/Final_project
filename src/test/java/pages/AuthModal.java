package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class AuthModal {

    private final SelenideElement noAccountButton = $(byText("Нет аккаунта"));

    private final SelenideElement emailInput = $("input[type='email'], input[name='email']");
    private final SelenideElement passwordInput = $("input[type='password'], input[name='password']");

    private final SelenideElement submitLoginButton = $("form button.buttonPrimary");


    public void clickNoAccount() {
        noAccountButton.click();
    }

    public void fillEmail(String email) {
        emailInput.setValue(email);
    }

    public void fillPassword(String password) {
        passwordInput.setValue(password);
    }

    public void clickSubmitLogin() {
        submitLoginButton.click();
    }
}