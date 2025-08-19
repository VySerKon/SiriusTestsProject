package ui.tests.unauth;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.TestBase;
import ui.pages.FilterPage;
import ui.pages.MainPage;


import static io.qameta.allure.Allure.step;

@DisplayName("Тесты фильтрации товаров")
@Tag("WEB")
@Tag("FILTER")
public class FilterTest extends TestBase {

    @Test
    @DisplayName("Фильтрация синтезаторов Casio по ценовому диапазону")
    void testCasioSynthsInPriceRange() {
        step("Открыть раздел каталога: 'Синтезаторы и рабочие станции'", () -> {
            new MainPage().openCatalogSection();
        });

        step("Открыть блок фильтров", () -> {
            new MainPage().openFilters();
            new FilterPage().scrollToFilters();
        });

        step("Очистить поля цен", () -> {
            new FilterPage().clearPriceFields();
        });

        step("Установить диапазон цен: от 10 000 до 20 000 руб.", () -> {
            new FilterPage().setPriceRange(10_000, 20_000);
        });

        step("Выбрать бренд Casio", () -> {
            new FilterPage().selectCasio();
        });

        step("Применить фильтры и проверить результаты", () -> {
            new FilterPage().applyAndVerify();
        });
    }
}
