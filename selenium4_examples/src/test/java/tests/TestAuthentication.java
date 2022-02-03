package tests;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TheInternetPage;
import utils.Constants;

import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestAuthentication extends BaseTest{

    private TheInternetPage internet;

    @Override
    protected String getMainUrl() {
        return "https://the-internet.herokuapp.com/";
    }

    @BeforeMethod
    public void registerCredentialsBefore(){
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains("the-internet.herokuapp.com");
        ((HasAuthentication) driver).register(uriPredicate,() -> new UsernameAndPassword("admin","admin"));
        internet = new TheInternetPage(driver);
    }

    @Test
    public void DigestAuthCredentials() throws InterruptedException {
        // Select Digest Auth
        internet.clickDigestAuth();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Constants.SHORT_TIMEOUT));
        // Wait for text to contain right content
        wait.until(driver ->
                driver.findElement(internet.content).getText().contains("Congratulations")
        );
        Assert.assertEquals(internet.getTitle(),"Digest Auth");
        Thread.sleep(2000);
    }

    @Test
    public void BasicAuthCredentials() throws InterruptedException {
        // Select Basic Auth
        internet.clickBasicAuth();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Constants.SHORT_TIMEOUT));
        // Wait for text to contain right content
        wait.until(driver ->
                driver.findElement(internet.content).getText().contains("Congratulations")
        );
        Assert.assertEquals(internet.getTitle(),"Basic Auth");
        Thread.sleep(2000);
    }
}
