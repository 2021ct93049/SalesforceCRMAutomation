package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;


public class CampaignsPage extends TestBase{

	@FindBy(xpath = "//a[@title='New']")
	WebElement newBtn;
	
	@FindBy(xpath = "//button[@title='Save']")
	WebElement saveButton;
	
	
	@FindBy(xpath = "(//ul[contains(@class,'slds-button-group')])[2]//li[4]//a[@title='Show 8 more actions']")
	WebElement editOption;
	
	@FindBy(xpath = "//a[@title='Edit']")
	WebElement editBtn;
	
	TestUtil testUtil;

	public CampaignsPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}

	public void clickBtn() throws Exception
	{
		newBtn.click();
		testUtil.waitForPageLoad(500);
	}
	
	
	public void saveBtn() throws Exception
	{
		try {
		saveButton.click();
		testUtil.waitForPageLoad(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void editCampaign() throws Exception
	{
		try {
		editOption.click();
		testUtil.waitForPageLoad(500);
		editBtn.click();
		testUtil.waitForPageLoad(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
		
}
