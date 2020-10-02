# Selenium Automation Framework for Web Testing

Here is the Automation Framework suite for Selenium, which supports Cross Browser Testing.

## Test Framework

Designed using : Selenium Java + Maven + TestNG + Selenium Webdriver Page Object Model

### Prerequisites

Applications/Softwares needed to import and run the Test Framework

```
1. Java - V1.8
2. Maven - V3+
3. Eclipse IDE
4. Selenium WebDriver
5. TestNG

```

### Importing

Following are the steps to import this framework into Eclipse/IntelliJ IDE

```
Prerequisite: Make sure you have Maven plugin installed in your Eclipse IDE
1. Open Eclipse
2. Click File > Import
3. Type Maven in the search box under Select an import source:
4. Select Existing Maven Projects
5. Click Next
6. Click Browse and select the folder that is the root of the Maven project (probably contains the pom.xml file)
7. Click Next
8. Click Finish

Make sure you've maven configured in your machine.
```
### Framework Description : Break down into end to end tests

Below is a general overview of our Framework Structure.

```
* The classes in the framework are classified under two main packages, which are the main and the test packages.
* Library is a common library class comprising of reusable methods.
* Constants class is a general class containing constant variables, whose values can be used elsewhere during execution.
* The Test package contains sub-packages having a Base Tests, containing the actual scripts.
* The Browser Drivers(Chrome Driver) is placed under 'BrowserDrivers' folder in 'main/resources/BrowserDrivers'.
* Also, there is a Config property file containing variables and their values as key-value pairs such as access token, url's etc.
* Any changes made to the values in Conig properties will get reflected during the runtime of the tests.

```


## Running the tests

There is an xml file to run the test cases using Testng, to run it, click on Run As > TestNG Suite.
	  
Also, the Common-Suite can be run without importing into IDE by opening Command-prompt, navigating to the framework's root path and running the following command.

```
mvn clean install
```

## Execution Results

The Execution results for Traditional Approach will get generated and saved under the following folder with the file names as mentioned.

```
./ExtentReports/ExtentReportResults.html

Note : The Test results get updated automatically every time a test case is ran.	
```

## Built With

* [Java](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html) - Language 
* [Selenium](https://www.selenium.dev/) - Web-Framework
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Vidyasagar Sakaray** - *Github* : [vidyasagarsakaray](https://github.com/vidyasagarsakaray)

