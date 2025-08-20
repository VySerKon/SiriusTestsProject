package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PersonalPage {
    public SelenideElement profileBlock = $("#user_div_reg");
    public SelenideElement emailField = $("input[name='EMAIL']");
    public SelenideElement nameField = $("input[name='NAME']");

    public PersonalPage verifyProfileVisible() {
        profileBlock.shouldBe(visible);
        return this;
    }

    public PersonalPage verifyUserData(String email, String name) {
        emailField.shouldHave(value(email));
        nameField.shouldHave(value(name));
        return this;
    }

    public PersonalPage openPersonalPage() {
        open("/personal/private/");
        return this;
    }
}
