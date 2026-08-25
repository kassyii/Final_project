package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreateListingPage {

    private final SelenideElement titleInput = $("input[name='name']");
    private final SelenideElement priceInput = $("input[name='price']");
    private final SelenideElement submitButton = $("form button[type='submit']");

    public void fillTitle(String title) {
        titleInput.shouldBe(visible).setValue(title);
    }

    public void fillPrice(String price) {
        priceInput.shouldBe(visible).clear();
        priceInput.setValue(price);
    }

    public void selectCondition(String condition) {
        $$("label").findBy(Condition.exactText(condition)).shouldBe(visible).click();
    }

    public void fillListingForm(String title, String price, String condition) {
        fillTitle(title);
        fillPrice(price);
        selectCondition(condition);
    }

    public void clickSubmit() {
        submitButton.shouldBe(visible).click();
    }
}