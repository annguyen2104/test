package com.playphone.devportal.project;

import org.junit.Before;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



public abstract class Base {
	
	protected WebDriver driver;
	protected  String url;
	
	@Before
	public void setUp() {
		// use an existing FirefoxProfile
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("default");
		driver = new FirefoxDriver(ffprofile);
		
		// open browser
		url = "https://developer.playphone.com";
		driver.get(url + "/");
		
		// implicit wait for 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// maximize browser size
		driver.manage().window().maximize();
	}
	
	// wait for visibility of an element
	public void wait(String xpathExpression) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
	}
	
	// click on element
	public void click(String xpathExpression) {
		WebElement element = driver.findElement(By.xpath(xpathExpression));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("argument[0].click;", element);
	}
	
	// enter text or upload file
	public void enterText(String xpathExpression, String text) {
		WebElement element = driver.findElement(By.xpath(xpathExpression));
		element.clear();
		element.sendKeys(text);
	}
	
	// check two strings if they are equal 
	public void assertStringEquals(String xpathExpression, String expected) {
		String text = driver.findElement(By.xpath(xpathExpression)).getText();
		assertEquals(expected, text);
	}
	
	// take screenshot
	public void takeScreenshot(String name) throws IOException {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("/Users/an/Desktop/Playphone/project/images/" + name + ".jpg"));
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
}

