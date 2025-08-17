package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterPage {
    private final SelenideElement minPriceInput = $("input#arCatalogFilter_125_MIN");
    private final SelenideElement maxPriceInput = $("input#arCatalogFilter_125_MAX");
    private final SelenideElement casioCheckbox = $("label[for='arCatalogFilter_55_3300509212']");
    private final SelenideElement applyButton = $("input#set_filter");

    public FilterPage scrollToFilters() {
        minPriceInput.scrollIntoView("{block: 'center'}");
        return this;
    }

    public FilterPage clearPriceFields() {
        scrollToFilters();
        executeJavaScript(
                "arguments[0].value = ''; " +
                        "arguments[0].dispatchEvent(new Event('input')); " +
                        "smartFilter.keyup(arguments[0]);",
                minPriceInput
        );

        executeJavaScript(
                "arguments[0].value = ''; " +
                        "arguments[0].dispatchEvent(new Event('input')); " +
                        "smartFilter.keyup(arguments[0]);",
                maxPriceInput
        );


        minPriceInput.shouldHave(value(""));
        maxPriceInput.shouldHave(value(""));
        return this;
    }

    public FilterPage setPriceRange(int min, int max) {
        // Установка значений через JavaScript с триггером событий
        setPriceWithJs(minPriceInput, min);
        setPriceWithJs(maxPriceInput, max);
        return this;
    }

    private void setPriceWithJs(SelenideElement element, int value) {
        executeJavaScript(
                "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input')); " +
                        "smartFilter.keyup(arguments[0]);",
                element,
                value
        );
        element.shouldHave(value(String.valueOf(value)));
    }

    public FilterPage selectCasio() {
        casioCheckbox.scrollIntoView("{block: 'center'}");
        casioCheckbox.shouldBe(visible, Duration.ofSeconds(5)).click();
        return this;
    }

    public void applyAndVerify() {
        applyButton.scrollIntoView("{block: 'center'}");
        applyButton.shouldBe(enabled).click();
        verifyResults();
    }

    private void verifyResults() {
        $(".layout__content").scrollIntoView("{block: 'center'}");
        $$(".card-product__title").shouldHave(sizeGreaterThan(0))
                .forEach(title -> title.shouldHave(text("Casio")));
        $$(".card-product__price").forEach(priceElement -> {
            String priceText = priceElement.getText().replaceAll("\\D", "");
            int price = Integer.parseInt(priceText.isEmpty() ? "0" : priceText);
            assertThat(price).isBetween(10_000, 20_000);
        });
    }
}
