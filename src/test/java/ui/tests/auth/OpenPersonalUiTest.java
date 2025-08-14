package ui.tests.auth;

import api.clients.AuthApi;
import api.clients.SessionApi;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ui.pages.PersonalPage;
import ui.base.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenPersonalUiTest extends TestBase {

    private final String EMAIL = "simpleaccounttest@mail.ru";
    private final String NAME = "Тестовый";

    @Test
    public void testSuccessfulAuthorizationWithRedirect() {
        open("/");
        String phpsessid = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
        assertNotNull(phpsessid, "PHPSESSID не получен");

        Response response = AuthApi.authorize(phpsessid);
        AuthApi.setAuthCookie(response);

        PersonalPage personalPage = new PersonalPage();
        personalPage.openPersonalPage();
        personalPage.verifyProfileVisible();
        personalPage.verifyUserData(EMAIL, NAME);
    }
}
