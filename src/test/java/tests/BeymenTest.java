package tests;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.SearchPage;
import pages.ShoppingCartPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BeymenTest {

    private static Logger logger = Logger.getLogger(String.valueOf(BeymenTest.class));
    private WebDriver driver;

    @Test
    public void testScenario() throws IOException, ParseException {
        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.beymen.com");

        HomePage homePage = new HomePage(driver, logger);
        homePage.closeCookiesPopup();
        homePage.closeGenderPopup();
        homePage.clickOnSearch();

        File fs = new File("src/files/search.xlsx");
        Workbook workbook = WorkbookFactory.create(fs);
        Sheet sheet = workbook.getSheetAt(0);
        String firstSearch = sheet.getRow(0).getCell(0).toString();
        String secondSearch = sheet.getRow(0).getCell(1).toString();

        SearchPage searchPage = new SearchPage(driver, logger);
        searchPage.writeSearchText(firstSearch);
        searchPage.deleteSearch();
        searchPage.writeSearchText(secondSearch);
        searchPage.pressEnterOnSearchText();
        searchPage.clickRandomResult();

        ProductDetailPage productDetailPage = new ProductDetailPage(driver, logger);
        String productName = productDetailPage.getProductName();
        String productPriceOnDetailString = productDetailPage.gerProductPrice();

        File txtFile = new File("src/files/txtFile.txt");
        Files.deleteIfExists(txtFile.toPath());
        List<String> lines = Arrays.asList(productName, productPriceOnDetailString);
        Files.write(txtFile.toPath(), lines);

        productDetailPage.selectRandomSize();
        productDetailPage.addToBasket();
        productDetailPage.clickShoppingCart();

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver, logger);
        String productPriceStringOnShoppingCart = shoppingCartPage.getProductPrice();


        NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("tr_TR"));
        double productPriceOnDetail = format.parse(productPriceOnDetailString.replace("TL", "").replace(" ", "")).doubleValue();
        double productPriceOnBasket = format.parse(productPriceStringOnShoppingCart.replace("TL", "").replace(" ", "")).doubleValue();
        Assert.assertTrue("Fiyatlar esit degil", productPriceOnDetail == productPriceOnBasket);

        shoppingCartPage.setQuantity("2");
        shoppingCartPage.removeFromShoppingCart();
        shoppingCartPage.assertShoppingCartEmpty();

        driver.quit();
    }

}





