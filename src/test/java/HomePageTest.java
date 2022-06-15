import com.example.Screenshot;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HomePageTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    //    @AfterClass
//    public static void tearDown() throws IOException {
//        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(sourceFile, new File("c:\\Users\\User\\Pictures\\autotest_Java_screenshot\\screenshot.jpg"));
//        driver.quit();
//    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Rule
    public Screenshot rule = new Screenshot(driver, "target\\surefire-reports");

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
}
