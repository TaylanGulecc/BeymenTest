package pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends Page {

    private final static By PRODUCT_PRICE = By.className("m-productPrice__salePrice");
    private final static By QUANTITY_SELECTOR = By.id("quantitySelect0-key-0");
    private final static By REMOVE_FROM_SHOPPING_CART = By.id("removeCartItemBtn0-key-0");
    private final static By SHOPPING_CART_EMPTY = By.xpath("//*[contains(text(),'Sepetinizde Ürün Bulunmamaktadır')]");

    public ShoppingCartPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    public void setQuantity(String quantity) {
        waitUntilPresent(QUANTITY_SELECTOR);
        click(By.xpath("//option[@value='" + quantity + "']"));
    }
    public void removeFromShoppingCart() {
        waitUntilPresent(REMOVE_FROM_SHOPPING_CART);
        click(REMOVE_FROM_SHOPPING_CART);
    }

    public void assertShoppingCartEmpty() {
        Assert.assertTrue("Sepet bos degil", countElements(SHOPPING_CART_EMPTY)>0);
    }
}
