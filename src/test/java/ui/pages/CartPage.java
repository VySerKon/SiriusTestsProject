package ui.pages;


import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {

    private final SelenideElement basketHeader = $(".basket-items-list-header-filter-item.active");
    private final SelenideElement itemTable = $("#basket-item-table");
    private final SelenideElement itemArticle = $(".basket-item-property-article");

    public void verifyCartNotEmpty() {
        basketHeader.shouldBe(visible)
                .shouldHave(text("В корзине 1 товар"));
        itemTable.shouldBe(visible);
    }

    public void verifyItemPresent(String expectedArticle) {
        itemArticle.shouldBe(visible)
                .shouldHave(text(expectedArticle));
    }

    public void deleteItem(String itemArticle) {
        SelenideElement itemContainer = $$(".basket-items-list-item-container")
                .findBy(text(itemArticle))
                .shouldBe(visible);
                itemContainer.hover();
                itemContainer.findAll("[data-entity='basket-item-delete']")
                .filter(visible)
                .first()
                .shouldBe(visible, enabled)
                .click();
    }

    public void verifyItemRemovedNotification(String itemArticle) {
        $(".basket-items-list-item-notification-removed")
                .shouldBe(visible)
                .shouldHave(text("был удален из корзины"))
                .shouldHave(text(itemArticle));
    }

    public void verifyRestoreButtonVisible() {
        $("[data-entity='basket-item-restore-button']").shouldBe(visible);
    }


    private final SelenideElement articleNumber = $("div[data-column-property-code='PROPERTY_CML2_ARTICLE_VALUE']");
    private final SelenideElement productName = $("div[data-column-property-code='PREVIEW_TEXT'] a");

    public CartPage verifyArticleNumber(String expectedArticle) {
        articleNumber.shouldHave(text(expectedArticle));
        return this;
    }

    public CartPage verifyProductName(String expectedProductName) {
        productName.shouldHave(text(expectedProductName));
        return this;
    }

}
