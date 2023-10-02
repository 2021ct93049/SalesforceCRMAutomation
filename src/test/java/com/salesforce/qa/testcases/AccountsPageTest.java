package com.salesforce.qa.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;




public class AccountsPageTest extends TestBase {

	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\resources\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "accounts";
	
	
	public AccountsPageTest(){
		super();
		
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		initialization(sheetName);
		testUtil = new TestUtil();
		accountsPage = new AccountsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		 
	}

	@Test(priority = 1)
	public void createAccount() throws Exception{
		try {
		
		logger = report.createTest("Account Creation", "It's will create an Account.");
			
		ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
		String accountName =  ExcelUtils.getCellData(1, 0);
		String rating = ExcelUtils.getCellData(1, 1);
		String phone = ExcelUtils.getCellData(1, 2);
		String fax = ExcelUtils.getCellData(1, 3);
		String accountNum = ExcelUtils.getCellData(1, 4);
		String billingCity =  ExcelUtils.getCellData(1, 5);
		String shippingCountry = ExcelUtils.getCellData(1, 6);
		String expirationDate = ExcelUtils.getCellData(1, 7);
		
		String accountText = accountName;
		String objectSelect = "Accounts";
		
		//accountsPage.clickAppLauncher();
		//accountsPage.clickViewAll();
		accountsPage.selectApplication(objectSelect);
		logger.info(objectSelect + "Object selectd");
		accountsPage.clickBtn();
		validateCreateNewAccount(accountName); 
		
		selectDropdown("rating_XPATH", rating);
		accountsPage.saveBtn();
		//accountsPage.verifyAccText(accountText);
		verifyAccText("verifyAccount_XPATH", accountText);
		logger.info(accountText + "Account Name verified");
		testUtil.waitForPageLoad(5000);
		
		editPage();
		type("phone_XPATH",phone);
		type("fax_XPATH",fax);
		type("accountNumber_XPATH",accountNum);
		type("expiratnDate_XPATH",expirationDate);
		accountsPage.saveBtn();
		
		//click("detailsTab_XPATH");
		
		logOff();
		
		}catch(Exception e)
		{
		e.printStackTrace();
		
	}
		
	}
	
	

	public static void takeScreenshot(String fileName) throws IOException{
		// Take screenshot and store as a file format
		String destination = System.getProperty("user.dir")+ "\\src\\main\\resources\\screenshot\\";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// now copy the screenshot to desired location using copyFile //method
		FileUtils.copyFile(src, 
		new File(destination + fileName +".png"));
	}
	
	  public void validateCreateNewAccount(String accountName){
	  //contactsPage.createNewContact("Mr.", "Tom", "Peter", "Google");
	  accountsPage.accountCreation(accountName); 
	  }
	
	/*
	 * @DataProvider(name ="getCRMTestData") public Object[][] getData() throws
	 * Exception{ Object[][] data = TestUtil.getTestData(sheetName); return data; }
	 */
	
	  @AfterMethod
	    public void tearDown(){

	        if(driver != null){
	            driver.close();
	        }
	    }


	

	
	
}
