package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.WebDriverUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TestNetworkInterceptor extends BaseTest{

    @Test
    public void ShowStatusCodes(){
        // In this test we simply see every single request being made explicitly shown in the console with its response status code
        Filter reportStatus = new Filter() {
            @Override
            public HttpHandler apply(HttpHandler httpHandler) {
                return req -> {
                    HttpResponse res = httpHandler.execute(req);
                    System.out.printf("Request: %s => Response: %s%n",req,res.getStatus());
                    return res;
                };
            }
        };
        try (NetworkInterceptor interceptor = new NetworkInterceptor(driver,reportStatus)){
            driver.navigate().refresh();
            System.out.println(driver.findElements(By.xpath("//*")).size());
        }
    }

    @Test
    public void SwitchSliderImages(){
        // This test changes the main images in the opencart slider by a wallpaper
        byte[] fileBytes = new byte[0];
        try{
            fileBytes = Files.readAllBytes(WebDriverUtils.getResourcePath("wallpaper.jpg"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        byte[] finalFileBytes = fileBytes;
        // When the resource requested matches the ones we want to replace, the response we send back has the bytes content from the wallpaper
        Routable route = Route.matching(req -> (req.getUri().contains("iPhone6-") || req.getUri().contains("MacBookAir"))  && req.getUri().endsWith(".jpg")).to(
                ()-> req -> new HttpResponse()
                        .addHeader("Content-Type","image/jpg")
                        .setContent(Contents.bytes(finalFileBytes))
        );

        try (NetworkInterceptor interceptor = new NetworkInterceptor(driver,route)){
            driver.navigate().refresh();
            WebDriverUtils.MoveToElement(By.className("swiper-pagination"),driver);
            System.out.println(driver.findElements(By.xpath("//*")).size());
        }
    }
}

