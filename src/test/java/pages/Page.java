package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Page {

    WebDriver driver;
    WebDriverWait wait;
    Logger logger;

    public Page(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitUntilPresent(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void click(By by) {
        waitUntilPresent(by);
        driver.findElement(by).click();
    }

    public void sendKeys(By by, String keys) {
        waitUntilPresent(by);
        driver.findElement(by).sendKeys(keys);
    }

    public void sendKeys(By by, Keys keys) {
        waitUntilPresent(by);
        driver.findElement(by).sendKeys(keys);
    }

    public int countElements(By by) {
        waitUntilPresent(by);
        return driver.findElements(by).size();
    }

    public void clickRandomElement(By by) {
        waitUntilPresent(by);
        List<WebElement> elements = driver.findElements(by);
        int randomNumber = new Random().nextInt(elements.size());
        elements.get(randomNumber).click();
    }

    public String getText(By by) {
        waitUntilPresent(by);
        return driver.findElement(by).getText();
    }

}
