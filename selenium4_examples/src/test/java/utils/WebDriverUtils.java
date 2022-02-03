package utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;


public class WebDriverUtils {

    public static WebElement ExplicitWaitElement(ExpectedCondition<WebElement> expectedCondition, int seconds, WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(expectedCondition);
    }

    public static void ExplicitWaitFrame(ExpectedCondition<WebDriver> expectedCondition, int seconds,WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(expectedCondition);
    }

    public static void ExplicitWaitAlert(ExpectedCondition<Alert> expectedCondition, int seconds, WebDriver driver) {
        new WebDriverWait(driver,Duration.ofSeconds(seconds)).until(expectedCondition) ;
    }

    public static void ExplicitWaitBoolean(ExpectedCondition<Boolean> expectedCondition, int seconds,WebDriver driver) {
        new WebDriverWait(driver,Duration.ofSeconds(seconds)).until(expectedCondition) ;
    }

    public static List<WebElement> ExplicitWaitListElement(ExpectedCondition<List<WebElement>> expectedCondition, int seconds, WebDriver driver) {
        return new WebDriverWait(driver,Duration.ofSeconds(seconds)).until(expectedCondition);
    }

    public static void ImplicitWait(int seconds,WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    public static void MoveToElement(By selector, WebDriver driver) {
        new Actions(driver).moveToElement(driver.findElement(selector)).build().perform();
    }

    public static void MoveToElement(WebElement element, WebDriver driver) {
        new Actions(driver).moveToElement(element).build().perform();
    }

    /**
     * Input data.
     * auxiliary method to erase, send data and set the focus away
     * @param e the input
     * @param data the data to send
     */
    public void InputData(WebElement e, String data, boolean tab) {
        e.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        e.sendKeys(data);
        if (tab)
            e.sendKeys(Keys.TAB);
    }

    public static boolean isElementPresent(WebDriver driver, final By locator) {
        try {
            ExplicitWaitElement(
                    ExpectedConditions.visibilityOfElementLocated(locator),
                    Constants.MEDIUM_TIMEOUT,
                    driver
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void highlightAll(List<WebElement> aux_list, WebDriver driver){
        for (WebElement e: aux_list)
            highlight(e,driver);
    }

    public static void highlight(WebElement element, WebDriver driver) {
        MoveToElement(element, driver);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object originalBoxShadows = executor.executeScript("return arguments[0].style.boxShadow", element);

        List<Color> colors = List.of(
                new Color(255,0,0,1),
                new Color(128,0,128,1),
                new Color(0,0,255,1),
                new Color(0,128,128,1),
                new Color(0,255,0,1),
                new Color(128,128,0,1));
        for (int i = 0; i < 4; i++) {
            for (Color color : colors){
                executor.executeScript(
                        "arguments[0].style.boxShadow = '0px 0px 5px 5px ' + arguments[1]",
                        element,
                        color.asRgba()
                );
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e){
                    throw new WebDriverException("Element highlighting failed!");
                }
            }
        }
        executor.executeScript("arguments[0].style.boxShadow = arguments[1]", element, originalBoxShadows);
    }

    public static Path getResourcePath(String resource){
        return Paths.get(System.getProperty("user.dir"),"src","test","java","resources",resource);
    }
}
