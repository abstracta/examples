package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TheInternetPage extends BasePage{

    private final By linkBasicAuth = By.linkText("Basic Auth");
    private final By linkDigestAuth = By.linkText("Digest Authentication");
    public By content = By.id("content");

    /**
     * The basic Page Object constructor
     * It waits until the element finder in the method is found or the timer has finished.
     * On the second case a Timeout Exception is thrown
     *
     * @param driver the driver
     */
    public TheInternetPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle(){
        return driver.findElement(By.tagName("h3")).getText();
    }

    public void clickBasicAuth(){
        driver.findElement(linkBasicAuth).click();
    }

    public void clickDigestAuth(){
        driver.findElement(linkDigestAuth).click();
    }

    @Override
    public By getPageLoadedLocator() {
        return By.className("heading");
    }
}
