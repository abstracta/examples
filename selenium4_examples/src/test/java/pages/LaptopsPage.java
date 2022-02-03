package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class LaptopsPage extends BasePage{

    public By products = By.className("product-thumb");
    public By TTL4Product = By.cssSelector(".product-thumb h4");

    public By spanAddToCart = By.cssSelector("#content .fa-shopping-cart + span");
    // Alternative locator
    public By btnAddToCart = By.cssSelector("#content button[onclick^=cart]");

    /**
     * The basic Page Object constructor
     * It waits until the element finder in the method is found or the timer has finished.
     * On the second case a Timeout Exception is thrown
     *
     * @param driver the driver
     */
    public LaptopsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public By getPageLoadedLocator() {
        return By.cssSelector("img[title^=Laptops]");
    }

    public List<WebElement> getProducts(){
        return driver.findElements(products);
    }
}
