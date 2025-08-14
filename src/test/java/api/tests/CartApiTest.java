package api.tests;

import api.base.ApiTestBase;
import api.clients.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartApiTest extends ApiTestBase {

    @Test
    void shouldAddItemToCart() {
        // 1. Получаем сессию
        String phpsessid = SessionApi.getPhpSessId();
        assertThat("PHPSESSID должен быть получен", phpsessid, notNullValue());

        // 2. Авторизуемся и получаем все необходимые куки
        Response authResponse = AuthApi.authorize(phpsessid);
        String bitrixLogin = authResponse.getCookie("BITRIX_SM_LOGIN");
        String guestId = authResponse.getCookie("BITRIX_SM_GUEST_ID");


        // 4. Добавляем товар в корзину (теперь с guestId)
        String testItemId = "59191";
        Response addResponse = CartApi.addItem(testItemId, phpsessid, bitrixLogin, guestId);

        // 5. Проверяем ответ
        addResponse.then()
                .statusCode(200)
                .body("STATUS", equalTo("OK"))
                .body("MESSAGE", equalTo("Товар успешно добавлен в корзину"));

        // 6. Проверяем содержимое корзины
        Response cartResponse = CartApi.getCartContent(phpsessid, bitrixLogin);
        String cartContent = cartResponse.getBody().asString();
        assertThat("Товар должен быть в корзине", cartContent, containsString(testItemId));
    }
}



