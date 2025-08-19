package ui.pages;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {
    private final SelenideElement categoriesContainer = $(".card-categories");
    public CatalogPage openCatalog() {
        open("/catalog");
        categoriesContainer.shouldBe(visible, Duration.ofSeconds(30));
        return this;
    }
    public CatalogPage hoverCategory(String categoryName) {
        SelenideElement category = categoriesContainer
                .$$(".card-category__title a")
                .findBy(text(categoryName))
                .scrollIntoView("{block: 'center'}");
        sleep(300);
        category.hover();
        return this;
    }
    public CatalogPage verifySubcategoriesContain(String categoryName, List<String> expectedSubcategories) {
        hoverCategory(categoryName);
        SelenideElement subcategoriesContainer = $(".card-category.-is-visible .card-category__list")
                .shouldBe(visible);
        for (String subcategory : expectedSubcategories) {
            subcategoriesContainer
                    .$$(".card-category__list-item")
                    .findBy(text(subcategory))
                    .scrollIntoView("{block: 'center'}")
                    .shouldBe(visible);
        }
        return this;
    }

    private final SelenideElement addToCartModal = $(".popup-window.--open");

    public CatalogPage openElectronicDrums() {
        open("/catalog/udarnye-instrumenty/elektronnye-udarnye/");
        return this;
    }

    public CatalogPage addToCart() {
        $(".card-product__footer .card-product__button a.button")
                .shouldBe(visible, Duration.ofSeconds(15))
                .scrollIntoView("{block: 'center'}")
                .click();
        return this;
    }

    public CatalogPage verifyAddToCartModal() {
        addToCartModal
                .shouldBe(visible)
                .$(".popup-window-titlebar-text")
                .shouldHave(text("Товар добавлен в корзину"));
        return this;
    }

    public void clickGoToCart() {
        addToCartModal
                .$x(".//button[contains(text(), 'Перейти в корзину')]")
                .click();
    }
    private final SelenideElement addToCartButton = $("#bx_3966226736_59406_7e1b8e3524755c391129a9d7e6f2d206_buy_link");
    private final SelenideElement popupCloseButton = $(".popup-window-close-icon.popup-window-titlebar-close-icon");

    public CatalogPage openElectricGuitarsSection() {
        open("https://siriusmusic.ru/catalog/gitary-i-gitarnoe-oborudovanie/elektrogitary/");
        return this;
    }

    public CatalogPage addGuitarToCart() {
        addToCartButton
                .shouldBe(visible)
                .scrollIntoView("{block: 'center'}")
                .click();
        return this;
    }

    public CatalogPage closePopup() {
        popupCloseButton.click();
        return this;
    }
}
