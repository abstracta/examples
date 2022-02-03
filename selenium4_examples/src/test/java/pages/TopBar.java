package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Constants;
import utils.WebDriverUtils;

import java.time.Duration;

public class TopBar extends BasePage{

    private final By dropLaptops = By.cssSelector(".dropdown > a[href$='18']");
    private final By btnShowAllLaptops = By.linkText("Show All Laptops & Notebooks");
    public final By itemCategories = By.cssSelector("#menu .nav > li");
    private final By inputSearch = By.name("search");
    private final By btnSearch = By.cssSelector("#search button");


    /**
     * The basic Page Object constructor
     * It waits until the element finder in the method is found or the timer has finished.
     * On the second case a Timeout Exception is thrown
     *
     * @param driver the driver
     */
    public TopBar(WebDriver driver) {
        super(driver);
    }

    @Override
    public By getPageLoadedLocator() {
        return By.id("menu");
    }

    public LaptopsPage goToShowAllLaptops(){
        WebDriverUtils.MoveToElement(dropLaptops,driver);
        WebDriverUtils.ExplicitWaitElement(
                ExpectedConditions.visibilityOfElementLocated(btnShowAllLaptops),
                Constants.TINY_TIMEOUT,
                driver
        ).click();
        return new LaptopsPage(driver);
    }

    public SearchResultsPage SearchProduct(String product){
        driver.findElement(inputSearch).sendKeys(product);
        driver.findElement(btnSearch).click();
        return new SearchResultsPage(driver);
    }


}
