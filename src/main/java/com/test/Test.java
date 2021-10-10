package com.test;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.neonomics.uicontrol.WebDriverManager;

public class Test {

	public static boolean isPresentAndDisplayed(final WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@org.testng.annotations.Test
	public void testSelenium() {

		WebDriverManager drv = new WebDriverManager();
		WebDriver driver = drv.createDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("https://www.autohero.com/de/search/?brand=VOLKSWAGEN&model=VOLKSWAGEN.Golf&mileageMax=25000");
		driver.findElement(By.xpath("/html/body/div[3]/div/form/div[2]/button[2]")).click();

		HashSet<String> h = new HashSet<String>();

		int initialLocation = 0;
		int latestLocation = 0;

		List<WebElement> abc = driver.findElements(By.xpath("//li[@class='specItem___2okEu' and @data-qa-selector='spec'][3]"));
		// System.out.println(abc.getText().replaceAll("•", "").trim());

//		for(WebElement g : abc) {
//			System.out.println(g.getText().replaceAll("•", "").trim());
//		}

//		System.out.println(driver.findElement(By.xpath("//button[@type='button' and @id='basicFilter']")).getSize());

//		do {
//			initialLocation = Integer.parseInt(js.executeScript("return window.scrollY").toString());
//			List<WebElement> allCars = driver.findElements(By.xpath("//a[contains(@href,'/de/volkswagen-golf')]"));
//			
//			for (WebElement eachCar : allCars) {
//				if (!h.contains(eachCar.getAttribute("href"))) {
//					h.add(eachCar.getAttribute("href"));
//				}
//			}
//			
//			js.executeScript("window.scrollBy(0,500)");
//			
//			latestLocation = Integer.parseInt(js.executeScript("return window.scrollY").toString());
//			
//		} while (initialLocation != latestLocation);

		List<WebElement> buttonList = driver.findElements(By.xpath("//button[@type='button' and @class='select___2BOta']"));

		for (WebElement eachBtn : buttonList) {

			System.out.println(eachBtn.getAttribute("id"));

			if (eachBtn.getAttribute("id").contains("basicFilter")) {
				System.out.println(buttonList.size());
				driver.findElement(By.xpath("//button[@type='button' and @id='basicFilter']")).click();
			}

			if (eachBtn.getAttribute("id").contains("mileageFilter")) {
				System.out.println(buttonList.size());
				driver.findElement(By.xpath("//button[@type='button' and @id='mileageFilter']")).click();
			}
		}

		for (WebElement eachBtn : buttonList) {

			System.out.println(eachBtn.getAttribute("id"));

			if (eachBtn.getAttribute("id").contains("basicFilter")) {
				System.out.println(buttonList.size());
				driver.findElement(By.xpath("//button[@type='button' and @id='basicFilter']")).click();
			}

			if (eachBtn.getAttribute("id").contains("mileageFilter")) {
				System.out.println(buttonList.size());
				driver.findElement(By.xpath("//button[@type='button' and @id='mileageFilter']")).click();
			}
		}

//		System.out.println(h.size());
	}
}
