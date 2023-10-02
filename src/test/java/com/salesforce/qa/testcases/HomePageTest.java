package com.salesforce.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.TestUtil;


public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	//ContactsPage contactsPage;

	public HomePageTest() {
		super();
	}

	//test cases should be separated -- independent with each other
	//before each test case -- launch the browser and login
	//@test -- execute test case
	//after each test case -- close the browser
	
	@BeforeMethod
	public void setUp() throws Exception {
		initialization("Login");
		testUtil = new TestUtil();
		//contactsPage = new ContactsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test(priority=1)
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "Lightning Experience","Home page title not matched");
	}
	
	/*
	 * @Test(priority=2) public void verifyUserNameTest(){ testUtil.switchToFrame();
	 * Assert.assertTrue(homePage.verifyCorrectUserName()); }
	 * 
	 * @Test(priority=3) public void verifyContactsLinkTest(){
	 * testUtil.switchToFrame(); contactsPage = homePage.clickOnContactsLink(); }
	 */
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	

}
