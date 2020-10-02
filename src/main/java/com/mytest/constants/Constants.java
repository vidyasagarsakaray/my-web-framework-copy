package com.mytest.constants;

public class Constants
{
	public static final int ITERATIONS = 5;
	//Delays
	public static final int VVS_DELAY_S = 1;
	public static final int VS_DELAY_S = 3;
	public static final int S_DELAY_S = 7;
	public static final int M_DELAY_S = 10;
	public static final int L_DELAY_S = 20;
	public static final int VL_DELAY_S = 35;
	public static final int MAX_DELAY_S = 500;
	public static final int VVVS_DELAY_MS = 500;
	public static final int VVS_DELAY_MS = 1000;
	public static final int VS_DELAY_MS = 1000;
	public static final int S_DELAY_MS = 3000;
	public static final int M_DELAY_MS = 5000;
	public static final int L_DELAY_MS = 10000;
	public static final int VL_DELAY_MS = 35000;
	// Color
	public static final String ERROR_COLOR = "rgba(255, 0, 0, 1)";
	// Directories
	public static final String PARENTFOLDER_PATH = System.getProperty("user.dir");
	public static final String BROWSER_DRIVERS_PATH = "\\src\\main\\resources\\BrowserDrivers";
	public static final String MAIN_RESOURCES_DIR = "\\src\\main\\resources";
	public static final String EXTENT_REPORTS_DIR = "\\ExtentReports";
	public static final String SCREENSHOTS_DIR = "\\ExtentReports\\Screenshots";
	// Property files
	public static final String CONFIG_PROPERTIES = PARENTFOLDER_PATH + MAIN_RESOURCES_DIR + "\\Config.properties";
	public static final String MISC_PROPERTIES = "Misc.properties";
	
	public static final String CHROME_BROWSER = "chrome";
	public static final String FIREFOX_BROWSER = "ff";
	public static final String IE_BROWSER = "ie";
	public static final String EDGE_BROWSER = "edge";
}
