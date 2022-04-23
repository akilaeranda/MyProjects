package com.ifs.AutomationExerciseNew;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

@Test
public class TestNGExercise {

	static ExtentHtmlReporter reporter;
	static ExtentReports extent;
	static ExtentTest logger;
	
	static WebDriver driver;
	
	@BeforeSuite
	public void automationTechnicalExercise(){
		System.out.println("Automation Technical Exercise using Java, Selenium, TestNG");
		
		reporter=new ExtentHtmlReporter("./Reports/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
	@Test(priority = 0)
	public void setUpChrome(){
		logger=extent.createTest("Test Automation in eBay site");
		
		//1. Open Google Chrome
        System.setProperty("webdriver.chrome.driver","D:\\QA\\TestAutomation\\SoftwareSetups\\SeleniumWebDriver\\chromedriver.exe");
        driver = new ChromeDriver(); 
        driver.manage().window().maximize();
        logger.log(Status.INFO, "Test Automation in eBay site using Selenium and TestNG");
        logger.log(Status.PASS, "Open Google Chrome");
        
        //2. Navigate to Google
        driver.get("https://www.google.lk/");
        logger.log(Status.PASS, "Navigate to Google");
    }
	
	@Test(priority = 1)
    public void siteNavigation(){
    	//3. Search for “eBay”
    	driver.findElement(By.name("q")).sendKeys("eBay");
    	driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    	logger.log(Status.PASS, "Search for eBay");

    	//4. Click on the appropriate result from the Google search result and navigate to the eBay Home page
    	driver.findElement(By.partialLinkText("eBay: Electronics, Cars, Fashion, Collectibles & More")).click();
    	logger.log(Status.PASS, "Navigate to the eBay Home page");
    }
    
	@Test(priority = 2)
    public void siteLogIn(){
    	/* 5. Sign In with eBay using a Username/Email and Password (You can use an existing account details here, 
    	 * no need to Sign Up via the script)
    	 */
    	driver.findElement(By.partialLinkText("Sign in")).click();
    	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    	driver.findElement(By.id("userid")).sendKeys("ukakilaeranda@gmail.com");
    	driver.findElement(By.id("signin-continue-btn")).click();
    	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    	driver.findElement(By.id("pass")).sendKeys("Welcome@01");
    	driver.findElement(By.name("sgnBt")).click();
    	//driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    	//driver.findElement(By.id("pass")).sendKeys("Welcome@01");
    	//driver.findElement(By.name("sgnBt")).click();
    	logger.log(Status.PASS, "Sign In with eBay using a Username/Email and Password");
    	
    	//6. Verify that you signed in successfully with eBay
    	driver.findElement(By.id("gh-ug")).click();
    	SoftAssert softAssert = new SoftAssert();
    	//String getActualTitle = driver.getTitle();
    	AssertJUnit.assertEquals(driver.findElement(By.partialLinkText("Account settings")).getAccessibleName(),"Account settings");
    	softAssert.assertAll();
    	logger.log(Status.INFO, "Verify that you signed in successfully with eBay");
    }
    
	@Test(priority = 3)
	public void searchItem(){
		//7. Search for any item (Ex: Smartwatch)
	    driver.findElement(By.name("_nkw")).sendKeys("power inverter 12v dc to 220v ac");
	    driver.findElement(By.id("gh-btn")).click();
	    logger.log(Status.PASS, "Search for any item");
	    
	    //8. Navigate into the item by select the first item from the search result
	    driver.findElement(By.xpath("//*[@id=\"srp-river-results\"]/ul/li[1]/div/div[2]/a/h3")).click();
	    logger.log(Status.PASS, "Navigate into the item by select the first item from the search result");
	}
	
	@Test(priority = 4)
	public void addIeam(){
	    /*9. Select any of the option and add the item to your cart 
	     *(Once you click on Add to cart button you will be navigate into your Shopping Cart)
	     */
	    String mainWindow = driver.getWindowHandle();
	    Set<String> handles = driver.getWindowHandles();  
	    for (String handle : handles) {
	        if (!handle.equals(mainWindow)) {
	              driver.switchTo().window(handle);
	              break;
	        }
	    }
	    driver.findElement(By.id("qtyTextBox")).clear();
	    driver.findElement(By.id("qtyTextBox")).sendKeys("2");
	    driver.findElement(By.id("isCartBtn_btn")).click();
	    logger.log(Status.PASS, "Select any of the option and navigate into your Shopping Cart");
	    
	    //10. Verify the added item in your Shopping Cart
	    SoftAssert softAssert2 = new SoftAssert();
	    String getActualTitle = driver.getTitle();
    	AssertJUnit.assertEquals(getActualTitle,"eBay shopping cart");
    	//softAssert2.assertAll();
    	//SoftAssert softAssert3 = new SoftAssert();
    				//String getActualTitle = driver.getTitle();
    	AssertJUnit.assertEquals(driver.findElement(By.partialLinkText("3000W Car Power Inverter DC 12V to AC 220V Pure Sine Wave Dual USB LCD Display")).getAccessibleName(),"3000W Car Power Inverter DC 12V to AC 220V Pure Sine Wave Dual USB LCD Display");
    	softAssert2.assertAll();
    	logger.log(Status.INFO, "Verify the added item in your Shopping Cart");
    	
    	driver.findElement(By.xpath("//button[contains(@data-test-id,'cart-remove-item')]")).click();
    	logger.log(Status.PASS, "Remove the added item from the Shopping Cart");	
    }
     
    @AfterSuite
    public void cleanUp(){
        //driver.quit();
        extent.flush();
    }

}
