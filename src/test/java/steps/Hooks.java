package steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import pages.MainPage;

public class Hooks {

    private final MainPage mainPage = new MainPage();

    @Before
    public void setUp() {
        mainPage.openMainPage();
    }

    @After
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}