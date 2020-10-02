package com.mytest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.mytest.constants.Constants;
import com.mytest.services.WebLibrary;

public class OracleCreateAccountPage extends WebLibrary
{
	public OracleCreateAccountPage(WebDriver driver)
	{
		setDriver(driver);
		PageFactory.initElements(driver, this);
	}

	// Constants
	private String createAccountPageTitle = "Oracle | Create Account";

	// Locators
	By emailAddressField = By.xpath("//input[@name='email']");
	By passwordField = By.id("password::content");
	By retypePasswordField = By.id("retypePassword::content");
	By country = By.id("country::content");
	By firstName = By.name("firstName");
	By lastName = By.name("lastName");
	By jobTitle = By.name("jobTitle");
	By workPhone = By.xpath("//label[text()='Work Phone']/preceding-sibling::input");
	By companyName = By.id("companyName::content");
	By address = By.id("address1::content");
	By city = By.id("city::content");
	By state = By.id("state::content");
	By zip = By.id("postalCode::content");
	By createAccountBtn = By.xpath("//a[@role='button']//*[text()='Create Account']");
	By emailConfirmation = By.xpath("//span[text()='Check Your Email']");

	public void navigateToAccountCreationPage()
	{
		navigateToUrl("https://profile.oracle.com/myprofile/account/create-account.jspx");
		verifyPageTitle(createAccountPageTitle);
	}

	public void enterEmail(String sValue)
	{
		setValue(emailAddressField, sValue, Constants.VS_DELAY_S, "Email");
	}

	public void enterPassword(String sValue)
	{
		setValue(passwordField, sValue, Constants.VS_DELAY_S, "Password");
	}

	public void reEnterPassword(String sValue)
	{
		setValue(retypePasswordField, sValue, Constants.VS_DELAY_S, "Retype Password");
	}

	public void selectCountry(String sValue)
	{
		setDropdownByText(country, sValue, Constants.VS_DELAY_S, "Country");
	}

	public void enterFirstName(String sValue)
	{
		setValue(firstName, sValue, Constants.VS_DELAY_S, "First Name");
	}

	public void enterLastName(String sValue)
	{
		setValue(lastName, sValue, Constants.VS_DELAY_S, "Last Name");
	}

	public void enterWorkPhone(String sValue)
	{
		setValue(workPhone, sValue, Constants.VS_DELAY_S, "Work Phone");
	}

	public void enterCompanyName(String sValue)
	{
		setValue(companyName, sValue, Constants.VS_DELAY_S, "Company Name");
	}

	public void enterAddress(String sValue)
	{
		setValue(address, sValue, Constants.VS_DELAY_S, "address");
	}

	public void enterCity(String sValue)
	{
		setValue(city, sValue, Constants.VS_DELAY_S, "City");
	}

	public void enterJobTitle(String sValue)
	{
		setValue(jobTitle, sValue, Constants.VS_DELAY_S, "Job Title");
	}

	public void selectState(String sValue)
	{
		setDropdownByText(state, sValue, Constants.VS_DELAY_S, "State");
	}

	public void enterZip(String sValue)
	{
		setValue(zip, sValue, Constants.VS_DELAY_S, "Zip");
	}

	public void clickCreateBtn()
	{
		clickElement(createAccountBtn, Constants.VS_DELAY_S, "Create Account Button");
	}

	public void verifyConfirmationMessage()
	{
		waitForElement(emailConfirmation, Constants.M_DELAY_S);
		if (verifyElementExist(emailConfirmation, "Email Confirmation").isDisplayed())
		{
			reportPass("Account Created succesfully, Confirmation is sent to email");
		}
		else
		{
			reportFail("Account was not Created succesfully, please verify the data and try again");
		}
	}
}
