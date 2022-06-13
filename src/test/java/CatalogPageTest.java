import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CatalogPageTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("c:\\Users\\User\\Pictures\\autotest_Java_screenshot\\screenshot.jpg"));
        driver.quit();
    }

    private static By titlePageLocator = By.xpath("//*[contains(@class, 'entry-title')]");
    private static By catalogHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//*[.='Каталог']");
    private static By titlePageCartLocator = By.xpath("//span[.='Корзина']");

    // Отображаемые товары на странице результатов соответствуют запросу поиска
    @Test
    public void testHomePage_SearchProducts_SearchResultIsTrue() {
        //arrange
        var requestLocator = "canon";
        var searchFieldLocator = By.className("search-field");
        var searchButtonLocator = By.className("searchsubmit");
        var titleProductsSearchResultLocator = By.cssSelector("a.collection_title[href*= 'canon']");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(searchFieldLocator).sendKeys(requestLocator);
        driver.findElement(searchButtonLocator).click();

        //assert
        Assert.assertTrue("Заголовки товаров не соответствуют поиску запроса", driver.findElement(titleProductsSearchResultLocator).isDisplayed());
    }

    // Название кнопки "В корзину" меняется на "Подробнее" при нажатии на нее
    @Test
    public void testCatalogPage_TapAddToCartButton_TitleButtonTrue() {
        //arrange
        var addToCartButtonLocator = By.xpath("(//*[.= 'В корзину'])[1]"); // на странице каталога
        var moreButtonLocator = By.xpath("//*[.= 'Подробнее']");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();

        //assert
        var expectedTitle = "ПОДРОБНЕЕ";
        var actualTitle = driver.findElement(moreButtonLocator).getText();
        Assert.assertTrue(String.format("Название кнопки не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на кнопку "Подробнее" открывается страница Корзины
    @Test
    public void testCatalogPage_TapMoreButton_OpenCheckoutPage() {
        //arrange
        var addToCartButtonLocator = By.xpath("(//*[.= 'В корзину'])[1]"); // на странице каталога
        var moreButtonLocator = By.xpath("//*[.= 'Подробнее']");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //assert
        var expectedTitle = "Корзина";
        var actualTitle = driver.findElement(titlePageCartLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на страницу "2" открывается соответствующая страница (кнопка "2" не активна)
    @Test
    public void testCatalogPage_TapTwoNumberButton_OpenTwoNumberPage() {
        //arrange
        var twoNumberButtonLocator = By.xpath("//a[.= '2']");
        var moreButtonLocator = By.cssSelector("span[aria-current]");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.END);
        driver.findElement(twoNumberButtonLocator).click();
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.PAGE_DOWN);

        //assert
        var expectedNumber = "2";
        var actualNumber = driver.findElement(moreButtonLocator).getText();
        Assert.assertTrue(String.format("Номер страницы не соответствует. Сейчас: %s, Ожидали: %s", actualNumber, expectedNumber), actualNumber.contains(expectedNumber));
    }

    private static By productCountLocator = By.className("woocommerce-result-count");
    private static By resultCountLocator = By.className("product");

    // При нажатии на категорию "Без категории" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapWithoutCategoryLink_TitlePageTrue() {
        //arrange
        var withoutCategoryLocator = By.xpath("//*[.= 'Без категории']");
        var withoutCategoryCountLocator = By.xpath("//*[.= 'Без категории']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(withoutCategoryLocator).click();


        //assert
        var expectedTitle = driver.findElement(withoutCategoryLocator).getText();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(withoutCategoryCountLocator).getText();
        var actualCount = driver.findElements(resultCountLocator);
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount.size()));
    }

    // При нажатии на категорию "Бытовая техника" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapAppliancesLink_TitlePageTrue() {
        //arrange
        var appliancesCategoryLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Бытовая техника']");
        var appliancesCategoryCountLocator = By.xpath("//*[.= 'Бытовая техника']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(appliancesCategoryLocator).click();


        //assert
        var expectedTitle = driver.findElement(appliancesCategoryLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(appliancesCategoryCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Каталог" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapCatalogLink_TitlePageTrue() {
        //arrange
        var catalogLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Каталог']");
        var catalogLinkCountLocator = By.xpath("//*[.= 'Каталог']/following-sibling::span");
        var nextPageButton = By.cssSelector(".next.page-numbers");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(catalogLinkLocator).click();

        //assert
        var expectedTitle = driver.findElement(catalogLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(catalogLinkCountLocator).getText();

        var actualCount = 0 + driver.findElements(resultCountLocator).size();
        driver.findElement(catalogLinkLocator).sendKeys(Keys.DOWN);

        while (driver.findElements(nextPageButton).size() > 0) {
            driver.findElement(nextPageButton).click();
            actualCount += driver.findElements(resultCountLocator).size();
            driver.findElement(catalogLinkLocator).sendKeys(Keys.DOWN);
        }
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Книги" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapBooksLink_TitlePageTrue() {
        //arrange
        var booksLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Книги']");
        var booksLinkCountLocator = By.xpath("//*[.= 'Книги']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(booksLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(booksLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(booksLinkCountLocator).getText();
        var actualCount = driver.findElements(resultCountLocator);
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount.size()));
    }

    // При нажатии на категорию "Одежда" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapClothesLink_TitlePageTrue() {
        //arrange
        var clothesLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Одежда']");
        var clothesLinkCountLocator = By.xpath("//*[.= 'Одежда']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(clothesLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(clothesLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(clothesLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Планшеты" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapTabletsLink_TitlePageTrue() {
        //arrange
        var tabletsLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Планшеты']");
        var tabletsLinkCountLocator = By.xpath("//*[.= 'Планшеты']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(tabletsLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(tabletsLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(tabletsLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Стиральные машины" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapLaundryWashersLink_TitlePageTrue() {
        //arrange
        var laundryWashersLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Стиральные машины']");
        var laundryWashersLinkCountLocator = By.xpath("//*[.= 'Стиральные машины']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(laundryWashersLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(laundryWashersLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(laundryWashersLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Телевизоры" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapTvLink_TitlePageTrue() {
        //arrange
        var tvLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Телевизоры']");
        var tvLinkCountLocator = By.xpath("//*[.= 'Телевизоры']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(tvLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(tvLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(tvLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Телефоны" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapPhonesLink_TitlePageTrue() {
        //arrange
        var phonesLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Телефоны']");
        var phonesLinkCountLocator = By.xpath("//*[.= 'Телефоны']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(phonesLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(phonesLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(phonesLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().substring(driver.findElement(productCountLocator).getText().lastIndexOf(' '));
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount.trim()));
    }

    // При нажатии на категорию "Фото/видео" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapPhotosAndVideosLink_TitlePageTrue() {
        //arrange
        var photosAndVideosLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Фото/видео']");
        var photosAndVideosLinkCountLocator = By.xpath("//*[.= 'Фото/видео']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(photosAndVideosLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(photosAndVideosLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(photosAndVideosLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Холодильники" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapRefrigeratorsLink_TitlePageTrue() {
        //arrange
        var refrigeratorsLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Холодильники']");
        var refrigeratorsLinkCountLocator = By.xpath("//*[.= 'Холодильники']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(refrigeratorsLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(refrigeratorsLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(refrigeratorsLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Часы" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapWatchesLink_TitlePageTrue() {
        //arrange
        var watchesLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Часы']");
        var watchesLinkCountLocator = By.xpath("//*[.= 'Часы']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(watchesLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(watchesLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(watchesLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().replaceAll("[^0-9]+", "");
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount));
    }

    // При нажатии на категорию "Электроника" открывается соответствующая страница (количество товаров соответствует)
    @Test
    public void testCatalogPage_TapElectronicsLink_TitlePageTrue() {
        //arrange
        var electronicsLinkLocator = By.xpath("//*[contains(@class, 'cat-item')]/*[.= 'Электроника']");
        var electronicsLinkCountLocator = By.xpath("//*[.= 'Электроника']/following-sibling::span");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(electronicsLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(electronicsLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(electronicsLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().substring(driver.findElement(productCountLocator).getText().lastIndexOf(' '));
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount.trim()));
    }
}
