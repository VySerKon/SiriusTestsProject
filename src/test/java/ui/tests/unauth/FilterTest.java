package ui.tests.unauth;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.TestBase;
import ui.pages.FilterPage;
import ui.pages.MainPage;


import static io.qameta.allure.Allure.step;

@DisplayName("Тесты фильтрации товаров")
@Tag("WEB")
@Tag("FILTER")
public class FilterTest extends TestBase {

    MainPage mainPage = new MainPage();
    FilterPage filterPage = new FilterPage();

    @Test
    @DisplayName("Фильтрация синтезаторов по ценовому диапазону")
    void testCasioSynthsInPriceRange() {
        step("Открыть раздел каталога: 'Синтезаторы и рабочие станции'", () -> {
            mainPage.openCatalogSection();
        });

        step("Открыть блок фильтров", () -> {
            mainPage.openFilters();
            filterPage.scrollToFilters();
        });

        step("Очистить поля цен", () -> {
            filterPage.clearPriceFields();
        });

        step("Установить диапазон цен", () -> {
            filterPage.setPriceRange(10_000, 20_000);
        });

        step("Выбрать бренд", () -> {
            filterPage.selectCasio();
        });

        step("Применить фильтры и проверить результаты", () -> {
            filterPage.applyAndVerify();
        });
    }
}
