package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends Page {

    private final static By PRODUCT_NAME = By.className("o-productDetail__description");
    private final static By PRODUCT_PRICE = By.id("priceNew");
    private final static By PRODUCT_SIZE = By.xpath("//span[contains(@class, \"m-variation__item\") and not(contains(@class,'-disabled'))]");
    private final static By ADD_TO_BASKET = By.id("addBasket");
    private final static By SHOPPING_CART = By.xpath("//span[contains(text(),'Sepetim')]");
    private final static By PRODUCT_ADDED_TO_SHOPPING_CART = By.xpath("//p[contains(text(),'Ürün sepetinize eklenmiştir.')]");

    public ProductDetailPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public String getProductName() {
        return getText(PRODUCT_NAME);
    }

    public String gerProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    public void selectRandomSize() {
        clickRandomElement(PRODUCT_SIZE);
    }

    public void addToBasket() {
        click(ADD_TO_BASKET);
        waitUntilPresent(PRODUCT_ADDED_TO_SHOPPING_CART);
    }
    public void clickShoppingCart() {
        click(SHOPPING_CART);
    }
}
