package com.salesforce.qa.testcases;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

public class LeadsPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	LeadsPage leadsPage;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\resources\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "leads";
	
	
	public LeadsPageTest(){
		super();
		
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		initialization(sheetName);
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		accountsPage = new AccountsPage();
		leadsPage = new LeadsPage();
		
		//homePage = loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority = 1)
	public void createContact() throws Exception{
		try {
			
			logger = report.createTest("Contact Creation", "It's will create an Account.");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String salutation =  ExcelUtils.getCellData(1, 0);	
			String firstname =  ExcelUtils.getCellData(1, 1);	
			String lastname	=  ExcelUtils.getCellData(1, 2);
			String phone = ExcelUtils.getCellData(1, 3);
			String mobile =  ExcelUtils.getCellData(1, 4);
			String selectleadstatus = ExcelUtils.getCellData(1, 5);
			String company = ExcelUtils.getCellData(1, 6);

			String objectSelect = "Leads";
			String labelName = "Lead Status";
			String objectName ="Lead";
			
			accountsPage.selectApplication(objectSelect);
			logger.info(objectSelect + " Object selectd");
			
			leadsPage.clickBtn();
			testUtil.verifyNewClicked(objectName);
			
			selectDropdown("salutation_XPATH", salutation);
			leadsPage.sendFirstName(firstname);
			leadsPage.sendLastName(lastname);
			leadsPage.sendCompanyName(company);
			testUtil.selectFromList(labelName, selectleadstatus);
			leadsPage.saveBtn();
	        logger.pass("Lead created");
			testUtil.waitForPageLoad(5000);
			
			editPage();
			logger.pass("Lead Edited");
			type("leadPhone_XPATH",phone);
			type("leadMobile_XPATH",mobile);
			leadsPage.saveBtn();
			logger.pass("edited Lead saved");
				
			logOff();
			logger.pass("logged out successfully");
			
			}catch(Exception e)
			{
			e.printStackTrace();
			}
		 
		}
	
	 @AfterMethod
	    public void tearDown(ITestResult result) throws IOException{

	       if(result.getStatus() == ITestResult.FAILURE) {
	    	   logger.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
	       }
	       else if(result.getStatus() == ITestResult.SUCCESS) {
	    	   logger.fail("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
	       }
	       
	       report.flush();
	 
	 }
	 
}
