package api.tests;

import api.base.ApiTestBase;
import api.clients.AuthApi;
import api.clients.SessionApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthApiTest extends ApiTestBase {
    @Test
    void authTest() {
        String phpsessid = SessionApi.getPhpSessId();
        Response response = AuthApi.authorize(phpsessid);
        assertNotNull(response.getCookie("BITRIX_SM_LOGIN"));
    }
}
