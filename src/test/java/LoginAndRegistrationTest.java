import com.example.Screenshot;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginAndRegistrationTest {

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

    private static By accountLocator = By.className("account");
    private static By registerButtonLocator = By.className("custom-register-button");
    private static By usernameRegLocator = By.id("reg_username");
    private static By emailRegLocator = By.id("reg_email");
    private static By passwordRegLocator = By.id("reg_password");
    private static By registerButtonSubmitLocator = By.className("woocommerce-form-register__submit");
    private static By registrationIsCompletedLocator = By.xpath("//*[.= 'Регистрация завершена']");
    private static By logoutLocator = By.className("logout");
    private static By loginButtonLocator = By.className("woocommerce-form-login__submit");
    private static By helloUserLocator = By.xpath("//*[contains(@class, 'woocommerce-MyAccount-content')]/p[1]");
    private static By usernameLoginLocator = By.id("username");
    private static By passwordLoginLocator = By.id("password");
    private static By errorMessageLocator = By.cssSelector(".woocommerce-error li");
    private static By showPasswordButtonLocator = By.className("show-password-input");
    private static By displayPasswordLocator = By.className("display-password");
    private static By forgetPasswordLocator = By.cssSelector(".lost_password a");
    private static By titlePageH2Locator = By.cssSelector(".post-title");
    private static By randomEmailLocator = By.id("egen");
    private static By authorizationLinkLocator = By.className("showlogin");
    private static By openPageLocator = By.cssSelector("[aria-current = 'page']");

    private static By catalogHeaderMenuLocator = By.xpath("//*[contains(@class, 'store-menu')]//*[.='Каталог']");
    private static By addToCartButtonLocator = By.xpath("(//*[.= 'В корзину'])[1]"); // на странице каталога
    private static By moreButtonLocator = By.xpath("//*[.= 'Подробнее']");
    private static By checkoutButtonLocator = By.className("checkout-button");
    private static By billingFieldsLocator = By.className("woocommerce-billing-fields");

    // Регистрация пользователя со страницы регистрации
    @Test
    public void testRegisterPage_ClickRegisterButton_UserRegistrationIsTrue() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, driver.findElement(randomEmailLocator).getText().indexOf('@'));
        var email = driver.findElement(randomEmailLocator).getText().substring(0, 10);
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(usernameRegLocator).sendKeys(username);
        driver.findElement(emailRegLocator).sendKeys(email + "@t.ru");
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Надпись \"Регистрация завершена\" не отображается", driver.findElement(registrationIsCompletedLocator).isDisplayed());
        var expectedText = "Регистрация завершена";
        var actualText = driver.findElement(registrationIsCompletedLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualText, expectedText), actualText.contains(expectedText));
        var expectedLogoutText = "Выйти";
        var actualLogoutText = driver.findElement(logoutLocator).getText();
        Assert.assertTrue("Текст \"Выйти\" не отображается", driver.findElement(logoutLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualLogoutText, expectedLogoutText), actualLogoutText.contains(expectedLogoutText));
        driver.findElement(logoutLocator).click();
    }

    // Регистрация пользователя со страницы регистрации с незаполненным полем ввода "Адрес почты"
    @Test
    public void testRegisterPage_EmptyEmailTapRegisterButton_ErrorMessageIsDisplayed() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Пожалуйста, введите корректный email.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя со страницы регистрации с незаполненным полем ввода "Имя пользователя"
    @Test
    public void testRegisterPage_EmptyUsernameTapRegisterButton_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var email = driver.findElement(randomEmailLocator).getText();

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Пожалуйста введите корректное имя пользователя.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя со страницы регистрации с незаполненным полем ввода "Пароль"
    @Test
    public void testRegisterPage_EmptyPasswordTapRegisterButton_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, 10);
        var email = driver.findElement(randomEmailLocator).getText().substring(0, 10);

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email + "u" + "@t.ru");
        driver.findElement(usernameRegLocator).sendKeys(username + "u");
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Введите пароль для регистрации.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя со страницы регистрации - проверка граничного значения поля ввода "Адрес почты"
    @Test
    public void testRegisterPage_EnterUpperValueEmailField_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, driver.findElement(randomEmailLocator).getText().indexOf('@'));
        var email = driver.findElement(randomEmailLocator).getText().substring(0, 10);
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email + "@yopmail.ru");
        driver.findElement(usernameRegLocator).sendKeys(username + "u");
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Максимальное допустимое количество символов: 20";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя со страницы регистрации - невалидный email
    @Test
    public void testRegisterPage_EnterInvalidEmail_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, driver.findElement(randomEmailLocator).getText().indexOf('@'));
        var email = driver.findElement(randomEmailLocator).getText().substring(0, 10);
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email + "@tru");
        driver.findElement(usernameRegLocator).sendKeys(username);
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Пожалуйста, введите корректный email.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя с зарегистрированным (с существующим) email
    @Test
    public void testRegisterPage_EnterRegisteredEmail_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, driver.findElement(randomEmailLocator).getText().indexOf('@'));
        var email = "anna1@anna.test";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email);
        driver.findElement(usernameRegLocator).sendKeys(username);
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Учетная запись с такой почтой уже зарегистировавана. Пожалуйста авторизуйтесь.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // Регистрация пользователя с зарегистрированным (с существующим) именем пользователя
    @Test
    public void testRegisterPage_EnterRegisteredUsername_ErrorMessageIsDisplayed() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = "anna";
        var email = driver.findElement(randomEmailLocator).getText().substring(0, 10);
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email + "@t.ru");
        driver.findElement(usernameRegLocator).sendKeys(username);
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //assert
        Assert.assertTrue("Сообщение об ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        var expectedError = "Error: Учетная запись с такой почтой уже зарегистировавана. Пожалуйста авторизуйтесь.";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // При нажатии на ссылку авторизации со страницы регистрации открывается страница для входа
    @Test
    public void testRegisterPage_ClickAuthorizationLink_TitlePageTrue() {
        //arrange
        driver.get("https://yopmail.com/ru/email-generator");
        var username = driver.findElement(randomEmailLocator).getText().substring(0, driver.findElement(randomEmailLocator).getText().indexOf('@'));
        var email = "anna1@anna.test";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(accountLocator).click();
        driver.findElement(registerButtonLocator).click();
        driver.findElement(emailRegLocator).sendKeys(email);
        driver.findElement(usernameRegLocator).sendKeys(username);
        driver.findElement(passwordRegLocator).sendKeys(password);
        driver.findElement(registerButtonSubmitLocator).click();

        //act
        driver.findElement(authorizationLinkLocator).click();

        //assert
        Assert.assertTrue("Страница для входа не отображается", driver.findElement(titlePageH2Locator).isDisplayed());
        var expectedTitle = "Мой аккаунт";
        var actualTitle = driver.findElement(titlePageH2Locator).getText();
        Assert.assertTrue(String.format("Неправильный заголовок страницы. Сейчас: %s, Ожидали: %s", expectedTitle, actualTitle), expectedTitle.contains(actualTitle));
    }

    // Вход со страницы "Мой аккаунт" - с использованием имени пользователя
    @Test
    public void testLoginPage_ClickLoginButton_UserNameIsTrue() {
        //arrange
        var username = "anna";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(username);
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();

        //assert
        var expectedLogoutText = "Выйти";
        var actualLogoutText = driver.findElement(logoutLocator).getText();
        Assert.assertTrue("Текст \"Выйти\" не отображается", driver.findElement(logoutLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualLogoutText, expectedLogoutText), actualLogoutText.contains(expectedLogoutText));
        var expectedHelloUserText = "Привет " + username + " (Выйти)";
        var actualHelloUserText = driver.findElement(helloUserLocator).getText();
        Assert.assertTrue("Текст \"Привет\" не отображается", driver.findElement(helloUserLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualHelloUserText, expectedHelloUserText), actualHelloUserText.contains(expectedHelloUserText));
        driver.findElement(logoutLocator).click();
    }

    // Вход со страницы "Мой аккаунт" - с использованием почты
    @Test
    public void testLoginPage_ClickLoginButton_UserEmailIsTrue() {
        //arrange
        var username = "anna";
        var email = "anna@anna.test";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(email);
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();

        //assert
        var expectedLogoutText = "Выйти";
        var actualLogoutText = driver.findElement(logoutLocator).getText();
        Assert.assertTrue("Текст \"Выйти\" не отображается", driver.findElement(logoutLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualLogoutText, expectedLogoutText), actualLogoutText.contains(expectedLogoutText));
        var expectedHelloUserText = "Привет " + username + " (Выйти)";
        var actualHelloUserText = driver.findElement(helloUserLocator).getText();
        Assert.assertTrue("Текст \"Привет\" не отображается", driver.findElement(helloUserLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после регистрации. Сейчас: %s, Ожидали: %s", actualHelloUserText, expectedHelloUserText), actualHelloUserText.contains(expectedHelloUserText));
        driver.findElement(logoutLocator).click();
    }

    // Вход со страницы "Мой аккаунт" - При незаполненном поле ввода "Имя пользователя или почта" появляется сообщение об ошибке
    @Test
    public void testLoginPage_EmptyUsernameTapLoginButton_ErrorMessageIsDisplayed() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(loginButtonLocator).click();

        //assert
        var expectedErrorMessage = "Error: Имя пользователя обязательно.";
        var actualErrorMessage = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue("Текст ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст ошибки при незаполненном поле ввода имени пользователя. Сейчас: %s, Ожидали: %s", actualErrorMessage, expectedErrorMessage), actualErrorMessage.contains(expectedErrorMessage));
    }

    // Вход со страницы "Мой аккаунт" - При незаполненном поле ввода "Пароль" появляется сообщение об ошибке
    @Test
    public void testLoginPage_EmptyPasswordTapLoginButton_ErrorMessageIsDisplayed() {
        //arrange
        var username = "anna";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(username);
        driver.findElement(loginButtonLocator).click();

        //assert
        var expectedErrorMessage = "Пароль обязателен.";
        var actualErrorMessage = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue("Текст ошибки не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст ошибки при незаполненном поле ввода пароля. Сейчас: %s, Ожидали: %s", actualErrorMessage, expectedErrorMessage), actualErrorMessage.contains(expectedErrorMessage));
    }

    // Вход со страницы "Мой аккаунт" - При нажатии на кнопку Показать пароль пароль отображается. При повторном нажатии пароль отображается буллетами.
    @Test
    public void testLoginPage_ClickShowPasswordButton_PasswordIsDisplayed() {
        //arrange
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(showPasswordButtonLocator).click();

        //assert
        Assert.assertTrue("Пароль замаскирован буллетами", driver.findElement(displayPasswordLocator).isDisplayed());
        driver.findElement(showPasswordButtonLocator).click();
        Assert.assertFalse("Пароль замаскирован буллетами", driver.findElement(showPasswordButtonLocator).getAttribute("class").contains("display-password"));
    }

    // Вход со страницы "Мой аккаунт" - При нажатии на ссылку "Забыли пароль?" открывается страница восстановления пароля
    @Test
    public void testLoginPage_ClickForgetPasswordLink_TitlePageTrue() {
        //arrange

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(forgetPasswordLocator).click();

        //assert
        var expectedTitle = "Восстановление пароля";
        var actualTitle = driver.findElement(titlePageH2Locator).getText();
        Assert.assertTrue("Заголовок \"Восстановление пароля\" не отображается", driver.findElement(titlePageH2Locator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный заголовок страницы восстановления пароля. Сейчас: %s, Ожидали: %s", actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

    // Вход со страницы "Мой аккаунт" - с невалидным паролем
    @Test
    public void testLoginPage_EnterInvalidPassword_ErrorMessageIsDisplayed() {
        //arrange
        var username = "anna";
        var password = "anna888B";

        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        driver.findElement(accountLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(username);
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();

        //assert
        var expectedError = "Веденный пароль для пользователя anna неверный. Забыли пароль?";
        var actualError = driver.findElement(errorMessageLocator).getText();
        Assert.assertTrue("Текст сообщения об ошибке не отображается", driver.findElement(errorMessageLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст сообщения об ошибке. Сейчас: %s, Ожидали: %s", actualError, expectedError), actualError.contains(expectedError));
    }

    // При нажатии на "Выйти" пользователь выходит из аккаунта
    @Test
    public void testLoginPage_ClickLogoutLink_OpenHomepage() {
        //arrange
        var username = "anna";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(accountLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(username);
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();

        //act
        driver.findElement(logoutLocator).click();

        //assert
        var expectedLoginText = "Войти";
        var actualLoginText = driver.findElement(accountLocator).getText();
        Assert.assertTrue("Текст ссылки \"Войти\" не отображается", driver.findElement(accountLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст ссылки после выхода из аккаунта. Сейчас: %s, Ожидали: %s", actualLoginText, expectedLoginText), actualLoginText.contains(expectedLoginText));
        var expectedPage = "ГЛАВНАЯ";
        var actualPage = driver.findElement(openPageLocator).getText();
        Assert.assertTrue("Текст \"Привет\" не отображается", driver.findElement(openPageLocator).isDisplayed());
        Assert.assertTrue(String.format("Загрузилась не та страница. Сейчас: %s, Ожидали: %s", actualPage, expectedPage), actualPage.contains(expectedPage));
    }

    // Авторизация со страницы оформления заказа
    @Test
    public void testCheckoutPage_UserAuthorization_UserLoggedIn() {
        //arrange
        var username = "anna";
        var password = "anna8888";

        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.findElement(catalogHeaderMenuLocator).click();
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(moreButtonLocator).click();
        driver.findElement(checkoutButtonLocator).click();

        //act
        driver.findElement(authorizationLinkLocator).click();
        driver.findElement(usernameLoginLocator).sendKeys(username);
        driver.findElement(passwordLoginLocator).sendKeys(password);
        driver.findElement(loginButtonLocator).click();

        //assert
        Assert.assertTrue("Текст \"Выйти\" не отображается", driver.findElement(billingFieldsLocator).isDisplayed());
        var expectedLogoutText = "Выйти";
        var actualLogoutText = driver.findElement(logoutLocator).getText();
        Assert.assertTrue("Текст \"Выйти\" не отображается", driver.findElement(logoutLocator).isDisplayed());
        Assert.assertTrue(String.format("Неправильный текст после авторизации. Сейчас: %s, Ожидали: %s", actualLogoutText, expectedLogoutText), actualLogoutText.contains(expectedLogoutText));
        driver.findElement(logoutLocator).click();
    }
}
