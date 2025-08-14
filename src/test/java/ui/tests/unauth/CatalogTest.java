package ui.tests.unauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.base.TestBase;
import ui.pages.CatalogPage;
import ui.pages.MainPage;

import java.util.List;
import java.util.stream.Stream;


import static io.qameta.allure.Allure.step;

@Tag("SIMPLE")
@DisplayName("Тесты каталога")
public class CatalogTest extends TestBase {

    // Провайдер данных для открытия категорий
    static Stream<String> categoryNamesProvider() {
        return Stream.of(
                "Клавишные инструменты",
                "Гитары и гитарное оборудование",
                "Ударные инструменты",
                "Звуковое оборудование",
                "Студийное оборудование"
        );
    }

    // Провайдер данных для подкатегорий
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

    // Тест открытия категорий
    @ParameterizedTest(name = "Категория: {0}")
    @MethodSource("categoryNamesProvider")
    @DisplayName("Открытие категорий каталога")
    void testCategoryOpening(String categoryName) {
        step("Открыть главную страницу", () -> {
            new MainPage().openMainPage();
        });

        step("Активировать блок категорий", () -> {
            new MainPage().activateCategoriesBlock();
        });

        step("Кликнуть на категорию: " + categoryName, () -> {
            new MainPage().clickCategoryByName(categoryName);
        });

        step("Проверить заголовок категории", () -> {
            new MainPage().verifyCategoryTitle(categoryName);
        });
    }

    // Тест проверки подкатегорий
    @ParameterizedTest(name = "Для категории {0} должны отображаться подкатегории: {1}")
    @MethodSource("categoryWithSubcategories")
    @DisplayName("Проверка подкатегорий")
    void verifyCategoryContainsSubcategories(String categoryName, List<String> expectedSubcategories) {
        step("Открыть каталог", () -> {
            new CatalogPage().openCatalog();
        });

        step("Проверить подкатегории для: " + categoryName, () -> {
            new CatalogPage().verifySubcategoriesContain(categoryName, expectedSubcategories);
        });
    }
}
