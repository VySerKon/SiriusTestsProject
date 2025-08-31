package ui.tests.auth;

import api.clients.AuthApi;
import api.clients.CartApi;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ui.TestBase;
import ui.pages.CartPage;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClearCartUiTest extends TestBase {
    private final String testItemId = "22512";
    private final String testItemArticle = "16182";

    @Test
    void clearCartContent() {
        open("");

        String phpsessid = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
        assertNotNull(phpsessid, "PHPSESSID не получен");

        Response authResponse = AuthApi.authorize(phpsessid);
        String authCookie = authResponse.getCookie("BITRIX_SM_LOGIN");
        assertNotNull(authCookie, "Куки авторизации не получены");

        WebDriverRunner.getWebDriver().manage().addCookie(
                new org.openqa.selenium.Cookie("BITRIX_SM_LOGIN", authCookie)
        );

        Response addResponse = CartApi.addItem(testItemId, phpsessid, authCookie, authResponse.getCookie("BITRIX_SM_GUEST_ID"));
        addResponse.then().statusCode(200);

        open("/personal/cart/");

        CartPage cartPage = new CartPage();
        cartPage.verifyCartNotEmpty();
        cartPage.verifyItemPresent(testItemArticle);
        cartPage.deleteItem(testItemArticle);
        cartPage.verifyItemRemovedNotification(testItemArticle);
        cartPage.verifyRestoreButtonVisible();
    }
}
