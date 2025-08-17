package ui.base;

import api.clients.AuthApi;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import ui.pages.PersonalPage;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.Map;

public class TestBase {

    protected String lastPhpsessid;
    protected String lastAuthCookie;

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://siriusmusic.ru";
        RestAssured.baseURI = "https://siriusmusic.ru";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "128.0");
        Configuration.browserSize = System.getProperty("browserResolution", "1920x1080");
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

//        Configuration.baseUrl = "https://siriusmusic.ru";
//        Configuration.timeout = 10000;
//        Configuration.pageLoadTimeout = 10000;
//        Configuration.browserSize = "1920x1080";
//        Configuration.pageLoadStrategy = "eager";
//        RestAssured.baseURI = "https://siriusmusic.ru";
//
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        Configuration.browserCapabilities = capabilities;
//        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
//                "enableVNC", true,
//                "enableVideo", true
//        ));
//        String browserName = System.getProperty("browserName", "chrome");
//        String browserVersion = System.getProperty("browserVersion", "128.0");
//        String windowSize = System.getProperty("windowSize", "1920x1080");
//
//        Configuration.browser = browserName;
//        Configuration.browserVersion = browserVersion;
//        Configuration.browserSize = windowSize;
//
//        String selenoid = System.getProperty("selenoid", "ru.selenoid.autotests.cloud");
//        String login = System.getProperty("login", "user1");
//        String password = System.getProperty("password", "1234");
//        Configuration.remote = String.format("https://%s:%s@%s/wd/hub", login, password, selenoid);
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void cleanup() {
        try {
            // 1. Пробую API-логаут если есть данные
            if (lastPhpsessid != null && lastAuthCookie != null) {
                AuthApi.apiLogout(lastPhpsessid, lastAuthCookie);
            }
        } catch (Throwable e) {
            System.out.println("API логаут не удался, пробуем UI: " + e.getMessage());
            // 2. Фолбэк на UI логаут
            new PersonalPage().closePersonalPage();
        } finally {
            // 3. Всегда чистим куки
            if (WebDriverRunner.hasWebDriverStarted()) {
                WebDriverRunner.getWebDriver().manage().deleteAllCookies();
            }
            helpers.Attach.screenshotAs("Last screenshot");
            helpers.Attach.pageSource();
            helpers.Attach.browserConsoleLogs();
            helpers.Attach.addVideo();
            lastPhpsessid = null;
            lastAuthCookie = null;
        }
    }
}
