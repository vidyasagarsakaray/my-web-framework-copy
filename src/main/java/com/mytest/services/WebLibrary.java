package com.mytest.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.mytest.constants.Constants;
import com.mytest.utils.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

public class WebLibrary
{
//	private Logger LOGGER;
	private WebDriver driver;
	Date date;
	SimpleDateFormat formatter;
	JSONObject jsonObject = null;
	private HashMap<String, Properties> propertiesMap;
	Instant start;
	Instant finish;

	public WebLibrary()
	{
//		LOGGER = LogManager.getLogger(WebLibrary.class.getName());
		formatter = new SimpleDateFormat("MM-dd-yyyy");
		date = new Date();
		propertiesMap = new HashMap<String, Properties>();
	}

	public void setDriver(WebDriver driver)
	{
		this.driver = driver;
	}

	public WebDriver getDriver()
	{
		return driver;
	}

	public WebDriver invokeBrowser(String browserType)
	{
		try
		{
			String iePath = Constants.PARENTFOLDER_PATH + Constants.BROWSER_DRIVERS_PATH + "\\IEDriverServer.exe";
			String chromePath = Constants.PARENTFOLDER_PATH + Constants.BROWSER_DRIVERS_PATH + "\\chromedriver.exe";
			String edgePath = Constants.PARENTFOLDER_PATH + Constants.BROWSER_DRIVERS_PATH + "\\MicrosoftWebDriver.exe";
			String firefoxPath = Constants.PARENTFOLDER_PATH + Constants.BROWSER_DRIVERS_PATH + "\\geckodriver.exe";
			if ("ff".equalsIgnoreCase(browserType))
			{
				System.setProperty("webdriver.gecko.driver", firefoxPath);
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
				FirefoxProfile ffProfile = new FirefoxProfile();
				ffProfile.setPreference("browser.tabs.remote.autostart.2", false);
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(ffProfile);
				driver = new FirefoxDriver(options);
			}
			else if ("chrome".equalsIgnoreCase(browserType))
			{
				System.setProperty("webdriver.chrome.driver", chromePath);
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				if (getPropertyValue(Constants.CONFIG_PROPERTIES, "headlessMode").equals("y"))
					chromeOptions.addArguments("--headless");
				chromeOptions.addArguments("start-maximized");
				driver = new ChromeDriver(chromeOptions);
			}
			else if ("ie".equalsIgnoreCase(browserType))
			{
				System.setProperty("webdriver.ie.driver", iePath);
				driver = new InternetExplorerDriver(new InternetExplorerOptions());
			}
			else if ("edge".equalsIgnoreCase(browserType))
			{
				System.setProperty("webdriver.edge.driver", edgePath);
				driver = new EdgeDriver();
			}
			else
			{
				reportFail("The specified browser is '" + browserType
						+ "'which couldnt be launched. Please specify valida browser.");
			}
			if (!"edge".equalsIgnoreCase(browserType))
			{
				driver.manage().deleteAllCookies();
			}
			driver.manage().window().maximize();
			setDriver(driver);
			reportPass("Invoked browser is : " + browserType);
			return driver;
		}
		catch (Exception e)
		{
			reportError("Exception while invoking browser : " + e);
		}
		return driver;
	}

	public void reportPass(String reportText)
	{
		ExtentTestManager.getTest().log(LogStatus.PASS, reportText);
	}

	public void reportError(String reportText)
	{
		ExtentTestManager.getTest().log(LogStatus.ERROR, reportText);

		if (getDriver() != null)
		{
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File source = screenshot.getScreenshotAs(OutputType.FILE);

			LocalDateTime today = LocalDateTime.now();
			DateTimeFormatter formatToday = DateTimeFormatter.ofPattern("MMddYYYYHHMMSS");
			String formattedToday = today.format(formatToday);

			String dest = Constants.PARENTFOLDER_PATH + Constants.SCREENSHOTS_DIR + "\\" + "SCREENSHOT_"
					+ formattedToday + ".png";
			File destination = new File(dest);
			try
			{
				FileUtils.copyFile(source, destination);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			ExtentTestManager.getTest().log(LogStatus.ERROR, ExtentTestManager.getTest().addScreenCapture(dest));
			ExtentTestManager.getTest().log(LogStatus.ERROR, ExtentTestManager.getTest()
					.addScreenCapture("Screenshots/" + "SCREENSHOT_" + formattedToday + ".png"));
		}

		closeBrowser();
		Assert.fail(reportText);
	}

	public void reportFail(String reportText)
	{
		ExtentTestManager.getTest().log(LogStatus.FAIL, reportText);
		closeBrowser();
		Assert.fail(reportText);
	}

	public void closeBrowser()
	{
		if (getDriver() != null)
		{
			getDriver().quit();
			reportPass("Closed Browser");
		}
	}

	public void navigateToUrl(String url)
	{
		try
		{
			getDriver().navigate().to(url);
			reportPass("Navigated to URL : " + url);
		}
		catch (Exception e)
		{
			reportError("Error while navigating to URL : " + url);
		}
	}

	/**
	 * Getting value from properties file.
	 * 
	 * @param sPropFileName : Properties file to be read.
	 * @param key           : key of the property.
	 * @return : value of the provided key.
	 */
	public String getPropertyValue(String sPropFileName, String key)
	{
		String value = null;
		try
		{
			Properties props = null;
			try
			{
				props = getPropFile(sPropFileName);
			}
			catch (Exception e)
			{
				reportError("The mentioned properties file '" + sPropFileName + "' was not found");
				return null;
			}
			value = props.getProperty(key).trim();
			if (value == null)
			{
				reportError("Object with key : '" + key + "' not found in the file : " + sPropFileName);
				return null;
			}
		}
		catch (Exception e)
		{
			reportError("Exception while getting the property value of the key '" + key + "'");
		}
		return value.trim();
	}

	/**
	 * @param sPropFileName : property file name
	 * @return obj.get(sPropFileName): Property file
	 * @throws IOException
	 */
	public Properties getPropFile(String sPropFileName) throws IOException
	{
		Properties prop;
		if (propertiesMap.get(sPropFileName) == null)
		{
			prop = new Properties();
			prop.load(new FileInputStream(sPropFileName));
			propertiesMap.put(sPropFileName, prop);
		}
		return propertiesMap.get(sPropFileName);
	}

	/**
	 * Method waits until element becomes visible, if element is not visible in 10
	 * seconds then throws exception
	 * 
	 * @param by            : Element locator to recognize the element
	 * @param timeOutInSecs TODO
	 */
	public void waitForElement(By by, int timeOutInSecs)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			wait.until(ExpectedConditions.elementToBeClickable(by));
			wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class).pollingEvery(1,
					TimeUnit.SECONDS);
		}
		catch (Exception e)
		{
//			reportError("Exception while waiting for the element with By locator : " + by + ", Exception : "
//					+ e.getMessage());
		}
	}

	public void waitForElementAndContinue(By by, int timeOutInSecs)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			wait.until(ExpectedConditions.elementToBeClickable(by));
			wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class).pollingEvery(1,
					TimeUnit.SECONDS);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Method for Fluentwait, Ignore NoSuchElementException and
	 * StaleElementReferenceException
	 * 
	 * @param by    : Element locator
	 * @param iSecs : Number of seconds to wait
	 */
	public void fluentWait(final By locator, int timeInSecs)
	{
		try
		{
			delay(Constants.VVS_DELAY_MS);
			new FluentWait<WebDriver>(getDriver()).withTimeout(timeInSecs, TimeUnit.SECONDS)
					.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(Exception.class)
					.until(new ExpectedCondition<WebElement>()
					{
						public WebElement apply(WebDriver driver)
						{
							return driver.findElement(locator);
						}
					});
			waitForElement(locator, timeInSecs);
		}
		catch (Exception e)
		{
			reportError("Exception in fluent wait while waiting for the element with locator : " + locator
					+ ", Exception : " + e.getMessage());
		}
	}

	public boolean waitForJStoLoad(int timeOutInSecs)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSecs);
			// wait for jQuery to load
			ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>()
			{
				@Override
				public Boolean apply(WebDriver driver)
				{
					try
					{
						return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
					}
					catch (Exception e)
					{
						return true;
					}
				}
			};
			// wait for Javascript to load
			ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>()
			{
				@Override
				public Boolean apply(WebDriver driver)
				{
					return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString()
							.equals("complete");
				}
			};
			return wait.until(jQueryLoad) && wait.until(jsLoad);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Method sets value to text box after clearing and clicking
	 * 
	 * @param byVal       : Element locator
	 * @param sValue      : Value to be be entered in the element
	 * @param iSecs       : Number of seconds to wait
	 * @param sReportText : Report text
	 * @return : webelement used for setting text
	 */
	public WebElement setValue(By byVal, String sValue, int iSecs, String sReportText)
	{
		WebElement element = null;
		try
		{
			fluentWait(byVal, iSecs);
			element = verifyElementExist(byVal, sReportText);
			element.click();
			String elementValue = element.getAttribute("value");
			if (!elementValue.isEmpty() && elementValue != null)
			{
				element.clear();
			}
			element.sendKeys(sValue);
			reportPass("The text '" + sValue + "' was successfully entered into '" + sReportText + "' field");
		}
		catch (Exception e)
		{
			reportError("Exception while entering text '" + sValue + "' into the field '" + sReportText
					+ "', Exception : " + e.getMessage());

		}
		return element;
	}

	/**
	 * Method sets value to text box after clearing and clicking
	 * 
	 * @param byVal       : Element locator
	 * @param sValue      : Encrypted Value to be be entered in the element
	 * @param iSecs       : Number of seconds to wait
	 * @param sReportText : Report text
	 * @return : webelement used for setting text
	 */
	/*
	 * public WebElement setPassword(By byVal, String sValue, int iSecs, String
	 * sReportText) { WebElement element = null; try { fluentWait(byVal, iSecs);
	 * element = verifyElementExist(byVal, sReportText); element.click(); String
	 * elementValue = element.getAttribute("value"); if (!elementValue.isEmpty() &&
	 * elementValue != null) { element.clear(); }
	 * element.sendKeys(EncryptData.getDecryptValue(sValue));
	 * reportPass("The encrypted text '" + sValue +
	 * "' was successfully entered into '" + sReportText + "' field"); } catch
	 * (Exception e) { reportError("Exception while entering the encrypted text '" +
	 * sValue + "' into the field '" + sReportText + "', Exception : " +
	 * e.getMessage()); } return element; }
	 */

	/**
	 * Method clicks on element for the given By value
	 * 
	 * @param byVal       : Element locator
	 * @param iSecs       : Number of seconds to wait
	 * @param sReportText : Report tex
	 */
	public void clickElement(By byVal, int iSecs, String sReportText)
	{
		try
		{
			fluentWait(byVal, iSecs);
			WebElement element = verifyElementExist(byVal, sReportText);
			scrollToElement(element);
			element.click();
			reportPass(sReportText + " was successfully clicked");
		}
		catch (Exception e)
		{
			reportError("Exception while clicking the element with By locator : " + byVal + ", Exception : "
					+ e.getMessage());

		}
	}

	public void clickElementUsingJs(By byVal, int iSecs, String sReportText)
	{
		try
		{
			delay(Constants.VVVS_DELAY_MS);
			new FluentWait<WebDriver>(getDriver()).withTimeout(iSecs, TimeUnit.SECONDS)
					.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class).until(new ExpectedCondition<WebElement>()
					{
						public WebElement apply(WebDriver driver)
						{
							return driver.findElement(byVal);
						}
					});
			WebElement element = verifyElementExist(byVal, sReportText);
			scrollToElement(element);
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", element);
			reportPass(sReportText + " was successfully clicked");
		}
		catch (Exception e)
		{
			reportError("Exception while clicking the element with By locator : " + byVal + ", Exception : "
					+ e.getMessage());

		}
	}

	public void clickElementUsingAction(By byVal, int iSecs, String sReportText)
	{
		try
		{
			fluentWait(byVal, iSecs);
			WebElement element = verifyElementExist(byVal, sReportText);
			scrollToElement(element);
			reportPass(sReportText + " was successfully clicked");
		}
		catch (Exception e)
		{
			reportError("Exception while clicking the element with By locator : " + byVal + ", Exception : "
					+ e.getMessage());

		}
	}

	/**
	 * Methods sets value for drop down or select box using text content
	 * 
	 * @param byVal       : By locator of dropdown
	 * @param sVal:       Value to be set for dropdown
	 * @param iSecs       : waits until this value for an element to visible
	 * @param sReportText : text used for reporting about element
	 */
	public WebElement setDropdownByText(By byVal, String sVal, int iSecs, String sReportText)
	{
		WebElement element = null;
		try
		{
			fluentWait(byVal, iSecs);
			element = verifyElementExist(byVal, sReportText);
			verifyElementExist(byVal, sReportText);
			Select select = new Select(element);
			select.selectByVisibleText(sVal);
			reportPass(sVal + " is selected");
		}
		catch (Exception e)
		{
			reportError("Exception in setDropdownValue()" + e);
		}
		return element;
	}

	public void delay(int timeOutInMilliSeconds)
	{
		try
		{
			Thread.sleep(timeOutInMilliSeconds);
		}
		catch (InterruptedException e)
		{
			reportError(e.getMessage());
		}
	}

	/**
	 * Method to verify element present on Page, verify whether duplicate elements
	 * exists and reports the passed text
	 * 
	 * @param byVal       : Element locator
	 * @param elementName : Name of the element to be verified
	 * @return : Element
	 */
	public WebElement verifyElementExist(By byVal, String elementName)
	{
		WebElement element = null;
		try
		{
			List<WebElement> allElements = getDriver().findElements(byVal);
			int size = allElements.size();
			if (size != 0)
			{
				if (size == 1)
				{
					element = allElements.get(0);
				}
				else
				{
					reportError("Found duplicate elements for the locator : " + byVal);
				}
			}
			else
			{
				reportError("The element with locator : " + byVal + " was not displayed on the page");
			}
		}
		catch (Exception e)
		{
			reportError("Exception while verifying for existence of the element with locator : " + byVal
					+ ", Exception : " + e.getMessage());

		}
		return element;
	}

	public void waitForElementToDisappear(By byVal, int timeOutInSecs)
	{
		try
		{
			reportPass("Waiting for the element with By locator " + byVal + " to disappear from the page");
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSecs);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byVal));
			wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class).pollingEvery(1,
					TimeUnit.SECONDS);
			wait.ignoring(Exception.class).pollingEvery(1, TimeUnit.SECONDS);
			reportPass("The element with By locator " + byVal + " was disappeared from the page");
		}
		catch (Exception e)
		{
			reportError("Exception while waiting for the element with By locator " + byVal
					+ " to disappear from the page, Exception : " + e.getMessage());

		}
	}

	/**
	 * Method to verify whether element present on page
	 * 
	 * @param by    : Element Locator
	 * @param iSecs : Number of seconds to wait
	 * @return boolean isPresent : true or false
	 */
	public boolean isElementPresent(By by, String reportText)
	{
		boolean isPresent = true;
		try
		{
			if (getDriver().findElements(by).isEmpty())
			{
				reportPass("The element " + reportText + " was not present on the page");
				isPresent = false;
			}
			else
			{
				reportFail("The element " + reportText + " with By locator : " + by + " was not present on the page");
			}
		}
		catch (Exception e)
		{
			reportFail("An Exception occured while locating the element '" + reportText + "' with By locator" + by
					+ "'. Exception : " + e);
			isPresent = false;
		}
		return isPresent;
	}

	public void scrollToElement(WebElement element)
	{
		// delay(500);
		String scrollElementIntoMiddle = "var viewPortHeight =Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
		// ((JavascriptExecutor)
		// getDriver()).executeScript("arguments[0].scrollIntoView();", element);
		delay(300);
		new Actions(getDriver()).moveToElement(element).build().perform();
		delay(300);
	}

	public void verifyPageTitle(String expectedPageTitle)
	{
		String actualPageTitle = getDriver().getTitle().trim();
		if (expectedPageTitle.trim().equals(actualPageTitle))
		{
			reportPass("Succesfully navigated to '" + expectedPageTitle + "' Page");
		}
		else
		{
			reportFail("Did not navigate to the right page. Expected page title - '" + expectedPageTitle
					+ "', Actual page title - '" + actualPageTitle + "'");
		}
	}
}
