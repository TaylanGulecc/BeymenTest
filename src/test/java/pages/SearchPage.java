package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class SearchPage extends Page {

    public final static By SEARCH_BOX = By.id("o-searchSuggestion__input");
    public final static By DELETE_SEARCH = By.xpath("//button[contains(text(),'Sil')]");
    public final static By PRODUCT_ITEM = By.xpath("//div[@class=\"o-productList__item\"]");
    public SearchPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public void writeSearchText(String searchText){
        sendKeys(SEARCH_BOX, searchText);
    }

    public void deleteSearch(){
        click(DELETE_SEARCH);
    }

    public void pressEnterOnSearchText(){
        sendKeys(SEARCH_BOX, Keys.ENTER);
    }

    public void clickRandomResult() {
        super.clickRandomElement(PRODUCT_ITEM);
    }
}
