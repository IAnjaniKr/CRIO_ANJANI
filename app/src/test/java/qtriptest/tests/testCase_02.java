package qtriptest.tests;

import qtriptest.ReportSingleton;
import qtriptest.TakeScreenshots;
import qtriptest.DP2;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class testCase_02 {
    private static ExtentReports extent;
    private static ExtentTest extentTest;
     
    static RemoteWebDriver driver;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s |  %s |  %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true, enabled=true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        driver = DriverSingleton.getDriver();
        logStatus("driver", "Initializing driver", "Success");
        extent = ReportSingleton.getInstance();
        extentTest = ReportSingleton.createTest("testCase_02", "Test Case 02 Description");
    }

    @Test(enabled=true)
    public void verifyAdventureFlow_negative_test2(){
        extentTest = extent.startTest("Test2: verifyAdventureFlow_negative_test2");
        Assertion assertion = new Assertion();
        try{
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage();    
    }catch(Exception e){
        logStatus("Page test", "navigation to adventure page", "failed");
			e.printStackTrace();
       }
    }

    @Test(enabled=true,dataProvider="data-provider", dataProviderClass = DP2.class,groups = "Search and Filter flow",description = "This test case is for the search and filter flow",priority = 2)
    public void TestCase02(String CityName, String categoryFilter, String durationFilter,String expectedFiltered, String expectedUnfiltered){
        extentTest = extent.startTest("TestCase02: This test case is for the search and filter flow");
        Assertion assertion = new Assertion();
        try{
        WebDriverWait wait = new WebDriverWait(driver, 120);    
        AdventurePage adventurePage = new AdventurePage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        assertion.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://qtripdynamic-qa-frontend.vercel.app/")); 
        homePage.enterCityNameToSearch("Pune");
            if(homePage.getAutoSuggestionValue("Pune").getText().equalsIgnoreCase("Pune")){
                System.out.println("Pune is available");
            }else{
                System.out.println(homePage.getAutoSuggestionValue("Pune").getText());
            }
        homePage.enterCityNameToSearch(CityName);
        WebElement element = homePage.getAutoSuggestionValue(CityName);
        wait.until(ExpectedConditions.visibilityOf(element));
        
        if(homePage.getAutoSuggestionValue(CityName).getText().equalsIgnoreCase(CityName)){
            System.out.println(CityName+" is available");
            wait.until(ExpectedConditions.elementToBeClickable(element));
            homePage.clickOnSearchedCity();
        }else{
            System.out.println("Autosuggested City Name: "+homePage.getAutoSuggestionValue(CityName).getText());
        }
        adventurePage.checkTheNavigationOfSelectedCity(CityName);
        //Apply Duration Filter
        adventurePage.selectFilter(adventurePage.durationFilter, durationFilter);
        Thread.sleep(3000);
        int sizeWithDurationFilter = adventurePage.getActivityCards().size();
        System.out.println("Size With Duration Filter: "+sizeWithDurationFilter);
        Thread.sleep(3000);
        //Apply Category Filter
        adventurePage.selectFilter(adventurePage.categoryFilter,categoryFilter);
        Thread.sleep(3000);
        System.out.println("Check Tag: "+adventurePage.checkTag());
        expectedFiltered = Integer.toString(adventurePage.getActivityCards().size());
        System.out.println("Size With Category Filter: "+expectedFiltered);
        Thread.sleep(3000);
        //Clear Duration and Category filters
        adventurePage.clearFilter(adventurePage.clearDuration);
        adventurePage.clearFilter(adventurePage.clearCategory);
        expectedUnfiltered = Integer.toString(adventurePage.getActivityCards().size());
        System.out.println("Size Without Filter: "+expectedUnfiltered);
        assertion.assertNotEquals(expectedFiltered,expectedUnfiltered);
        extentTest.log(LogStatus.PASS,"Test Passed: TestCase02");
    }catch(Exception e){
        logStatus("Page test", "navigation to adventure page", "failed");
        extentTest.log(LogStatus.FAIL,"Test Failed: TestCase02");
        extentTest.addScreenCapture(TakeScreenshots.capture(driver) + "Test Failed: TestCase02");
			e.printStackTrace();
       }
    }

    // Quit webdriver after Unit Tests
	@AfterTest(enabled=true)
	public void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
		logStatus("driver", "Quitting driver", "Success");
        extent.flush();
	}

}
