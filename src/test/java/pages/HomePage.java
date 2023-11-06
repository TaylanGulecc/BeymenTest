package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

public class HomePage extends Page {

    public final static By SEARCH_BOX = By.xpath("//input[@class=\"o-header__search--input\"]");
    public final static By GENDER_POPUP_CLOSE = By.xpath("/html/body/div[3]/div[6]/div[2]/div/div[1]/button");
    public final static By ACCEPT_COOKIES = By.xpath("//button[@id='onetrust-accept-btn-handler']");

    public HomePage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public void closeCookiesPopup() {
        logger.info("Çerez pop-up görüntülenir ve tüm çerezleri kabul et'e tıklanır");
        click(ACCEPT_COOKIES);
    }

    public void closeGenderPopup() {
        logger.info("cinsiyet seçim pop-up'ı görüntülenir ve cinsiyet seçilir.");
        click(GENDER_POPUP_CLOSE);
    }

    public void clickOnSearch() {
        click(SEARCH_BOX);
    }
}
