package com.salesforce.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.salesforce.qa.utility.DateUtils;
import com.salesforce.qa.utility.TestUtil;


public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static Properties prop1;
	public static WebDriverWait wait;
	
	public static ExtentTest logger;
	
	public static ExtentHtmlReporter extent = new ExtentHtmlReporter(
			System.getProperty("user.dir") + "\\Report\\ExtentReport_" + DateUtils.getTimeStamp() + ".html");
	public static ExtentReports report = new ExtentReports();

	public static final String PROJDIR = System.getProperty("user.dir");
	private static final String ORFILE_PATH = PROJDIR
			+ "\\src\\main\\java\\com\\salesforce\\qa\\config\\objectrepo.properties";


	public static Properties config = new Properties();
	public static Properties objectrepo = new Properties();
	private static FileInputStream fis;

	public TestBase() {
		try {
			
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\salesforce\\qa\\config\\config.properties");
			prop.load(ip);

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\salesforce\\qa\\config\\objectrepo.properties");
			objectrepo.load(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpSuite()
	{
		
	}
	
	public static void initialization(String objectName) {
		report.attachReporter(extent);
		logger = report.createTest(objectName +" creation");

		try {
		String browserName = prop.getProperty("browser");

		String chromePath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\executables\\chromedriver\\chromedriver.exe";
		String geckoPath = System.getProperty("user.dir")
				+ "src\\main\\resources\\executables\\geckodriver\\geckodriver.exe";

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", geckoPath);
			driver = new FirefoxDriver();
		}
	
		driver.manage().window().maximize();
		Thread.sleep(1000);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		//logger.log(Status.INFO, "Driver Maiximized.");
		screenShotMsg("Driver Maiximized");
		driver.get(prop.getProperty("url"));
		Thread.sleep(1000);
		//logger.log(Status.INFO, "Salesforce Website is opened");
		screenShotMsg("Launched Salesforce");
		} catch (Exception e) {
		} 	
	}

	public void switchToClassic() {
		// Switching to the classic version
		String currUrl = driver.getCurrentUrl();
		if (currUrl.contains("lightning")) {
			driver.findElement(By.xpath(prop.getProperty("Profile_Xpath"))).click();
			driver.findElement(By.xpath(prop.getProperty("switch2Classic_Xpath"))).click();
			System.out.println("Successfully Switched to the Classic version of salesforce");
		}
	}

	private WebElement findElementByLocator(String locator) {
		WebElement element = null;
			
		if (locator.endsWith("_CSS")) {
			element = driver.findElement(By.cssSelector(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			element = driver.findElement(By.xpath(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			element = driver.findElement(By.id(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_TAG")) {
			element = driver.findElement(By.tagName(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_CLASS")) {
			element = driver.findElement(By.className(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_LINK")) {
			element = driver.findElement(By.linkText(objectrepo.getProperty(locator)));
		}
		
    	return element;
	}

	public void verifyAccText(String locator, String ActualName) throws Exception {

		String getText = findElementByLocator(locator).getText();
		implicitWait(20);
		if (getText.equalsIgnoreCase(ActualName)) {
			System.out.println("Text matches");
		} else {
			System.out.println("Text doesn't match");
		}
	}

	public void selectDropdown(String locator, String dropdownValue) {
		WebElement element = findElementByLocator(locator);
		// new Actions(driver).moveToElement(element).perform();
		element.click();
		implicitWait(30);
		String locator2 = "//span[contains(@class,'slds-truncate')]/descendant-or-self::*[text()='" + dropdownValue+ "']";
		WebElement selectValue = driver.findElement(By.xpath(locator2));
		String actualVal = selectValue.getText();
		if (actualVal.equalsIgnoreCase(dropdownValue)) {
			selectValue.click();
			implicitWait(30);
		}

	}

	public void implicitWait(int value) {
		driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);
	}

	public void logOff() throws Exception {
		try {

			String profile = "//button[contains(@class,'userProfile')][1]";
			Thread.sleep(3000L);
			driver.findElement(By.xpath(profile)).click();
			Thread.sleep(3000L);
			driver.findElement(By.xpath("(//a[text()='Log Out'])[1]")).click();
			System.out.println("Logging out from salesforce Lightning");
			Thread.sleep(5000L);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to Logout from Salesforce");
		}

	}

	public void editPage() {
		try {

			String editObject = "(//ul[@class='slds-button-group-list'])[1]/li[4]//button";
			String editxpath = "//a[@role='menuitem']/span[contains(text(),'Edit')]";

			WebElement editbutton = driver.findElement(By.xpath(editObject));
			editbutton.click();
			Thread.sleep(1000);
			WebElement clickEdit = driver.findElement(By.xpath(editxpath));
			clickEdit.click();
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to Logout from Salesforce");
		}
	}

	public void type(String locator, String value) {
		WebElement element = findElementByLocator(locator);
		element.sendKeys(value);
		implicitWait(100);
	}

	public void clickButton(String buttonName) {
		String buttonXpath = "//button[contains(text(),'" + buttonName.trim() + "')]";
		WebElement element = driver.findElement(By.xpath(buttonXpath));
		element.click();
		implicitWait(60);
		
	}

	public void click(String locator) {
		WebElement element = findElementByLocator(locator);
	//	test.log(LogStatus.INFO, "Trying to click on : " + locator);
		element.click();
		
		implicitWait(100);
	}
	
	public void selectCheckBox(String locator) {
		WebElement checkBoxElement = findElementByLocator(locator);
		boolean isSelected = checkBoxElement.isSelected();
				
		//performing click operation if element is not checked
		if(isSelected == false) {
			checkBoxElement.click();
		}
	}
   
	public void closeBrowser()
	{
		driver.close();
	}

	public void attachScreenShot(ITestResult result)
	{	try {
		if(result.getStatus() == ITestResult.FAILURE) {
	    	   
				logger.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
			
	       }
	       else if(result.getStatus() == ITestResult.SUCCESS) {
	    	   logger.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
	       }
		}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void screenShotMsg(String msg)
	{
		try {
			logger.pass(msg, MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
			//logger.pass("edited Opportunity saved", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void WebDriverWaitForElement(String xpath,int waitingTimeinsec) throws Exception {
		WebElement element=null;
		     try {
        	
        	 	WebDriverWait wait = new WebDriverWait(driver, waitingTimeinsec);
        	 	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        	  }
         	catch(Exception e)
         	{
         		e.printStackTrace();
         		System.out.println("Could not find the element even after waiting explicitly for ("+waitingTimeinsec+")sec");
         	          	 
         	}
     }

	
}
	/*
	 e_driver = new EventFiringWebDriver(driver); // Now create object of
	 EventListerHandler to register it with // EventFiringWebDriver eventListener
	 = new WebEventListener(); e_driver.register(eventListener); driver =
	 e_driver;*/

