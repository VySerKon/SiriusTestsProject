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

    public static boolean clearCart(String phpsessid, String bitrixLogin, String guestId) {
        try {
            Response clearResponse = given()
                    .spec(BaseSpec.requestSpec)
                    .cookie("PHPSESSID", phpsessid)
                    .cookie("BITRIX_SM_LOGIN", bitrixLogin)
                    .cookie("BITRIX_SM_GUEST_ID", guestId)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("accept", "*/*")
                    .header("origin", "https://siriusmusic.ru")
                    .header("referer", "https://siriusmusic.ru/login/")
                    .header("bx-ajax", "true")
                    .header("priority", "u=1, i")
                    .formParam("delete", "all")
                    .when()
                    .post("/ajax/getSmallBasketProducts.php");

            if (clearResponse.statusCode() != 200) {
                System.err.println("Clear cart failed. Status: " + clearResponse.statusCode());
                return false;
            }

            Response cartResponse = getCartContent(phpsessid, bitrixLogin);
            String cartContent = cartResponse.getBody().asString();

            return !cartContent.contains("cart-item") && !cartContent.contains("basket-item");
        } catch (Exception e) {
            System.err.println("Exception during cart clearing: " + e.getMessage());
            return false;
        }
    }
}
