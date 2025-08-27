package api.clients;

import api.specs.BaseSpec;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CartApi {

    public static Response addItem(String itemId, String phpsessid, String bitrixLogin, String guestId) {
        return given()
                .spec(BaseSpec.requestSpec)
                .cookie("PHPSESSID", phpsessid)
                .cookie("BITRIX_SM_LOGIN", bitrixLogin)
                .cookie("BITRIX_SM_GUEST_ID", guestId)
                .header("bx-ajax", "true")
                .header("referer", "https://siriusmusic.ru/catalog/")
                .queryParam("action", "ADD2BASKET")
                .queryParam("id", itemId)
                .formParam("ajax_basket", "Y")
                .formParam("prop[0]", "0")
                .when()
                .post("/catalog/gitary-i-gitarnoe-oborudovanie/akusticheskie-gitary/");
    }

    public static Response getCartContent(String phpsessid, String bitrixLogin) {
        return given()
                .spec(BaseSpec.requestSpec)
                .cookie("PHPSESSID", phpsessid)
                .cookie("BITRIX_SM_LOGIN", bitrixLogin)
                .when()
                .get("/personal/cart/");
    }
}
