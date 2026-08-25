package steps;

import api.ListingApiClient;
import api.UserApiClient;
import data.DataGenerator;
import io.cucumber.java.ru.*;
import pages.AuthModal;
import pages.CreateListingPage;
import pages.ListingPage;
import pages.MainPage;
import io.restassured.response.Response;

public class ListingSteps {

    private final MainPage mainPage = new MainPage();
    private final AuthModal authModal = new AuthModal();
    private final CreateListingPage createListingPage = new CreateListingPage();

    private final UserApiClient userApiClient = new UserApiClient();
    private final ListingApiClient listingApiClient = new ListingApiClient();

    private static String createdListingTitle;

    @И("Пользователь авторизуется под созданным аккаунтом")
    public void loginWithCreatedUser() {
        mainPage.clickLoginAndRegister();
        authModal.fillEmail(AuthSteps.getUserEmail());
        authModal.fillPassword(AuthSteps.getUserPassword());
        authModal.clickSubmitLogin();
        mainPage.checkUserIsLoggedIn();
    }

    @Когда("Пользователь нажимает кнопку размещения объявления")
    public void clickCreateListing() {
        mainPage.clickCreateListing();
    }

    @И("Вводит название объявления")
    public void fillListingTitle() {
        createdListingTitle = DataGenerator.getRandomTitle();
        createListingPage.fillTitle(createdListingTitle);
    }

    @И("Нажимает кнопку публикации")
    public void submitListing() {
        createListingPage.clickSubmit();
    }

    @Тогда("Объявление успешно отображается в поиске на главной странице")
    public void checkListingInSearch() {
        mainPage.searchListing(createdListingTitle);
        mainPage.checkListingIsFound(createdListingTitle);
    }

    @И("Пользователь создает объявление со статусом {string} и ценой {string}")
    public void createListingViaUi(String condition, String price) {
        createdListingTitle = DataGenerator.getRandomTitle();

        mainPage.clickCreateListing();
        createListingPage.fillListingForm(createdListingTitle, price, condition);
        createListingPage.clickSubmit();
    }

    @Когда("Пользователь ищет созданное объявление на главной странице")
    public void searchCreatedListingOnMainPage() {
        mainPage.openMainPage();
        mainPage.searchListing(createdListingTitle);
    }

    @И("Нажимает кнопку редактирования найденного объявления")
    public void clickEditListingButton() {
        mainPage.clickEditForListing(createdListingTitle);
    }

    @Когда("Изменяет состояние товара на {string}")
    public void changeCondition(String conditionText) {
        createListingPage.selectCondition(conditionText);
    }

    @Когда("Изменяет стоимость товара на {string}")
    public void changePrice(String newPrice) {
        createListingPage.fillPrice(newPrice);
    }

    @Когда("Нажимает кнопку сохранения изменений")
    public void submitEditForm() {
        createListingPage.clickSubmit();
    }

    @Тогда("Цена найденного объявления на главной странице равна {string}")
    public void verifyListingPrice(String expectedPrice) {
        mainPage.openMainPage();
        mainPage.searchListing(createdListingTitle);
        mainPage.verifyListingPrice(createdListingTitle, expectedPrice);
    }

    @И("Удаляет созданное объявление через API")
    public void deleteCreatedListingViaApi() throws InterruptedException {
        Thread.sleep(1000);

        Response loginResponse = userApiClient.loginUser(AuthSteps.getUserEmail(), AuthSteps.getUserPassword());
        loginResponse.then().statusCode(201);
        String token = loginResponse.jsonPath().getString("token.access_token");

        int listingId = listingApiClient.getListingIdByName(createdListingTitle);

        Response deleteResponse = listingApiClient.deleteListingById(listingId, token);
        deleteResponse.then().statusCode(200);
    }

    // Объявляем страницу просмотра объявления
    private final ListingPage listingPage = new ListingPage();

    @И("Переходит в карточку найденного объявления")
    public void openListingDetails() {
        mainPage.clickListingByTitle(createdListingTitle);
    }

    @Тогда("Кнопка {string} отображается на странице")
    public void verifyButtonVisible(String buttonName) {
        listingPage.checkDeleteButtonIsVisible();
    }

    @Когда("Пользователь нажимает кнопку \"Удалить\"")
    public void clickDeleteButton() {
        listingPage.clickDeleteButton();
    }

    @Тогда("Пользователь перенаправлен на главную страницу")
    public void verifyRedirectToMainPage() {
        // Проверяем, что URL соответствует главной странице
        com.codeborne.selenide.WebDriverRunner.url().equals("https://qa-desk.education-services.ru/");
    }

    @И("Созданное объявление отсутствует в поиске")
    public void checkListingNotFoundInSearch() {
        mainPage.searchListing(createdListingTitle);
        mainPage.checkListingIsNotFound(createdListingTitle);
    }
}