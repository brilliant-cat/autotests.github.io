import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
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
    public static void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("c:\\Users\\User\\Pictures\\autotest_Java_screenshot\\screenshot.jpg"));
        driver.quit();
    }

    private static By titlePageLocator = By.xpath("//*[contains(@class, 'entry-title')]");
    private static By catalogHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//*[.='Каталог']");
    private static By titlePageCheckoutLocator = By.cssSelector(".post-title");
    private static By addToCartButtonLocator = By.xpath("(//*[.= 'В корзину'])[1]"); // на странице каталога
    private static By moreButtonLocator = By.xpath("//*[.= 'Подробнее']");
    private static By removeButtonLocator = By.className("remove");
    private static By cartEmptyLocator = By.cssSelector(".woocommerce-cart-form__cart-item.cart_item");
    private static By restoreItemLocator = By.cssSelector("a.restore-item");
    private static By cartItemLocator = By.cssSelector(".woocommerce-cart-form .cart_item");

    // Удаление товара со страницы корзина
    @Test
    public void testCartPage_TapRemoveButton_DeletedProductIsTrue() {
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
    public void testCartPage_TapRestore_ProductInCartIsTrue() {
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
    public void testCartPage_TapBackToTheShop_TitlePageTrue() {
        //arrange
        var backShopButtonLocator = By.className("wc-backward");

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();
        driver.findElement(removeButtonLocator).click();

        //act
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(backShopButtonLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        //wait.until(ExpectedConditions.elementToBeClickable(backShopButtonLocator)).click();

        //assert
        var expectedTitle = "ВСЕ ТОВАРЫ";
        var actualTitle = driver.findElement(titlePageLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    private static By couponCodeInputLocator = By.cssSelector("#coupon_code");
    private static By applyCouponButtonLocator = By.cssSelector("[name = 'apply_coupon']");

    // При отправке валидного купона отображается скидка
    @Test
    public void testCartPage_ApplyValidCoupon_SaleIsDisplayed() {
        //arrange
        var productPriceTotalLocator = By.className("cart-discount");
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
    }

    // При отправке пустого поля для ввода купона отображается соответствующее сообщение
    @Test
    public void testCartPage_ApplyEmptyCoupon_warningMessageIsDisplayed() {
        //arrange
        var warningMessageLocator = By.className("woocommerce-error");

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

    // При отправке не валидного купона отображается соответствующее сообщение
    @Test
    public void testCartPage_ApplyInvalidCoupon_warningMessageIsDisplayed() {
        //arrange
        var warningMessageLocator = By.className("woocommerce-error");
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

    private static By checkoutButtonLocator = By.className("checkout-button");

    // При нажатии на кнопку "Оформить заказ" открывается соответствующая страница
    @Test
    public void testCartPage_TapCheckoutButton_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();

        //act
        driver.findElement(checkoutButtonLocator).click();

        //assert
        var expectedTitle = "Оформление заказа";
        var actualTitle = driver.findElement(titlePageCheckoutLocator).getText();
        Assert.assertTrue(String.format("Заголовок страницы не соответствует. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }
}
