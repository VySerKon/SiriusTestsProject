package ui.tests.auth;

import api.clients.AuthApi;
import api.clients.CartApi;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.TestBase;
import ui.pages.CartPage;


import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClearCartUiTest extends TestBase {
    private final String testItemId = "22512"; // ID товара для API
    private final String testItemArticle = "16182"; // Артикул для UI (из HTML)

    @Test
    void clearCartContent() {
        // 1. Открываем главную страницу, чтобы инициализировать сессию браузера
        open("/");

        // 2. Получаем PHPSESSID из кук браузера
        String phpsessid = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
        assertNotNull(phpsessid, "PHPSESSID не получен");

        // 3. Авторизуемся через API в этой же сессии
        Response authResponse = AuthApi.authorize(phpsessid);
        String authCookie = authResponse.getCookie("BITRIX_SM_LOGIN");
        assertNotNull(authCookie, "Куки авторизации не получены");

        // 4. Устанавливаем куки в браузер
        WebDriverRunner.getWebDriver().manage().addCookie(
                new org.openqa.selenium.Cookie("BITRIX_SM_LOGIN", authCookie)
        );

        // 5. Добавляем товар в корзину через API
        Response addResponse = CartApi.addItem(testItemId, phpsessid, authCookie, authResponse.getCookie("BITRIX_SM_GUEST_ID"));
        addResponse.then().statusCode(200);

        // 6. Открываем корзину в UI (уже с авторизацией)
        open("/personal/cart/");

        // 7. Проверяем товар в корзине
        CartPage cartPage = new CartPage();
        cartPage.verifyCartNotEmpty();
        cartPage.verifyItemPresent(testItemArticle);
        // 8. Удаляем товар из корзины через UI
        cartPage.deleteItem(testItemArticle);

// 9. Проверяем, что появилось сообщение о удалении и кнопка восстановления
        cartPage.verifyItemRemovedNotification(testItemArticle);
        cartPage.verifyRestoreButtonVisible();
    }
}
