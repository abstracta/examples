import org.eclipse.jetty.http.MimeTypes.Type;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class Checkout {

    @Test
    public void performanceTest() throws IOException {
        String baseUrl="http://opencart.abstracta.us";
        String product= "iPhone";

        TestPlanStats stats = testPlan(
                threadGroup(1, 1,
                        httpSampler("1. Go to Opencart",baseUrl+"/")
                                .header("Host","opencart.abstracta.us")
                                .header("Connection","keep-alive")
                                .header("Upgrade-Insecure-Requests","1")
                                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                .header("Accept-Encoding","gzip, deflate")
                                .header("Accept-Language","en-US,en;q=0.9")
                                .children(
                                        responseAssertion().containsSubstrings("<title>Your Store</title>"),
                                        regexExtractor("productId","product_id=(\\d*)\">"+product),
                                        uniformRandomTimer(1000,2000)
                                ),

                        transaction("2. Click product",
                                httpSampler("Click product - product",baseUrl+"/index.php?route=product/product&product_id=${productId}")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Upgrade-Insecure-Requests","1")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                        .header("Referer","http://opencart.abstracta.us/")
                                        .header("Accept-Encoding","gzip, deflate")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings(product),
                                                uniformRandomTimer(2000,2000)
                                        ),
                                httpSampler("Click product - review", baseUrl+"/index.php?route=product/product/review&product_id=${productId}")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("Referer","http://opencart.abstracta.us/index.php?route=product/product&product_id=40")
                                        .header("Accept-Encoding","gzip, deflate")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings("review")
                                        )
                        ),

                        transaction("3. Add to cart",
                                httpSampler("Add to cart - add",baseUrl+"/index.php?route=checkout/cart/add")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Content-Length","24")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                                        .header("Origin","http://opencart.abstracta.us")
                                        .header("Referer","http://opencart.abstracta.us/index.php?route=product/product&product_id=40")
                                        .header("Accept-Encoding","gzip, deflate")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .post("quantity=1&product_id=${productId}", Type.FORM_ENCODED)
                                        .children(
                                                responseAssertion().containsSubstrings("You have added",product),
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Add to cart - info",baseUrl+"/index.php?route=common/cart/info%20")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("Referer","http://opencart.abstracta.us/index.php?route=product/product&product_id=40")
                                        .header("Accept-Encoding","gzip, deflate")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings(product)
                                        )
                        ),

                        httpSampler("4. View cart", baseUrl+"/index.php?route=checkout/cart")
                                .header("Host","opencart.abstracta.us")
                                .header("Connection","keep-alive")
                                .header("Upgrade-Insecure-Requests","1")
                                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                .header("Referer","http://opencart.abstracta.us/index.php?route=product/product&product_id=40")
                                .header("Accept-Encoding","gzip, deflate")
                                .header("Accept-Language","en-US,en;q=0.9")
                                .children(
                                        responseAssertion().containsSubstrings(product),
                                        uniformRandomTimer(1000,2000)
                                ),

                        transaction("5. Checkout",
                                httpSampler("Checkout 1",baseUrl+"/index.php?route=checkout/checkout")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Cache-Control","max-age=0")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Upgrade-Insecure-Requests","1")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                        .header("Sec-Fetch-Site","cross-site")
                                        .header("Sec-Fetch-Mode","navigate")
                                        .header("Sec-Fetch-User","?1")
                                        .header("Sec-Fetch-Dest","document")
                                        .header("Referer","http://opencart.abstracta.us/")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings(product),
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Checkout - login", baseUrl+"/index.php?route=checkout/login")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings("Guest Checkout")
                                        )
                        ),

                        transaction("6. Select guest checkout",
                                httpSampler("Select guest checkout 1",baseUrl+"/index.php?route=checkout/guest")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings("guest"),
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Select guest checkout - customer group",baseUrl+"/index.php?route=checkout/checkout/customfield&customer_group_id=1")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9"),
                                httpSampler("Select guest checkout - get countries",baseUrl+"/index.php?route=checkout/checkout/country&country_id=222")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings("country_id")
                                        )
                        ),

                        httpSampler("7. Select country", baseUrl+"/index.php?route=checkout/checkout/country&country_id=225")
                                .header("Host","opencart.abstracta.us")
                                .header("Connection","keep-alive")
                                .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                .header("Accept","application/json, text/javascript, */*; q=0.01")
                                .header("X-Requested-With","XMLHttpRequest")
                                .header("sec-ch-ua-mobile","?0")
                                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                .header("sec-ch-ua-platform","\"Windows\"")
                                .header("Sec-Fetch-Site","same-origin")
                                .header("Sec-Fetch-Mode","cors")
                                .header("Sec-Fetch-Dest","empty")
                                .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                .header("Accept-Encoding","gzip, deflate, br")
                                .header("Accept-Language","en-US,en;q=0.9")
                                .children(
                                        responseAssertion().containsSubstrings("country_id"),
                                        uniformRandomTimer(3000,4000)
                                ),

                        transaction("8. Send billing details",
                                httpSampler("Send billing details 1", baseUrl+"/index.php?route=checkout/guest/save")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Content-Length","201")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Origin","https://opencart.abstracta.us")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .post("customer_group_id=1&firstname=Nombre&lastname=Apellido&email=Correo%40gmail.com&telephone=123123123&company=&address_1=Direccion1&address_2=&city=Montevideo&postcode=1234&country_id=225&zone_id=3704&shipping_address=1",Type.FORM_ENCODED)
                                        .children(
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Send billing details - get shipping method",baseUrl+"/index.php?route=checkout/shipping_method")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                responseAssertion().containsSubstrings("Please select the preferred shipping method")
                                        ),
                                httpSampler("Send billing details - get guest shipping",baseUrl+"/index.php?route=checkout/guest_shipping")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9"),
                                httpSampler("Send billing details - get country data",baseUrl+"/index.php?route=checkout/checkout/country&country_id=225")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                        ),

                        transaction("9. Send shipping method",
                                httpSampler("Send shipping method 1",baseUrl+"/index.php?route=checkout/shipping_method/save")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Content-Length","62")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Origin","https://opencart.abstracta.us")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .post("shipping_method=flat.flat&comment=comentario+asdsad",Type.TEXT_PLAIN)
                                        .children(
                                                uniformRandomTimer(2000,3000)
                                        ),
                                httpSampler("Send shipping method - get payment method",baseUrl+"/index.php?route=checkout/payment_method")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                        ),

                        transaction("10. Send payment method",
                                httpSampler("Send payment method 1",baseUrl+"/index.php?route=checkout/payment_method/save")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Content-Length","63")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Origin","https://opencart.abstracta.us")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .post("payment_method=cod&comment=comentario+asdsad&agree=1",Type.TEXT_PLAIN)
                                        .children(
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Send payment method - confirm",baseUrl+"/index.php?route=checkout/confirm")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","text/html, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                        ),

                        transaction("11. Confirm order",
                                httpSampler("Confirm order 1",baseUrl+"/index.php?route=extension/payment/cod/confirm")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("sec-ch-ua","\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"")
                                        .header("Accept","application/json, text/javascript, */*; q=0.01")
                                        .header("X-Requested-With","XMLHttpRequest")
                                        .header("sec-ch-ua-mobile","?0")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("sec-ch-ua-platform","\"Windows\"")
                                        .header("Sec-Fetch-Site","same-origin")
                                        .header("Sec-Fetch-Mode","cors")
                                        .header("Sec-Fetch-Dest","empty")
                                        .header("Referer","https://opencart.abstracta.us/index.php?route=checkout/checkout")
                                        .header("Accept-Encoding","gzip, deflate, br")
                                        .header("Accept-Language","en-US,en;q=0.9")
                                        .children(
                                                uniformRandomTimer(1000,2000)
                                        ),
                                httpSampler("Confirm order - success",baseUrl+"/index.php?route=checkout/success")
                                        .header("Host","opencart.abstracta.us")
                                        .header("Connection","keep-alive")
                                        .header("Upgrade-Insecure-Requests","1")
                                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
                                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                        .header("Accept-Encoding","gzip, deflate")
                                        .header("Accept-Language","en-US,en;q=0.9")
                        )

                ),
                resultsTreeVisualizer()
        ).run();
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(2));
    }

}