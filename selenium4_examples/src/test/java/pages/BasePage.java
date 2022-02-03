package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.*;

// TODO: Auto-generated Javadoc
/**
 * The Class BasePage.
 * Parent class of all Page Objects, has the common elements that appear on screen as well as methods that the derived class need to use 
 */
public abstract class BasePage {
	
	/** The driver is used mainly to find elements on the present Page Object, but it also can be used to implement waits or Javascript executions. */
	public static WebDriver driver;
	
	/**
	 * The basic Page Object constructor 
	 * It waits until the element finder in the method is found or the timer has finished.
	 * On the second case a Timeout Exception is thrown
	 * @param driver the driver
	 */
	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
		this.isLoaded();
	}
	
	/**
	 * Checks if the page object is loaded with the locator provided by the getPageLoadedLocator method.
	 * @throws WebDriverException the illegal access exception
	 */
	public final void isLoaded() throws WebDriverException {
		if(!WebDriverUtils.isElementPresent(driver, this.getPageLoadedLocator())) {
			throw new WebDriverException("This is not " + this.getClass().getName() + " Page Object");
		}	
	}

	public abstract By getPageLoadedLocator();

}
