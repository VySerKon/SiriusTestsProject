package api.clients;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;


import static api.specs.AuthSpec.responseAuthSpec;
import static api.specs.BaseSpec.redirectSpec;
import static api.specs.BaseSpec.requestSpec;
import static io.restassured.RestAssured.given;

public class AuthApi {

    public static Response authorize(String phpsessid) {

        String userLogin = System.getProperty("user_login", "");
        String userPassword = System.getProperty("user_password", "");

        return given()
                .spec(requestSpec)
                .cookie("PHPSESSID", phpsessid)
                .formParam("AUTH_FORM", "Y")
                .formParam("TYPE", "AUTH")
                .formParam("backurl", "/login/")
                .formParam("USER_LOGIN", userLogin)
                .formParam("USER_PASSWORD", userPassword)
                .formParam("Login", "Войти")
                .redirects().follow(false)
                .when()
                .post("/login/?login=yes")
                .then()
                .spec(redirectSpec)
                .spec(responseAuthSpec)
                .extract().response();
    }

    public static void setAuthCookie(Response authResponse) {
        String bitrixLogin = authResponse.getCookie("BITRIX_SM_LOGIN");
        WebDriverRunner.getWebDriver().manage().addCookie(
                new Cookie("BITRIX_SM_LOGIN", bitrixLogin));
    }

    public static void apiLogout(String phpsessid, String authCookie) {
        given()
                .spec(requestSpec)
                .cookie("PHPSESSID", phpsessid)
                .cookie("BITRIX_SM_LOGIN", authCookie)
                .when()
                .post("/?logout=yes")
                .then()
                .statusCode(200);
    }
}




