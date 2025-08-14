package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

public class CartPage {
    // Селекторы для корзины с товарами
    private final SelenideElement basketHeader = $(".basket-items-list-header-filter-item.active");
    private final SelenideElement itemTable = $("#basket-item-table");
    private final SelenideElement itemArticle = $(".basket-item-property-article");

    // Селектор для пустой корзины
    private final SelenideElement emptyCartMessage = $("#basket-item-list-empty-result");

    public void verifyCartNotEmpty() {
        basketHeader.shouldBe(visible)
                .shouldHave(text("В корзине 1 товар")); // Проверяем заголовок
        itemTable.shouldBe(visible); // Проверяем таблицу с товарами
    }

    public void verifyItemPresent(String expectedArticle) {
        itemArticle.shouldBe(visible)
                .shouldHave(text(expectedArticle)); // Проверяем артикул товара
    }

    public void verifyCartIsEmpty() {
        emptyCartMessage.shouldBe(visible)
                .$(".basket-search-not-found-text")
                .shouldHave(text("По данному запросу товаров не найдено"));
    }

    // Удаление товара
    public void deleteItem(String itemArticle) {
        // Находим контейнер товара по артикулу
        SelenideElement itemContainer = $$(".basket-items-list-item-container")
                .findBy(text(itemArticle))
                .shouldBe(visible);

        // Прокручиваем к элементу, чтобы он стал видимым
        itemContainer.hover();

        // Ищем кнопку удаления среди всех возможных вариантов (и видимых и скрытых)
        itemContainer.findAll("[data-entity='basket-item-delete']")
                .filter(visible) // фильтруем только видимые
                .first() // берем первую видимую
                .shouldBe(visible, enabled) // проверяем что кликабельна
                .click(); // кликаем
    }

    // Проверка уведомления об удалении
    public void verifyItemRemovedNotification(String itemArticle) {
        $(".basket-items-list-item-notification-removed")
                .shouldBe(visible)
                .shouldHave(text("был удален из корзины")) // Проверяем часть текста
                .shouldHave(text(itemArticle)); // Проверяем наличие артикула
    }

    // Проверка видимости кнопки восстановления
    public void verifyRestoreButtonVisible() {
        $("[data-entity='basket-item-restore-button']").shouldBe(visible);
    }

    // Восстановление товара
    public void restoreItem() {
        // Прокручиваем к элементу восстановления
        $("[data-entity='basket-item-restore-button']")
                .hover()
                .click();
        $(".basket-items-list-item-notification-removed")
                .should(disappear);
    }

    public void verifyItemRestored(String itemArticle) {
        $$(".basket-items-list-item-container")
                .findBy(text(itemArticle))
                .shouldBe(visible);
    }

}
