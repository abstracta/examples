package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Constants;
import utils.WebDriverUtils;

public class SearchResultsPage extends BasePage{

    private final By title = By.cssSelector("#product-search h1");

    /**
     * The basic Page Object constructor
     * It waits until the element finder in the method is found or the timer has finished.
     * On the second case a Timeout Exception is thrown
     *
     * @param driver the driver
     */
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage selectProduct(String name){
        WebElement product = null;
        long startTime = System.currentTimeMillis();
        try {
//            product = WebDriverUtils.ExplicitWaitElement(
//                    ExpectedConditions.visibilityOfElementLocated(By.linkText(name)),
//                    Constants.TINY_TIMEOUT,
//                    driver);
            product = driver.findElement(By.linkText(name));
        } catch (WebDriverException e){
            long endTime = System.currentTimeMillis();
            System.out.println("Find element failed under " + (endTime - startTime) + " milliseconds");
            startTime = System.currentTimeMillis();
            product = driver.findElement(By.partialLinkText(name));
//            product = WebDriverUtils.ExplicitWaitElement(
//                    ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(name)),
//                    Constants.TINY_TIMEOUT,
//                    driver);
            endTime = System.currentTimeMillis();
            System.out.println("Find element success under " + (endTime - startTime) + " milliseconds");
        }
        WebDriverUtils.highlight(product,driver);
        product.click();
        return new ProductPage(driver);
    }

    public String getTitle(){
        WebElement title = driver.findElement(this.title);
        WebDriverUtils.highlight(title,driver);
        return title.getText();
    }

    @Override
    public By getPageLoadedLocator() {
        return By.id("product-search");
    }
}
