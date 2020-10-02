package com.mytest.webscraping;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mytest.constants.Constants;
import com.mytest.pages.CricbuzzHomePage;
import com.mytest.services.WebApplicationLibrary;

@Listeners(com.mytest.utils.TestListener.class)
public class CricbuzzWebScraping_EngVsAus
{
	WebApplicationLibrary appLib;
	WebDriver driver;

	@Test
	public void testMethod()
	{
		appLib = new WebApplicationLibrary();
		driver = appLib.invokeBrowser("chrome");
		CricbuzzHomePage cbHome = new CricbuzzHomePage(driver);

		driver.get("https://www.cricbuzz.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return window.stop", new Object[0]);
		appLib.clickElement(By.xpath(
				"(//div[@id='match_menu_container']//a[contains(@title,'England')][contains(@href,'-england-')])[1]"),
				Constants.M_DELAY_S, "Menu");
		appLib.clickElement(By.xpath("//a[text()='Scorecard']"), 20, "Scorecard");

		cbHome.printCompleteScorecard();
		appLib.closeBrowser();
	}
}
