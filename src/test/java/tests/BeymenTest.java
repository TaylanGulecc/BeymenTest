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
import org.openqa.selenium.remote.Browser;
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
    }






//    @Test
//    public void beymenTestScenario() throws InterruptedException, IOException {
//        File fs = new File("src/files/search.xlsx");
////Creating a workbook
//        Workbook workbook = WorkbookFactory.create(fs);
//        Sheet sheet = workbook.getSheetAt(0);
//        String firstSearch = sheet.getRow(0).getCell(0).toString();
//        String secondSearch = sheet.getRow(0).getCell(1).toString();
//
//        logger.info("Setup kuruluyor");
//        System.setProperty("webdriver.chrome.driver", "src/driver/chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        logger.info("https://www.beymen.com'a gidilir");
//        driver.get("https://www.beymen.com");
//        Thread.sleep(2000);
//        logger.info("Çerez pop-up görüntülenir ve tüm çerezleri kabul et'e tıklanır");
//        driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).click();
//        logger.info("cinsiyet seçim pop-up'ı görüntülenir ve cinsiyet seçilir.");
//        driver.findElement(By.xpath("//button[@id='genderManButton']")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//input[@class=\"o-header__search--input\"]")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.id("o-searchSuggestion__input")).sendKeys(firstSearch);
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//button[contains(text(),'Sil')]")).click();
//        driver.findElement(By.id("o-searchSuggestion__input")).sendKeys(secondSearch);
//        driver.findElement(By.id("o-searchSuggestion__input")).sendKeys(Keys.ENTER);
//        int productCount = driver.findElements(By.xpath("//div[@class=\"o-productList__item\"]")).size();
//        int randomProduct = new Random().nextInt(productCount);
//        driver.findElements(By.xpath("//div[@class=\"o-productList__item\"]")).get(randomProduct).click();
//        String productDescription = driver.findElement(By.className("o-productDetail__description")).getText();
//        logger.info(productDescription);
//        String productPriceString = driver.findElement(By.id("priceNew")).getText();
//
//        File txtFile = new File("src/files/txtFile.txt");
//        Files.deleteIfExists(txtFile.toPath());
////        Files.createFile(txtFile.toPath());
//        List<String> lines = Arrays.asList(productDescription, productPriceString);
//        Files.write(txtFile.toPath(), lines, StandardCharsets.UTF_8);
//
//        logger.info(productPriceString);
//        List<WebElement> sizeWebElements = driver.findElements(By.xpath("//span[contains(@class, \"m-variation__item\") and not(contains(@class,'-disabled'))]"));
//        int randomProductSize = new Random().nextInt(sizeWebElements.size());
//        sizeWebElements.get(randomProductSize).click();
//        driver.findElement(By.id("addBasket")).click();
//        logger.info("ürünün sepete eklendiğinin bildirimi görünür.");
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//span[contains(text(),'Sepetim')]")).click();
//        Thread.sleep(2000);
//        String productPriceOnBasketString = driver.findElement(By.className("m-productPrice__salePrice")).getText();
//        logger.info(productPriceOnBasketString);
//
//        try {
//            NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("tr_TR"));
//            double productPrice = format.parse(productPriceString.replace("TL", "").replace(" ", "")).doubleValue();
//            double productPriceOnBasket = format.parse(productPriceOnBasketString.replace("TL", "").replace(" ", "")).doubleValue();
//            Assert.assertTrue("Fiyatlar esit degil", productPrice == productPriceOnBasket);
//        } catch (ParseException e) {
//            Assert.fail("Fiyatlar parse edilemedi");
//        }
//
//        driver.findElement(By.id("quantitySelect0-key-0"));
//        driver.findElement(By.xpath("//option[@value='2']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("removeCartItemBtn0-key-0")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[contains(text(),'Sepetinizde Ürün Bulunmamaktadır')]"));
//
//
//    }


}