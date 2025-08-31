package ui.tests.unauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.TestBase;
import ui.pages.CatalogPage;
import ui.pages.CartPage;
import ui.pages.MainPage;

import static io.qameta.allure.Allure.step;


@DisplayName("Тесты корзины товаров")
@Tag("WEB")
@Tag("CART")
   public class CartTest extends TestBase {

    private static final String EXPECTED_ARTICLE = "STD MN GBK";
    private static final String EXPECTED_PRODUCT_NAME = "EVH Wolfgang STD MN GBK Gloss Black электрогитара";

    CatalogPage catalogPage = new CatalogPage();
    MainPage mainPage = new MainPage();
    CartPage cartPage = new CartPage();

    @Test
    @DisplayName("Добавление электрогитары в корзину и проверка содержимого")
    void testAddElectricGuitarToCartAndVerify() {
        step("Открыть раздел каталога: 'Электрогитары'", () -> {
            catalogPage.openElectricGuitarsSection();
        });

        step("Добавить электрогитару EVH Wolfgang в корзину", () -> {
            catalogPage.addGuitarToCart();
        });

        step("Закрыть всплывающее окно добавления товара", () -> {
            catalogPage.closePopup();
        });

        step("Перейти в корзину", () -> {
            mainPage.openCart();
        });

        step("Проверить, что артикул товара соответствует ожидаемому: " + EXPECTED_ARTICLE, () -> {
            cartPage.verifyArticleNumber(EXPECTED_ARTICLE);
        });

        step("Проверить, что название товара соответствует ожидаемому: " + EXPECTED_PRODUCT_NAME, () -> {
            cartPage.verifyProductName(EXPECTED_PRODUCT_NAME);
        });
    }
}
