package steps;

import api.UserApiClient;
import data.DataGenerator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.AuthModal;
import pages.MainPage;

public class AuthSteps {

    private final UserApiClient userApiClient = new UserApiClient();
    private final MainPage mainPage = new MainPage();
    private final AuthModal authModal = new AuthModal();

    private static String userEmail;
    private static String userPassword;

//    private static UserApiClient userApiClient = new UserApiClient();

    @Дано("Пользователь зарегистрирован в системе через API")
    public void registerUserViaApi() {
        userEmail = DataGenerator.getRandomEmail();
        userPassword = DataGenerator.getRandomPassword();
        userApiClient.registerUser(userEmail, userPassword);
    }

    @Когда("Пользователь нажимает кнопку входа и регистрации")
    public void openAuthModal() {
        mainPage.clickLoginAndRegister();
    }

    @И("Вводит верные данные для входа")
    public void fillCorrectCredentials() {
        authModal.fillEmail(userEmail);
        authModal.fillPassword(userPassword);
    }

    @И("Нажимает кнопку входа")
    public void submitAuthForm() {
        authModal.clickSubmitLogin();
    }

    @Тогда("Пользователь успешно авторизован в системе")
    public void checkSuccessfulAuth() {
        mainPage.checkUserIsLoggedIn();
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getUserPassword() {
        return userPassword;
    }
}