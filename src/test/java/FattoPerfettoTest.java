import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FattoPerfettoTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("c:\\Users\\User\\Pictures\\autotest_Java_screenshot\\screenshot.jpg"));
        driver.quit();
    }

    private static By booksCategoryCardLocator = By.xpath("//*[.='Книги']/../*[contains(@class, 'promo-link-btn')]");
    private static By titlePageLocator = By.xpath("//*[contains(@class, 'entry-title')]");
    private static By tabletsCategoryCardLocator = By.xpath("//*[.='Планшеты']/../*[contains(@class, 'promo-link-btn')]");
    private static By camerasCategoryCardLocator = By.xpath("//*[.='Фотоаппараты']/../*[contains(@class, 'promo-link-btn')]");
    private static By catalogHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//*[.='Каталог']");
    private static By myAccountHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//a[.='Мой аккаунт']");
    private static By titlePageMyAccountLocator = By.xpath("//*[contains(@class, 'post-title')]");
    private static By cartHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//a[.='Корзина']");
    private static By titlePageCartLocator = By.xpath("//span[.='Корзина']");
    private static By checkoutHeaderMenuLocator = By.cssSelector(".store-menu [href$='checkout/']");
    private static By titlePageCheckoutLocator = By.cssSelector(".post-title");
    private static By appliancesSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//*[.='Бытовая техника']");
    private static By refrigeratorsSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Холодильники']");
    private static By laundryWashersSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Стиральные машины']");
    private static By electronicsSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Электроника']");
    private static By phonesSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Телефоны']");
    private static By tabletsSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Планшеты']");
    private static By tvSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Телевизоры']");
    private static By photosVideosSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Фото/видео']");
    private static By watchesSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Часы']");
    private static By booksSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Книги']");
    private static By clothesSubMenuLocator = By.xpath("//*[contains(@class, 'sub-menu')]//a[.='Одежда']");
    private static By homePageLocator = By.xpath("//*[contains(@class, 'store-menu')]//a[.='Главная']");

    // При нажатии на категорию "Книги" на главной странице открывается соответствующая страница
    @Test
    public void testHomePage_TapCategoryBooks_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        //wait.until(ExpectedConditions.textToBePresentInElementLocated(booksCategoryCardLocator, "Книги"));
        driver.findElement(booksCategoryCardLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Книги\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "КНИГИ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на категорию "Планшеты" на главной странице открывается соответствующая страница
    @Test
    public void testHomePage_TapCategoryTablets_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(tabletsCategoryCardLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Планшеты\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ПЛАНШЕТЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на категорию "Фотоаппараты" на главной странице открывается соответствующая страница
    @Test
    public void testHomePage_TapCategoryCameras_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(camerasCategoryCardLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Фотоаппараты\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ФОТО/ВИДЕО";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Каталог" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapHeaderMenuCatalog_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Каталог\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "КАТАЛОГ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Мой аккаунт" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapHeaderMenuMyAccount_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(myAccountHeaderMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Мой аккаунт\" не отображается", driver.findElement(titlePageMyAccountLocator).isDisplayed());
        var expectedTitle = "Мой аккаунт";
        var actualTitle = driver.findElement(titlePageMyAccountLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Корзина" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapHeaderMenuCart_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(cartHeaderMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Корзина\" не отображается", driver.findElement(titlePageCartLocator).isDisplayed());
        var expectedTitle = "Корзина";
        var actualTitle = driver.findElement(titlePageCartLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Оформление заказа" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapHeaderMenuCheckout_TitlePageTrue() {
        //arrange
        var addToCartButtonLocator = By.xpath("(//li[contains(@aria-hidden, 'false')]//*[.= 'В корзину'])[1]"); // на главной странице
        var moreButtonLocator = By.xpath("//*[.= 'Подробнее']");

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator);

        //act
        driver.findElement(checkoutHeaderMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Оформление заказа\" не отображается", driver.findElement(titlePageCheckoutLocator).isDisplayed());
        var expectedTitle = "Оформление заказа";
        var actualTitle = driver.findElement(titlePageCheckoutLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Бытовая техника" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuAppliances_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Бытовая техника\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "БЫТОВАЯ ТЕХНИКА";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Холодильники" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuRefrigerators_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(refrigeratorsSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Холодильники\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ХОЛОДИЛЬНИКИ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Стиральные машины" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuLaundryWashers_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(refrigeratorsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(laundryWashersSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Стиральные машины\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "СТИРАЛЬНЫЕ МАШИНЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Электроника" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuElectronics_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Электроника\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ЭЛЕКТРОНИКА";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Телефоны" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuPhones_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(phonesSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Телефоны\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ТЕЛЕФОНЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Планшеты" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuTablets_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(phonesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tabletsSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Планшеты\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ПЛАНШЕТЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Телевизоры" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuTv_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(phonesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tabletsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tvSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Телевизоры\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ТЕЛЕВИЗОРЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Фото/видео" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuPhotosVideos_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(phonesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tabletsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tvSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(photosVideosSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Фото/видео\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ФОТО/ВИДЕО";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Часы" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuWatches_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(phonesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tabletsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(tvSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(photosVideosSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(watchesSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Часы\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ЧАСЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Книги" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuBooks_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(booksSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Книги\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "КНИГИ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При нажатии на ссылку "Одежда" в главном меню сайта открывается соответствующая страница
    @Test
    public void testHomePage_TapSubMenuClothes_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).sendKeys(Keys.TAB);
        driver.findElement(appliancesSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(electronicsSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(booksSubMenuLocator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(clothesSubMenuLocator).click();

        //assert
        Assert.assertTrue("Заголовок \"Одежда\" не отображается", driver.findElement(titlePageLocator).isDisplayed());
        var expectedTitle = "ОДЕЖДА";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы категории. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // После просмотра любого товара он отображается в разделе "Просмотренные товары" на главной странице сайта
    @Test
    public void testHomePage_ViewedProducts_ViewedProductIsDisplayed() {
        //arrange
        var firstProductTabletsCategoryLocator = By.xpath("(//ul[contains(@class, 'product')]//li)[1]");
        var viewedProductsSectionLocator = By.cssSelector("h2.widget-title");
        var titleViewedProductsSectionLocator = By.cssSelector("#woocommerce_recently_viewed_products-2 .product-title");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(tabletsCategoryCardLocator).click();
        driver.findElement(firstProductTabletsCategoryLocator).click();
        driver.findElement(homePageLocator).click();
        driver.findElement(homePageLocator).sendKeys(Keys.PAGE_DOWN);

        //assert
        Assert.assertTrue("Секция \"Просмотренные товары\" не отображается", driver.findElement(viewedProductsSectionLocator).isDisplayed());
        var expectedTitle = "iPad 2020 32gb wi-fi";
        var actualTitle = driver.findElement(titleViewedProductsSectionLocator).getText();
        Assert.assertTrue(String.format("Не найден заголовок продукта просмотренного товара в секции \"Просмотренные товары\". Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

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
        var nextPageButton = By.className("next");

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(catalogLinkLocator).click();


        //assert
        var expectedTitle = driver.findElement(catalogLinkLocator).getText().toUpperCase();
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
        var expectedCount = driver.findElement(catalogLinkCountLocator).getText();
        var actualCount = driver.findElement(productCountLocator).getText().substring(driver.findElement(productCountLocator).getText().lastIndexOf(' '));
        Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", actualCount.trim()));
//        var expectedCount = driver.findElement(catalogLinkCountLocator).getText();
//        var actualCount = driver.findElements(productCountLocator);
//        var totalActualCount = 0;
//        var rule = driver.findElements(nextPageButton).size();
//        driver.findElement(catalogLinkLocator).sendKeys(Keys.END);
//        while (true) {
//            if (rule != 0) {
//                driver.findElement(nextPageButton).click();
//                driver.findElements(productCountLocator);
//                totalActualCount += actualCount.size();
//                driver.findElement(catalogLinkLocator).sendKeys(Keys.END);
//            } else {
//                Assert.assertEquals("Число товаров на странице категории не соответствует.", expectedCount, String.format("(%s)", totalActualCount));
//            }
//
//        }
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
