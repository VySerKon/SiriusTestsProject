package ui.tests.unauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.TestBase;
import ui.pages.CatalogPage;
import ui.pages.CartPage;
import ui.pages.MainPage;

import static io.qameta.allure.Allure.step;


@DisplayName("Тесты корзины товаров")
@Tag("WEB")
   public class CartTest extends TestBase {

    private static final String EXPECTED_ARTICLE = "STD MN GBK";
    private static final String EXPECTED_PRODUCT_NAME = "EVH Wolfgang STD MN GBK Gloss Black электрогитара";

    @Test
    @DisplayName("Добавление электрогитары в корзину и проверка содержимого")
    void testAddElectricGuitarToCartAndVerify() {
        step("Открыть раздел каталога: 'Электрогитары'", () -> {
            new CatalogPage().openElectricGuitarsSection();
        });

        step("Добавить электрогитару EVH Wolfgang в корзину", () -> {
            new CatalogPage().addGuitarToCart();
        });

        step("Закрыть всплывающее окно добавления товара", () -> {
            new CatalogPage().closePopup();
        });

        step("Перейти в корзину", () -> {
            new MainPage().openCart();
        });

        step("Проверить, что артикул товара соответствует ожидаемому: " + EXPECTED_ARTICLE, () -> {
            new CartPage().verifyArticleNumber(EXPECTED_ARTICLE);
        });

        step("Проверить, что название товара соответствует ожидаемому: " + EXPECTED_PRODUCT_NAME, () -> {
            new CartPage().verifyProductName(EXPECTED_PRODUCT_NAME);
        });
    }
}
