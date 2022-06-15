package com.example;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author steve
 *
 * to use, add the following to your JUnit4 tests:
 *
 * @Rule
 * public ScreenshotRule4 rule = new ScreenshotRule4(driver, "target/surefire-reports");
 *
 */
public class Screenshot implements MethodRule {

    WebDriver driver;
    String path;

    public Screenshot(WebDriver driver, String path) {
        this.driver = driver;
        this.path = path;
    }

    @Override
    public Statement apply(final Statement statement, final FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable throwable) {
                    captureScreenshot(driver, method.getName());
                    throw throwable;
                }
            }

            public void captureScreenshot(WebDriver driver, String fileName) {
                try {
                    new File(path).mkdirs();
                    try ( FileOutputStream out = new FileOutputStream(path + File.separator + "screenshot-" + fileName + ".png")) {
                        out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                    }
                } catch (IOException | WebDriverException e) {
                    System.out.println("screenshot failed:" + e.getMessage());
                }
            }

        };
    }

}