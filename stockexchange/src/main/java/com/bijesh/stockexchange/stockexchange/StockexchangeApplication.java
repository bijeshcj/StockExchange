package com.bijesh.stockexchange.stockexchange;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class StockexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockexchangeApplication.class, args);
        initJSoup();
        initSelenium();
	}

	private static void initSelenium(){
		System.setProperty("webdriver.gecko.driver","C:\\SeleniumGecko\\geckodriver.exe");
		WebDriver driver=new FirefoxDriver();
		driver.get("https://www.nseindia.com/");
		findElement(driver);
//		WebElement element=driver.findElement(By.xpath("//input[@name='emailid']"));
//		element.sendKeys("abc@gmail.com");
//
//		WebElement button=driver.findElement(By.xpath("//input[@name='btnLogin']"));
//		button.click();
	}

	private static void findElement(WebDriver webDriver){
		List<WebElement> elements = webDriver.findElements(By.name("companyED"));
		System.out.println("Number of elements:" +elements.size());
        WebElement element = null;
		for (int i=0; i<elements.size();i++){
			element = elements.get(i);
//			System.out.println("Radio button text:" + elements.get(i).getAttribute("value"));
			WebDriverWait wait = new WebDriverWait(webDriver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(element));


			element.sendKeys("DHFL");
			try{
				Thread.sleep(3000);
			}catch (Exception e){
				e.printStackTrace();
			}
			element.sendKeys(Keys.RETURN);
			System.out.println("$$$ Executed...");
			webDriver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
//			String kk = webDriver.findElement(By.xpath("//*[@id='show_hide_content']/.//span[contains(@class,'show_hide')]")).getAttribute("innerHTML");
//            System.out.println("$$$ kk "+kk);

			WebElement elementPer = webDriver.findElement(By.id("deliveryToTradedQuantity"));
			System.out.println("$$$ "+elementPer.getText());
			break;
		}

//		WebDriverWait wait = new WebDriverWait(webDriver, 5);
//		wait.until(ExpectedConditions.(element,"DHFL"));

	}


	private static void initJSoup(){
		try {
			Document doc = Jsoup.connect("https://www.nseindia.com/").get();
			System.out.println(doc.title());
			Elements newsHeadlines = doc.select("#mp-itn b a");
			for (Element headline : newsHeadlines) {
				System.out.println("$$$ "+headline.attr("title") + headline.absUrl("href"));
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/")
	public String hello(){
		return "Hello World";
	}


}

