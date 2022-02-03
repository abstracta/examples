package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import tests.BaseTest;
import utils.WebDriverUtils;

public class ProductPage extends BasePage {

    private final By title1Product = RelativeLocator.with(By.tagName("h1")).above(getPageLoadedLocator());

    /**
     * The basic Page Object constructor
     * It waits until the element finder in the method is found or the timer has finished.
     * On the second case a Timeout Exception is thrown
     *
     * @param driver the driver
     */
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName(){
        WebElement name = driver.findElement(title1Product);
        WebDriverUtils.highlight(name,driver);
        return name.getText();
    }

    @Override
    public By getPageLoadedLocator() {
        return By.id("product");
    }
}
