package com.mytest.pages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.mytest.constants.Constants;
import com.mytest.services.WebLibrary;

public class CricbuzzHomePage extends WebLibrary
{
	public CricbuzzHomePage(WebDriver driver)
	{
		setDriver(driver);
		PageFactory.initElements(driver, this);
	}

	By firstOngoingMatch = By.xpath("(//div[@id='match_menu_container']//a[@target='_self'])[1]");
	By scorecard = By.xpath("//a[text()='Scorecard']");
	By scorecardStats = By.cssSelector("div[class*='scrcrd-status']");
	By scorecardList = By.xpath("//div[contains(@id,'innings')]//div[contains(@class,'scrd')]");

	public void goToCricbuzzHome()
	{
		navigateToUrl("https://www.cricbuzz.com/");
		verifyPageTitle("Cricket Score, Schedule, Latest News, Stats & Videos | Cricbuzz.com");
	}

	public void openFirstOngoingMatch()
	{
		clickElement(firstOngoingMatch, Constants.M_DELAY_S, "First on going match");
	}

	public void clickOnScorecard()
	{
		clickElement(scorecard, Constants.M_DELAY_S, "Scorecard");
	}

	public void printCompleteScorecard()
	{
		waitForElement(scorecardStats, Constants.M_DELAY_S);
		if (getDriver().findElement(scorecardStats).isDisplayed())
		{
			System.out.println(
					"\n==============================================================================================================\n");
			System.out.println(getDriver().findElement(scorecardStats).getText());
		}
		List<WebElement> headers = getDriver().findElements(scorecardList);
		Iterator<WebElement> var4 = headers.iterator();
		while (var4.hasNext())
		{
			WebElement header = (WebElement) var4.next();
			if (header.getAttribute("class").contains("scrd-hdr"))
			{
				System.out.println(
						"\n==============================================================================================================\n");
			}
			List<WebElement> indElements = header.findElements(By.xpath(".//*[not(descendant::*)]"));
			Iterator<WebElement> var7 = indElements.iterator();

			while (var7.hasNext())
			{
				WebElement indElement = (WebElement) var7.next();
				if (!indElement.getAttribute("class").contains("text-right"))
				{
					System.out.printf("%-30s", indElement.getText());
				}
				else
				{
					System.out.printf("%10s", indElement.getText());
				}
			}
			System.out.println();
		}
		System.out.print(
				"\n==============================================================================================================\n");
	}
}
