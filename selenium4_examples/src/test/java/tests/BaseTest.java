package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogExporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.TopBar;
import utils.Constants;
import utils.WebDriverUtils;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver = null;
    protected TopBar topbarPage;

    @BeforeSuite
    public void setUpDriver(){
        WebDriverManager.chromedriver().setup();
        System.out.println("Downloading required driver...");
    }

    @BeforeMethod
    public void beforeTest() {
        System.out.println("Creating instance of WebDriver");
        driver = RemoteWebDriver.builder().oneOf(new ChromeOptions()).build();
        driver.manage().window().maximize();
        driver.get(getMainUrl());
        topbarPage = new TopBar(driver);
    }

    protected String getMainUrl() {
        return "http://opencart.abstracta.us";
    }

    @AfterMethod
    public void afterTest()  {
        this.driver.quit();
    }

}

