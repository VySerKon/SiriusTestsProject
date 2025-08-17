package ui.tests.unauth;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.base.TestBase;
import ui.pages.MainPage;

import java.util.stream.Stream;


import static io.qameta.allure.Allure.step;

@DisplayName("Тесты поиска товаров")
@Tag("WEB")
public class SearchTest extends TestBase {


    static Stream<Arguments> searchTestData() {
        return Stream.of(
                Arguments.of("Yamaha PACIFICA012", "Yamaha PACIFICA012 WHITE электрогитара"),
                Arguments.of("НФЬФРФ", "К сожалению, на ваш поисковый запрос ничего не найдено.")
        );
    }

    @ParameterizedTest(name = "Поиск ''{0}'': {1}")
    @MethodSource("searchTestData")
    @DisplayName("Проверка поиска товаров")
    void testSearchFunctionality(String searchQuery, String expectedMessage) {
        step("Открыть главную страницу", () -> {
            new MainPage().openMainPage();
        });

        step("Выполнить поиск по запросу: '" + searchQuery + "'", () -> {
            new MainPage().searchFor(searchQuery);
        });

        step("Проверить результаты поиска", () -> {
            new MainPage().verifySearchResults(expectedMessage);
        });
    }

}
