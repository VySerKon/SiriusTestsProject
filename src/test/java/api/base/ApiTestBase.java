package api.base;

import api.clients.AuthApi;
import api.clients.CartApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTestBase {

    protected static String testPhpsessid;
    protected static String testAuthCookie;
    protected static String testGuestId;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://siriusmusic.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    void apiCleanup() {
        if (testPhpsessid != null && testAuthCookie != null && testGuestId != null) {
            try {
                System.out.println("Starting cleanup...");

                // Получаем текущие куки для диагностики
                Map<String, String> cookies = given()
                        .cookie("PHPSESSID", testPhpsessid)
                        .get("/")
                        .getCookies();
                System.out.println("Current cookies: " + cookies);

                // Очищаем корзину
                //CartApi.clearCart(testPhpsessid, testAuthCookie, testGuestId);

                // Выход из системы
                AuthApi.apiLogout(testPhpsessid, testAuthCookie);
            } catch (Throwable e) {
                System.err.println("API cleanup failed: " + e.getMessage());
            } finally {
                testPhpsessid = null;
                testAuthCookie = null;
            }
        }
    }
}
