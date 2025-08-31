package ui.tests.unauth;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.TestBase;
import ui.pages.MainPage;

import java.util.stream.Stream;


import static io.qameta.allure.Allure.step;


@DisplayName("Тесты навигационного меню")
@Tag("WEB")
@Tag("MENU")
public class MenuTest extends TestBase {

    MainPage mainPage = new MainPage();

    static Stream<Arguments> menuItemsProvider() {
        return Stream.of(
                Arguments.of("Почему мы?", "Об интернет-магазине музыкальных инструментов Сириус"),
                Arguments.of("Доставка", "Доставка из интернет-магазина музыкальных инструментов Сириус"),
                Arguments.of("Оплата", "Варианты оплаты в интернет-магазине музыкальных инструментов Сириус"),
                Arguments.of("Сервис", "Гарантия от интернет-магазина музыкальных инструментов Сириус")
        );
    }

    @ParameterizedTest(name = "Пункт меню: {0} → Заголовок: {1}")
    @MethodSource("menuItemsProvider")
    @DisplayName("Проверка переходов по пунктам меню")
    void testMenuNavigation(String menuItem, String expectedPageTitle) {
        step("Открыть главную страницу", () -> {
            mainPage.openMainPage();
        });

        step("Кликнуть на пункт меню: '" + menuItem + "'", () -> {
            mainPage.clickTopMenuLink(menuItem);
        });

        step("Проверить заголовок страницы", () -> {
            mainPage.verifyPageTitle(expectedPageTitle);
        });
    }
}
