package ui.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private final SelenideElement categoriesSection = $("section.section_catalog-quick-categories");
    private final SelenideElement quickCategories = $(".catalog-quick-categories");
    private final SelenideElement topMenu = $("nav.navigation");

    public MainPage openMainPage() {
        open("/");
        categoriesSection.should(exist, Duration.ofSeconds(20));
        return this;
    }

    public MainPage activateCategoriesBlock() {
        executeJavaScript("return document.readyState").equals("complete");

        categoriesSection.scrollIntoView("{block: 'center'}");

        categoriesSection
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(interactable, Duration.ofSeconds(10))
                .click();

        quickCategories.shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    public MainPage clickCategoryByName(String categoryName) {
        quickCategories
                .$$x(".//a[contains(@class, 'catalog-quick-categories__item')]")
                .findBy(text(categoryName))
                .shouldBe(visible, Duration.ofSeconds(10))
                .scrollIntoView("{block: 'center', behavior: 'smooth'}")
                .hover()
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();
        return this;
    }

    public MainPage clickTopMenuLink(String linkText) {
        topMenu
                .$$x(".//a[contains(@class, 'navigation__link')]")
                .findBy(text(linkText))
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();
        return this;
    }

    public void verifyCategoryTitle(String expectedTitle) {
        $("h1").shouldHave(text(expectedTitle), Duration.ofSeconds(15));
    }


    public MainPage verifyPageTitle(String expectedTitle) {
        $("h1").shouldHave(text(expectedTitle), Duration.ofSeconds(15));
        return this;
    }


    private final SelenideElement searchInput = $("#title-search-input");

    public MainPage searchFor(String query) {
        searchInput
                .setValue(query)
                .pressEnter();
        return this;
    }

    public MainPage verifySearchResults(String expectedText) {
        if (expectedText.contains("ничего не найдено")) {
            $(".notetext").shouldHave(text(expectedText));
        } else {
            $(".card-product__title").shouldHave(text(expectedText));
        }
        return this;
    }

    public MainPage openCatalogSection() {
        open("/catalog/klavishnye-instrumenty/sintezatory-i-rabochie-stantsii/");
        return this;
    }

    public FilterPage openFilters() {
        return new FilterPage(); // Если фильтры сразу видны
    }
}
