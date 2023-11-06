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
        logger.info("Chrome browser açılır ve beymen sitesine girilir.");
        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.beymen.com");

        logger.info("Ana sayfada cookies pop-up görüntülenir,çerezler kabul edilir ve cinsiyet seçimi yapılır.");
        HomePage homePage = new HomePage(driver, logger);
        homePage.closeCookiesPopup();
        homePage.closeGenderPopup();
        logger.info("Ana sayfada arama kutucuğuna tıklanır.");
        homePage.clickOnSearch();

        logger.info("Excel dosyası oluşturulur ve bu dosyadan gömlek ve şort kelimeleri çekilir.");
        File fs = new File("src/files/search.xlsx");
        Workbook workbook = WorkbookFactory.create(fs);
        Sheet sheet = workbook.getSheetAt(0);
        String firstSearch = sheet.getRow(0).getCell(0).toString();
        String secondSearch = sheet.getRow(0).getCell(1).toString();

        logger.info("şort kelimesi yazılıp silinir ve gömlek kelimesi yazılıp enter tuşuna basılır.");
        SearchPage searchPage = new SearchPage(driver, logger);
        searchPage.writeSearchText(firstSearch);
        searchPage.deleteSearch();
        searchPage.writeSearchText(secondSearch);
        searchPage.pressEnterOnSearchText();
        logger.info("Rastgele bir ürüne tıklanır.");
        searchPage.clickRandomResult();

        logger.info("Ürün bilgisi ve tutar bilgisi alınır ");
        ProductDetailPage productDetailPage = new ProductDetailPage(driver, logger);
        String productName = productDetailPage.getProductName();
        String productPriceOnDetailString = productDetailPage.gerProductPrice();

        logger.info("Ürün bilgisi ve tutar bilgisi 'txtFile' dosyasına yazdırılır.");
        File txtFile = new File("src/files/txtFile.txt");
        Files.deleteIfExists(txtFile.toPath());
        List<String> lines = Arrays.asList(productName, productPriceOnDetailString);
        Files.write(txtFile.toPath(), lines);

        logger.info("Random bir ürün bedeni seçilerek ürün sepete eklenir.");
        productDetailPage.selectRandomSize();
        productDetailPage.addToBasket();
        logger.info("Sepet ikonuna tıklanır.");
        productDetailPage.clickShoppingCart();

        logger.info("Sepetteki ürünün tutar bilgisi alınır.");
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver, logger);
        String productPriceStringOnShoppingCart = shoppingCartPage.getProductPrice();

        logger.info("Alınan iki fiyat tutarı Türkiye standartlarına uygun formata çevrilip tutarların karşılaştırmaları yapılır.");
        NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("tr_TR"));
        double productPriceOnDetail = format.parse(productPriceOnDetailString.replace("TL", "").replace(" ", "")).doubleValue();
        double productPriceOnBasket = format.parse(productPriceStringOnShoppingCart.replace("TL", "").replace(" ", "")).doubleValue();
        Assert.assertTrue("Fiyatlar esit degil", productPriceOnDetail == productPriceOnBasket);

        logger.info("Ürün adeti 2'ye çıkarılır ve ürün sepetten silinerek sepetin boş olduğu görüntülenir.");
        shoppingCartPage.setQuantity("2");
        shoppingCartPage.removeFromShoppingCart();
        shoppingCartPage.assertShoppingCartEmpty();

        logger.info("Test sonunda browser kapatılır.");
        driver.quit();
    }

}





