package com.salesforce.qa.testcases;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.pages.OpportunityPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

public class OpportunityPageTest extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	OpportunityPage opportunityPage;
	public static ExtentTest logger;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\resources\\CRMTestData.xlsx";
	public static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "opportunity";
	
	
	public OpportunityPageTest(){
		super();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		initialization(sheetName);
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		accountsPage = new AccountsPage();
		opportunityPage = new OpportunityPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test(priority = 1)
	public void createOpportunity() throws Exception{
		try {
			//logger = report.createTest("Opportunity Creation", "It's will create an Opportunity.");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String opportunityname =  ExcelUtils.getCellData(1, 0);	
			String closedate =  ExcelUtils.getCellData(1, 1);	
			String selectStage = ExcelUtils.getCellData(1, 2);
			String amount = ExcelUtils.getCellData(1, 3);
			String leadsource = ExcelUtils.getCellData(1, 4);
			
			String objectSelect = "Opportunities";
			String labelName = "Stage";
			String objectName ="Opportunity";
			String selectLeadSource = "Lead Source";
			
			
			accountsPage.selectApplication(objectSelect);
			//logger.pass(objectSelect + "Object selectd");
			screenShotMsg(objectSelect + " Object selectd");
			opportunityPage.clickBtn();
			screenShotMsg("Clicked on New Button");
			testUtil.verifyNewClicked(objectName);
			
			type("opportunityName_XPATH",opportunityname);
			type("closeDate_XPATH",closedate);
			testUtil.selectFromList(labelName, selectStage);
			opportunityPage.saveBtn();
			
			screenShotMsg("Opportunity created");
			testUtil.waitForPageLoad(5000);
			
			editPage();
			screenShotMsg("Opportunity Edited");
			type("optyAmt_XPATH",amount);
			testUtil.selectFromList(selectLeadSource, leadsource);
			opportunityPage.saveBtn();
			screenShotMsg("edited Opportunity saved");
				
			logOff();
			screenShotMsg("logged out successfully");
			}catch(Exception e)
			{
			e.printStackTrace();
			}
		 
		}
	
	
	@AfterMethod
	 public void tearDown() throws IOException{
		   report.flush();
	       closeBrowser();
	  }
	
	 
}
