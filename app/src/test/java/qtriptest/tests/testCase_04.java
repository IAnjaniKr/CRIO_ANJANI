package qtriptest.tests;

import qtriptest.ReportSingleton;
import qtriptest.TakeScreenshots;
import qtriptest.DP4;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utility.RandomEmailGenerator;
import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.By;
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


public class testCase_04 {

    private static ExtentReports extent;
    private static ExtentTest extentTest;
    
    private static WebDriverWait wait;

    static RemoteWebDriver driver;
    RandomEmailGenerator randomEmailGenerator = new RandomEmailGenerator(); 

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
        extentTest = ReportSingleton.createTest("testCase_04", "Test Case 04 Description");
        wait = new WebDriverWait(driver, 120);
    }

    @Test(enabled=true,dataProvider="data-provider", dataProviderClass = DP4.class,groups = "Reliability Flow",description = "This test case is for the reliability flow",priority = 4)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3){
        extentTest = extent.startTest("TestCase04: This test case is for the reliability flow");
        System.out.println("--->>" + NewUserName);
        System.out.println("--->>" + Password);
        System.out.println("--->>" + dataset1);
        System.out.println("--->>" + dataset2);
        System.out.println("--->>" + dataset3);
        
        Assertion assertion = new Assertion();
        String randomEmail = randomEmailGenerator.generateRandomEmail()+NewUserName;
        try{
                AdventurePage adventurePage = new AdventurePage(driver);   
                HomePage homePage = new HomePage(driver);
                AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
                HistoryPage historyPage = new HistoryPage(driver);
                RegisterPage registerPage = new RegisterPage(driver);  
                registerPage.navigateToRegisterPage();
                registerPage.verifyRegisterPage();
                registerPage.performRegister(randomEmail,Password);
                LoginPage loginPage = new LoginPage(driver);
                loginPage.performLogin(randomEmail,Password);
                homePage.verifyUserLoggedIn(randomEmail);
                
                 
                String [][] result = adventureDetailsPage.splitDataSets(new String[]{dataset1,dataset2,dataset3});
                //Search city 
                for(int i = 0;i<=2;i++){
                homePage.navigateToHomePage();    
                homePage.enterCityNameToSearch(result[i][0]);
                WebElement element = homePage.getAutoSuggestionValue(result[i][0]);
                Thread.sleep(6000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#results"))); 
                if(homePage.getAutoSuggestionValue(result[i][0]).getText().equalsIgnoreCase(result[i][0])){
                   System.out.println(result[i][0]+" is available");
                   wait.until(ExpectedConditions.elementToBeClickable(element));
                   homePage.clickOnSearchedCity();
                  }else{
                      System.out.println("Autosuggested City Name: "+homePage.getAutoSuggestionValue(result[i][0]).getText());
                    }
                
                adventurePage.checkTheNavigationOfSelectedCity(result[i][0]);
                driver.navigate().refresh();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/\\?city=.*"));
                adventurePage.searchAdventureMethod(adventurePage.searchAdventure,result[i][1]);
                Thread.sleep(2000);
                adventurePage.clickActivityCard();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/detail/\\?adventure=.*"));
                Thread.sleep(6000);
                driver.navigate().refresh();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/detail/\\?adventure=.*")); 
                Thread.sleep(6000);
                adventureDetailsPage.waitForReservationForm();
                
                adventureDetailsPage.createReservation(result[i][2], result[i][3], result[i][4]);
                System.out.println("Current URL after reservation: " + driver.getCurrentUrl());
                System.out.println(adventureDetailsPage.getReservationSuccess().getText());
                }
                String reservationsLink = adventureDetailsPage.getTopNavReservations().getAttribute("href");
               System.out.println("Navigating to history page...");
               adventureDetailsPage.getTopNavReservations().click();
               System.out.println("Navigated to history page. Current URL: " + driver.getCurrentUrl());
               String reservationsLinkOnNavigation = driver.getCurrentUrl();
               assertion.assertTrue(reservationsLinkOnNavigation.contains(reservationsLink));
               wait.until(ExpectedConditions.urlMatches(".*\\/pages\\/adventures\\/reservations\\/index\\.html"));
                List<WebElement> transactionIDs = historyPage.getBookingTransactionIDs();
                wait.until(ExpectedConditions.visibilityOfAllElements(transactionIDs));
                for(WebElement id : transactionIDs){
                    System.out.println(id.getText());
                }
                extentTest.log(LogStatus.PASS,"Test Passed: TestCase04");             
            }catch(Exception e){
                logStatus("Page test", "navigation to registration page", "failed");
                extentTest.log(LogStatus.FAIL,"Test Failed: TestCase04");
                extentTest.addScreenCapture(TakeScreenshots.capture(driver) + "Test Failed: TestCase04");
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
