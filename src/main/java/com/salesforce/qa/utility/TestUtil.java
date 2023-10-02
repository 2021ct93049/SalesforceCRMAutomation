package com.salesforce.qa.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.salesforce.qa.base.TestBase;


public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 30;
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\crm\\qa\\testdata\\FreeCrmTestData.xlsx";
    static Workbook book;
	static Sheet sheet;
	
	static JavascriptExecutor js;

	public void switchToFrame() {
		driver.switchTo().frame("mainpanel");
	}

	/*
	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		
		int rowCount = sheet.getLastRowNum();
	    int cellCount = sheet.getRow(0).getLastCellNum();
		//Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	   
	    Object[][] data = new Object[rowCount][cellCount];
		
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				//data[i][k] = sheet.getRow(i + 1).getCell(k).getStringCellValue().trim();
				
				switch (sheet.getRow(1).getCell(k).getCellType()) {
	               case Cell.CELL_TYPE_NUMERIC:
	                   // Call 'getNumericCellValue()' here instead of using just 'getCell()'
	                   data[i][k] = sheet.getRow(1).getCell(k).getNumericCellValue();
	                   break;
	               case Cell.CELL_TYPE_STRING:
	                   // Call 'getStringCellValue()' here instead of using just 'getCell()'
	                   data[i][k] = sheet.getRow(1).getCell(k).getStringCellValue().trim();
	                   break;
	            }
				// System.out.println(data[i][k]);
			}
		}
		return data;
		
	}*/
	
	/*
	public static String[][] getExcelData(String fileName, String sheetName){
    	
    	String[][] data = null;   	
	  	try
	  	{
	   	FileInputStream fis = new FileInputStream(fileName);
	   	XSSFWorkbook wb = new XSSFWorkbook(fis);
	   	XSSFSheet sh = wb.getSheet(sheetName);
	   	XSSFRow row = sh.getRow(0);
	   	int noOfRows = sh.getPhysicalNumberOfRows();
	   	int noOfCols = row.getLastCellNum();
	   	Cell cell;
	   	data = new String[noOfRows-1][noOfCols];
	   	for(int i =1; i<noOfRows;i++){
		     for(int j=0;j<noOfCols;j++){
		    	   row = sh.getRow(i);
		    	   cell= row.getCell(j);
		    	   data[i-1][j] = cell.getStringCellValue();
	   	 	   }
	   	}
	  	}
	  	catch (Exception e) {
	     	   System.out.println("The exception is: " +e.getMessage());
	     	           	}
    	return data;
	}*/
	
	
	public static String takeScreenshotAtTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		//FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		String screenShotPath=System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		File destination=new File(screenShotPath);
		try 
		{
			FileUtils.copyFile(scrFile, destination);
			System.out.println("Screenshot captured");
		} catch (IOException e) 
		{
			System.out.println("Capture Failed "+e.getMessage());
		}
		
		return screenShotPath;
	}

	public static void runTimeInfo(String messageType, String message) throws InterruptedException {
		js = (JavascriptExecutor) driver;
		// Check for jQuery on the page, add it if need be
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
		Thread.sleep(5000);

		// Use jQuery to add jquery-growl to the page
		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

		// Use jQuery to add jquery-growl styles to the page
		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
		Thread.sleep(5000);

		// jquery-growl w/ no frills
		js.executeScript("$.growl({ title: 'GET', message: '/' });");
		//'"+color+"'"
		if (messageType.equals("error")) {
			js.executeScript("$.growl.error({ title: 'ERROR', message: '"+message+"' });");
		}else if(messageType.equals("info")){
			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		}else if(messageType.equals("warning")){
			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		}else
			System.out.println("no error message");
		// jquery-growl w/ colorized output
		//		js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
		//		js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		//		js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		Thread.sleep(5000);
	}
	
	public void click(String locator){
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void type(String locator, String value){
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(value);
	}

	public String getLabelText(String locator){
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public void verifyNewClicked(String value){
		try {
		String xpath1 = "//h2[text()='New ";
		String xpath2 = ""+value+"']";
		WebElement element = driver.findElement(By.xpath(xpath1+xpath2));
		String text = element.getText();
		if(text.contains(value))
		{
			System.out.println("Text on New Page is"+ text);
			System.out.println("New" +value+"page opened");
		}else
		{
			System.out.println("New" +value+"page not opened");
		}
		}catch (NoSuchElementException exc){
			exc.printStackTrace();
		}
	}
	
	public boolean isElementPresent(String locator){
		try{
			driver.findElement(By.xpath(locator));        
			return true;
		} catch (NoSuchElementException exc){
			exc.printStackTrace();
		}
		return false;
	}


	public void select(String locator, String value){
		WebElement dropdown = driver.findElement(By.xpath(locator));
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	public void selectFromList(String labelName,String value){
		js = (JavascriptExecutor)driver;
		String xpapth = "//label[contains(text(),'"+labelName+"')]/../descendant::button/span";
		//System.out.println(xpapth+ " Field slected");
		
		String listXpath = "//label[contains(text(),'"+labelName+"')]/../descendant::div//following-sibling::span/span";
		//System.out.println(listXpath+ " list values");
		
		WebElement dropdown = driver.findElement(By.xpath(xpapth));
		js.executeScript("arguments[0].click();", dropdown);
		//dropdown.click();
		implicitWait(100);
		
		List<WebElement> options = driver.findElements(By.xpath(listXpath));
		int count = options.size();
		for(int i=0;i<count;i++) {
			 String text= options.get(i).getText();
			 if(text.equalsIgnoreCase(value)) {
				js.executeScript("arguments[0].click();", options.get(i));
				logger.pass(value +" value selected from dropdown for field "+ labelName);
				 //options.get(i).click();
				 break;
			 }
		}
	}

	public String getPageTitle(){
		return driver.getTitle();
	}

	public void setAttribute(String locator, String attValue) {
		WebElement element = driver.findElement(By.xpath(locator));

		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
				element, "value", attValue);
	}

	public void waitForPageLoad(int attValue) throws InterruptedException {
		Thread.sleep(attValue);
	}

	public void implicitWait(int value) {
		driver.manage().timeouts().implicitlyWait(value,TimeUnit.SECONDS);
	}

	public boolean WaitForElement(String xpath, long waitingTimeinsec) throws Exception
	{
		try {

			driver.manage().timeouts().implicitlyWait(waitingTimeinsec,TimeUnit.SECONDS);
			List<WebElement> myDynamicElement = driver.findElements(By.xpath(xpath));
			if (myDynamicElement.size() > 0)
			{
				System.out.println("Success: WaitForElement->Number of Element present is: "+myDynamicElement.size() +"Xpath is:"+xpath);
				return true;
			}
			else
			{
				System.out.println("Unsuccess: WaitForElement->Number of Element present is: "+myDynamicElement.size()+"Xpath is:"+xpath);
				return false;
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("Exception inside WaitForElement:"+xpath);
			return false;
		}
	}

	public void ScrollToElement(WebElement element) throws Exception
	{
		try
		{
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
			System.out.println("Successfully scrolled untill element.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Unable to scroll until elemnt");
		}
	}

	public WebElement WebDriverWaitForElement(WebElement WebElement, long waitingTimeinsec) throws Exception
	{
		WebElement element=null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitingTimeinsec);
			element = wait.until(ExpectedConditions.elementToBeClickable(WebElement));
			return element;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("Could not find the element even after waiting explicitly for ("+waitingTimeinsec+")sec");
			return element;
		}
	}

	public void click(String xpath, String value){
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
        implicitWait(50);
    }
	
	public void verifyEditPage(){
		String xpath = "//div[@data-aura-class='forceDetailPanelDesktop']//h2";
        WebElement element = driver.findElement(By.xpath(xpath));
        String getText = element.getText();
        if(getText.contains("Edit"))
        {
        	Assert.assertTrue(true);
        	logger.info("landed on " +getText);
        }
        implicitWait(50);
    }

}
