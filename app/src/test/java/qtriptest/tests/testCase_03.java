package qtriptest.tests;
import qtriptest.DP3;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utility.RandomEmailGenerator;
import java.net.MalformedURLException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;


public class testCase_03 {

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
    }

    @Test(enabled=true,dataProvider="data-provider", dataProviderClass = DP3.class,groups = "Booking and Cancellation Flow",description = "This test case is for the booking and cancellation flow",priority = 3)
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName,String Date, String Count){
        System.out.println("--->>" + NewUserName);
        System.out.println("--->>" + Password);
        System.out.println("--->>" + SearchCity);
        System.out.println("--->>" + AdventureName);
        System.out.println("--->>" + GuestName);
        System.out.println("--->>" + Date);
        System.out.println("--->>" + Count);
        Assertion assertion = new Assertion();
        String randomEmail = randomEmailGenerator.generateRandomEmail()+NewUserName;
        try{
                AdventurePage adventurePage = new AdventurePage(driver);   
                HomePage homePage = new HomePage(driver);
                AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
                HistoryPage historyPage = new HistoryPage(driver);
                homePage.navigateToHomePage();
                RegisterPage registerPage = new RegisterPage(driver);
                
                registerPage.navigateToRegisterPage();
                registerPage.verifyRegisterPage();
                registerPage.performRegister(randomEmail,Password);
                LoginPage loginPage = new LoginPage(driver);
                loginPage.performLogin(randomEmail,Password);
                homePage.verifyUserLoggedIn(randomEmail);
                
                //Search city 
                homePage.enterCityNameToSearch(SearchCity);
                if(homePage.getAutoSuggestionValue()==SearchCity){
                   System.out.println(SearchCity+" is available");
                   //homePage.clickOnSearchedCity();
                  }else{
                      System.out.println("Autosuggested City Name: "+homePage.getAutoSuggestionValue());
                    }
              homePage.clickOnSearchedCity();
              adventurePage.checkTheNavigationOfSelectedCity(SearchCity);
                WebDriverWait wait = new WebDriverWait(driver, 60);
                driver.navigate().refresh();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/\\?city=.*"));
                adventurePage.searchAdventure.sendKeys(AdventureName);
                adventurePage.activityCards().click();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/detail/\\?adventure=.*"));
                adventureDetailsPage.waitForReservationForm();
                adventureDetailsPage.createReservation(GuestName, Date, Count);
                System.out.println(adventureDetailsPage.getReservationSuccess().getText());
                adventureDetailsPage.getreservationLink().click();
                WebElement transactionID = historyPage.getBookingTransactionID();
                wait.until(ExpectedConditions.visibilityOf(transactionID));
                System.out.println(historyPage.getBookingTransactionID().getText());
                Thread.sleep(3000);
                historyPage.getBookingCancel().click();
                Thread.sleep(3000);
                System.out.println(historyPage.getnoReservationBanner().getText());
                driver.navigate().refresh();
                Thread.sleep(3000);              
            }catch(Exception e){
                logStatus("Page test", "navigation to registration page", "failed");
                    e.printStackTrace();
            }
            }

    // Quit webdriver after Unit Tests
	@AfterTest(enabled=true)
	public void quitDriver() throws MalformedURLException {
		DriverSingleton.quitDriver();
		logStatus("driver", "Quitting driver", "Success");
	}
}
