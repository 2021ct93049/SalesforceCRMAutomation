package com.salesforce.qa.testcases;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.CampaignsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

public class CampaignsPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	CampaignsPage campaignPage;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\resources\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "campaign";
	
	
	public CampaignsPageTest(){
		super();
		
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		initialization(sheetName);
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		accountsPage = new AccountsPage();
		campaignPage = new CampaignsPage();
		
		//homePage = loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority = 1)
	public void createCampaign() throws Exception{
		try {
			
			logger = report.createTest("Campaign Creation", "It's will create Campaign");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			
			String campaignName =  ExcelUtils.getCellData(1, 0);
			String startdate = ExcelUtils.getCellData(1, 1);
			String enddate = ExcelUtils.getCellData(1, 2);
			
			String objectSelect = "Campaigns";
			String objectName ="Campaign";
			
			accountsPage.selectApplication(objectSelect);
			logger.info(objectSelect + " Object selectd");
			
			campaignPage.clickBtn();
			testUtil.verifyNewClicked(objectName);
			type("campaignName_XPATH",campaignName);
			selectCheckBox("activeCheckBox_XPATH");
			campaignPage.saveBtn();
			
	        logger.pass(campaignName+ "Campaign created");
			testUtil.waitForPageLoad(7000);
			
			
			
			campaignPage.editCampaign();
			logger.pass("Campaign Edited");
			testUtil.verifyEditPage();
			type("startDate_XPATH", startdate);
			type("endDate_XPATH", enddate);
			campaignPage.saveBtn();
			
			logger.pass("edited Campaign saved");
				
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
	    	   logger.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
	    	   
	       }
	       
	       report.flush();
	     }
	 
	 @AfterClass
	 public void closeBrowser() {
			driver.close();
	} 	
}
