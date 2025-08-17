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
}
