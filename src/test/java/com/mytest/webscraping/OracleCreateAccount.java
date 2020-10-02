package com.mytest.webscraping;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mytest.constants.Constants;
import com.mytest.pages.OracleCreateAccountPage;

@Listeners(com.mytest.utils.TestListener.class)
public class OracleCreateAccount
{
	WebDriver driver;

	@Test
	public void testMethod()
	{
		OracleCreateAccountPage oracleCreateAccount = new OracleCreateAccountPage(driver);
		oracleCreateAccount.invokeBrowser(Constants.CHROME_BROWSER);
		oracleCreateAccount.navigateToAccountCreationPage();
		oracleCreateAccount.enterEmail("test@gmail.com");
		oracleCreateAccount.enterPassword("Hyderabad786");
		oracleCreateAccount.reEnterPassword("Hyderabad786");
		oracleCreateAccount.selectCountry("USA");
		oracleCreateAccount.enterFirstName("User");
		oracleCreateAccount.enterLastName("User");
		oracleCreateAccount.enterWorkPhone("9999999999");
		oracleCreateAccount.enterCompanyName("Company");
		oracleCreateAccount.enterAddress("USA");
		oracleCreateAccount.enterCity("New Jersey");
		oracleCreateAccount.enterJobTitle("Software Engineer");
		oracleCreateAccount.selectState("Alaska");
		oracleCreateAccount.enterZip("11111");
		oracleCreateAccount.clickCreateBtn();
		oracleCreateAccount.verifyConfirmationMessage();

		oracleCreateAccount.closeBrowser();
	}
}
