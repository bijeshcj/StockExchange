package com.bijesh.stockexchange.stockexchange;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

		for (int i=0; i<elements.size();i++){
			System.out.println("Radio button text:" + elements.get(i).getAttribute("value"));
		}
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

