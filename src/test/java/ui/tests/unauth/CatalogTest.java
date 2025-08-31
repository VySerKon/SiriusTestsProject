package ui.tests.unauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.TestBase;
import ui.pages.CatalogPage;
import ui.pages.MainPage;

import java.util.List;
import java.util.stream.Stream;


import static io.qameta.allure.Allure.step;


@DisplayName("Тесты каталога")
@Tag("WEB")
@Tag("CATALOG")
public class CatalogTest extends TestBase {

    MainPage mainPage = new MainPage();
    CatalogPage catalogPage = new CatalogPage();

    static Stream<String> categoryNamesProvider() {
        return Stream.of(
                "Клавишные инструменты",
                "Гитары и гитарное оборудование",
                "Ударные инструменты",
                "Звуковое оборудование",
                "Студийное оборудование"
        );
    }

    static Stream<Arguments> categoryWithSubcategories() {
        return Stream.of(
                Arguments.of(
                        "Клавишные инструменты",
                        List.of(
                                "Акустические фортепиано",
                                "Цифровые фортепиано",
                                "Синтезаторы и рабочие станции",
                                "MIDI-клавиатуры",
                                "Инструментальные комбо-усилители",
                                "Аксессуары и принадлежности",
                                "Банкетки",
                                "Чехлы и кейсы для клавишных",
                                "Органы и клавесины",
                                "Стойки для клавишных инструментов"
                        )
                )
        );
    }

    @ParameterizedTest(name = "Категория: {0}")
    @MethodSource("categoryNamesProvider")
    @DisplayName("Открытие категорий каталога")
    void testCategoryOpening(String categoryName) {
        step("Открыть главную страницу", () -> {
            mainPage.openMainPage();
        });

        step("Активировать блок категорий", () -> {
            mainPage.activateCategoriesBlock();
        });

        step("Кликнуть на категорию: " + categoryName, () -> {
            mainPage.clickCategoryByName(categoryName);
        });

        step("Проверить заголовок категории", () -> {
            mainPage.verifyCategoryTitle(categoryName);
        });
    }

    @ParameterizedTest(name = "Для категории {0} должны отображаться подкатегории: {1}")
    @MethodSource("categoryWithSubcategories")
    @DisplayName("Проверка динамического отображения подкатегорий")
    void verifyCategoryContainsSubcategories(String categoryName, List<String> expectedSubcategories) {
        step("Открыть каталог", () -> {
            catalogPage.openCatalog();
        });

        step("Проверить подкатегории для: " + categoryName, () -> {
            catalogPage.verifySubcategoriesContain(categoryName, expectedSubcategories);
        });
    }
}
