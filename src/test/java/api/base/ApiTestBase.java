package api.base;

import api.clients.AuthApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

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
                AuthApi.apiLogout(testPhpsessid, testAuthCookie);
                testPhpsessid = null;
                testAuthCookie = null;
            }
        }
    }
