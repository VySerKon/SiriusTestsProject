package ui.base;

import api.clients.AuthApi;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import ui.pages.PersonalPage;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.Map;

public class TestBase {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://siriusmusic.ru";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 10000;
        Configuration.pageLoadStrategy = "eager";
        RestAssured.baseURI = "https://siriusmusic.ru";

        String selenoidLogin = System.getProperty("login");
        String selenoidPassword = System.getProperty("password");
        String selenoidUrl = System.getProperty("selenoid_url", "selenoid.autotests.cloud");

        if (selenoidPassword != null && !selenoidPassword.isEmpty()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));

            Configuration.browser = System.getProperty("browserName", "chrome");
            Configuration.browserVersion = System.getProperty("browserVersion", "128.0");
            Configuration.browserSize = System.getProperty("windowSize", "1920x1080");

            Configuration.remote = String.format("https://%s:%s@%s/wd/hub", selenoidLogin, selenoidPassword, selenoidUrl);
            Configuration.browserCapabilities = capabilities;
        } else {
            Configuration.remote = null;
            Configuration.browser = System.getProperty("browserName", "chrome");
            Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
        }
    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void cleanup() {
        helpers.Attach.screenshotAs("Last screenshot");
        helpers.Attach.pageSource();
        helpers.Attach.browserConsoleLogs();
        helpers.Attach.addVideo();
        WebDriverRunner.closeWebDriver();
    }
}
