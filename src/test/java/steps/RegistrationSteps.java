package steps;

import api.UserApiClient;
import data.DataGenerator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.AuthModal;
import pages.MainPage;
import pages.RegisterModal;

public class RegistrationSteps {

    private final MainPage mainPage = new MainPage();
    private final AuthModal authModal = new AuthModal();
    private final RegisterModal registerModal = new RegisterModal();
    private final UserApiClient userApiClient = new UserApiClient();

    private String currentEmail;
    private String currentPassword;

    @Дано("Пользователь находится на главной странице")
    public void openMainPage() {
        mainPage.openMainPage();
    }

    @Дано("Пользователь уже зарегистрирован в системе через API")
    public void registerUserViaApi() {
        currentEmail = DataGenerator.getRandomEmail();
        currentPassword = DataGenerator.getRandomPassword();
        userApiClient.registerUser(currentEmail, currentPassword);
        mainPage.openMainPage();
    }

    @Когда("Пользователь переходит к форме регистрации")
    public void openRegisterModal() {
        mainPage.clickLoginAndRegister();
        authModal.clickNoAccount();
    }

    @И("Вводит уникальные данные для регистрации")
    public void fillUniqueData() {
        currentEmail = DataGenerator.getRandomEmail();
        currentPassword = DataGenerator.getRandomPassword();
        registerModal.fillRegistrationForm(currentEmail, currentPassword);
    }

    @И("Вводит email ранее зарегистрированного пользователя")
    public void fillExistingEmail() {
        registerModal.fillRegistrationForm(currentEmail, currentPassword);
    }

    @И("Нажимает кнопку создания аккаунта")
    public void submitForm() {
        registerModal.clickCreateAccount();
    }

    @Тогда("Пользователь успешно зарегистрирован и авторизован в системе")
    public void checkSuccessfulRegistration() {
        mainPage.checkUserIsLoggedIn();
    }

    @Тогда("Отображается ошибка о том, что пользователь уже существует")
    public void checkRegistrationError() {
        registerModal.checkErrorMessageIsDisplayed();
    }
}