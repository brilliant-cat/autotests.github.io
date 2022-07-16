import com.example.Screenshot;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CartPageTest {

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
    public static void tearDown() {
        driver.quit();
    }

    @Rule
    public Screenshot rule = new Screenshot(driver, "target\\surefire-reports");

    private static By titlePageLocator = By.xpath("//*[contains(@class, 'entry-title')]");
    private static By catalogHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//*[.='Каталог']");
    private static By titlePageH2Locator = By.cssSelector(".post-title");
    private static By addToCartButtonLocator = By.xpath("(//*[.= 'В корзину'])[1]"); // на странице каталога
    private static By moreButtonLocator = By.xpath("//*[.= 'Подробнее']");
    private static By removeButtonLocator = By.className("remove");
    private static By cartEmptyLocator = By.cssSelector(".woocommerce-cart-form__cart-item.cart_item");
    private static By restoreItemLocator = By.cssSelector("a.restore-item");
    private static By cartItemLocator = By.cssSelector(".woocommerce-cart-form .cart_item");
    private static By couponCodeInputLocator = By.cssSelector("#coupon_code");
    private static By applyCouponButtonLocator = By.cssSelector("[name = 'apply_coupon']");
    private static By productPriceTotalLocator = By.className("cart-discount");
    private static By warningMessageLocator = By.className("woocommerce-error");
    private static By checkoutButtonLocator = By.className("checkout-button");
    private static By backShopButtonLocator = By.className("wc-backward");
    private static By couponLinkLocator = By.className("showcoupon");
    private static By couponSuccessMessageLocator = By.className("woocommerce-message");
    private static By couponDeleteLocator = By.className("woocommerce-remove-coupon");


    // Удаление товара со страницы корзина
    @Test
    public void testCartPage_ClickRemoveButton_DeletedProductIsTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(removeButtonLocator).click();

        //assert
        Assert.assertTrue("Товар не удален из корзины после нажатия на кнопку удаления товара", driver.findElement(cartEmptyLocator).isDisplayed());
        //Assert.assertTrue("Товар не удален из корзины после нажатия на кнопку удаления товара", driver.findElement(cartEmptyLocator).getAttribute("class").contains("cart-empty"));
        //Assert.assertFalse("Товар не удален из корзины после нажатия на кнопку удаления товара", driver.findElement(cartItemLocator).isDisplayed());
    }

    // Вернуть удаленный товар
    @Test
    public void testCartPage_ClickRestore_ProductInCartIsTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();
        driver.findElement(removeButtonLocator).click();

        //act
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(restoreItemLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        //wait.until(ExpectedConditions.elementToBeClickable(restoreItemLocator)).click();

        //assert
        Assert.assertTrue("Товар не отображается после нажатия на \"Вернуть\" товар в корзину", driver.findElement(cartItemLocator).isDisplayed());
    }

    // При нажатии на кнопку "Назад в магазин" открывается соответствующая страница
    @Test
    public void testCartPage_ClickBackToTheShop_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();
        driver.findElement(removeButtonLocator).click();

        //act
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(backShopButtonLocator)); // ожидание когда элемент будет кликабельным
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        //wait.until(ExpectedConditions.elementToBeClickable(backShopButtonLocator)).click();

        //assert
        var expectedTitle = "ВСЕ ТОВАРЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // При отправке валидного купона отображается скидка
    @Test
    public void testCartPage_ApplyValidCoupon_SaleIsDisplayed() {
        //arrange
        var validCouponDataLocator = "sert500";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(couponCodeInputLocator).sendKeys(validCouponDataLocator);
        driver.findElement(applyCouponButtonLocator).click();

        //assert
        Assert.assertTrue("Скидка не отображается", driver.findElement(productPriceTotalLocator).isDisplayed());
        driver.findElement(couponDeleteLocator).click();
    }

    // При отправке пустого поля для ввода купона отображается соответствующее сообщение
    @Test
    public void testCartPage_ApplyEmptyCoupon_warningMessageIsDisplayed() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(applyCouponButtonLocator).click();

        //assert
        Assert.assertTrue("Предупреждающее сообщение не отображается", driver.findElement(warningMessageLocator).isDisplayed());
        var expectedMassage = "Пожалуйста, введите код купона.";
        var actualMassage = driver.findElement(warningMessageLocator).getText();
        Assert.assertEquals("Текст сообщения не совпадает", expectedMassage, actualMassage);
    }

    // При отправке невалидного купона отображается соответствующее сообщение
    @Test
    public void testCartPage_ApplyInvalidCoupon_warningMessageIsDisplayed() {
        //arrange
        var invalidCouponDataLocator = "sert50O";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(couponCodeInputLocator).sendKeys(invalidCouponDataLocator);
        driver.findElement(applyCouponButtonLocator).click();

        //assert
        Assert.assertTrue("Предупреждающее сообщение не отображается", driver.findElement(warningMessageLocator).isDisplayed());
        var expectedMassage = "Неверный купон.";
        var actualMassage = driver.findElement(warningMessageLocator).getText();
        Assert.assertEquals("Текст сообщения не совпадает", expectedMassage, actualMassage);
    }

    // При нажатии на кнопку "Оформить заказ" открывается соответствующая страница
    @Test
    public void testCartPage_ClickCheckoutButton_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(checkoutButtonLocator).click();

        //assert
        var expectedTitle = "Оформление заказа";
        var actualTitle = driver.findElement(titlePageH2Locator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // Ввод купона со страницы оформления заказа
    @Test
    public void testCheckoutPage_ApplyValidCoupon_SuccessMessageIsDisplayed() {
        //arrange
        var validCouponDataLocator = "sert500";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();
        driver.findElement(checkoutButtonLocator).click();

        //act
        driver.findElement(couponLinkLocator).click();
        driver.findElement(couponCodeInputLocator).sendKeys(validCouponDataLocator);
        driver.findElement(applyCouponButtonLocator).click();

        //assert
        Assert.assertTrue("Сообщение не отображается", driver.findElement(couponSuccessMessageLocator).isDisplayed());
        var expectedMessage = "Купон успешно добавлен.";
        var actualMessage = driver.findElement(couponSuccessMessageLocator).getText();
        Assert.assertTrue(String.format("Текст сообщения не совпадает. Сейчас: %s, Ожидали: %s", actualMessage, expectedMessage), actualMessage.contains(expectedMessage));
    }
}
