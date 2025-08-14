package api.clients;

import static io.restassured.RestAssured.given;

public class SessionApi {
    public static String getPhpSessId() {
        return given()
                .when()
                .get("/") // Главная страница
                .then()
                .statusCode(200)
                .extract()
                .cookie("PHPSESSID");
    }
}
