package com.mytest.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener
{
	private static String getTestMethodName(ITestResult iTestResult)
	{
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext)
	{
		System.out.println(iTestContext.getClass().getSimpleName());
	}

	@Override
	public void onFinish(ITestContext iTestContext)
	{
		System.out.println("I am in onFinish method " + iTestContext.getName());
		// Do tier down operations for extentreports reporting!
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult)
	{
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
		ExtentTestManager.startTest(iTestResult.getTestClass().getName(), "Test Case");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult)
	{
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult)
	{
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult)
	{
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult)
	{
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}
