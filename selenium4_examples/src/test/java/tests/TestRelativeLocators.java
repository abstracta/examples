package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LaptopsPage;
import pages.TopBar;
import utils.WebDriverUtils;

import java.util.List;

public class TestRelativeLocators extends BaseTest{

    @DataProvider(name = "Products")
    public static Object[][] DP_Products() {
        return new Object[][] {{"MacBook"},{"MacBook Air"},{"Sony VAIO"}};
    }

    @Test(dataProvider = "Products")
    public void RelativeLocatorsFeature(String productName){
        TopBar top = new TopBar(driver);
        LaptopsPage laptops = top.goToShowAllLaptops();
        System.out.printf("====== Product: %s =======%n",productName);
        // Relative locator from Laptops image
        WebElement titleLaptops = driver.findElement(RelativeLocator.with(By.tagName("h2")).above(laptops.getPageLoadedLocator()));
        WebDriverUtils.highlight(titleLaptops, driver);
        // Check there are 5 products on the list
        Assert.assertEquals(laptops.getProducts().size(),5);
        // Find the right product
        WebElement desiredProduct = driver.findElement(By.linkText(productName));
        // Below
        List<WebElement> aux_list = driver.findElements(RelativeLocator.with(laptops.spanAddToCart).below(desiredProduct));
        WebDriverUtils.highlightAll(aux_list,driver);
        System.out.printf("Below the label of product %s there are %d shopping cart spans %n",productName,aux_list.size());
        // Above
        aux_list = driver.findElements(RelativeLocator.with(laptops.spanAddToCart).above(desiredProduct));
        WebDriverUtils.highlightAll(aux_list,driver);
        System.out.printf("Above the label of product %s there are %d shopping cart spans %n",productName,aux_list.size());
        // Near
        aux_list = driver.findElements(RelativeLocator.with(laptops.spanAddToCart).near(desiredProduct,200));
        WebDriverUtils.highlightAll(aux_list,driver);
        System.out.printf("Near the label of product %s there are %d shopping cart spans %n",productName,aux_list.size());
        // Left
        aux_list = driver.findElements(RelativeLocator.with(laptops.spanAddToCart).toLeftOf(desiredProduct));
        WebDriverUtils.highlightAll(aux_list,driver);
        System.out.printf("Left of the label of product %s there are %d shopping cart spans %n",productName,aux_list.size());
        // Right
        aux_list = driver.findElements(RelativeLocator.with(laptops.spanAddToCart).toRightOf(desiredProduct));
        WebDriverUtils.highlightAll(aux_list,driver);
        System.out.printf("Right of the label of product %s there are %d shopping cart spans %n",productName,aux_list.size());
        // Definitive selector using XPath to find the element by its name and then navigating to the related 'Add to cart' span
        By addToCartByName = By.xpath(
                String.format(
                        "//a[normalize-space()='%s']//ancestor::div[@class='product-thumb']//button[contains(@onclick,'cart')]/span",
                        productName
                ));
        WebDriverUtils.highlight(driver.findElement(addToCartByName),driver);
    }
}
