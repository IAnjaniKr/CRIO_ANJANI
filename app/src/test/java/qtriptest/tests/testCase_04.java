package qtriptest.tests;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;


public class testCase_04 {

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

    @Test(enabled=true,dataProvider="data-provider", dataProviderClass = DP4.class,groups = "Reliability Flow",description = "This test case is for the reliability flow",priority = 4)
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3){
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
                homePage.navigateToHomePage();
                RegisterPage registerPage = new RegisterPage(driver);
                
                registerPage.navigateToRegisterPage();
                registerPage.verifyRegisterPage();
                registerPage.performRegister(randomEmail,Password);
                LoginPage loginPage = new LoginPage(driver);
                loginPage.performLogin(randomEmail,Password);
                homePage.verifyUserLoggedIn(randomEmail);
                WebDriverWait wait = new WebDriverWait(driver, 60);
                 
                String [][] result = adventureDetailsPage.splitDataSets(new String[]{dataset1,dataset2,dataset3});
                //Search city 
                for(int i = 0;i<=2;i++){
                homePage.navigateToHomePage();    
                homePage.enterCityNameToSearch(result[i][0]);
                if(homePage.getAutoSuggestionValue()==result[i][0]){
                   System.out.println(result[i][0]+" is available");
                  }else{
                      System.out.println("Autosuggested City Name: "+homePage.getAutoSuggestionValue());
                    }
                homePage.clickOnSearchedCity();
                adventurePage.checkTheNavigationOfSelectedCity(result[i][0]);
                driver.navigate().refresh();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/\\?city=.*"));
                adventurePage.searchAdventure.sendKeys(result[i][1]);
                adventurePage.activityCards().click();
                wait.until(ExpectedConditions.urlMatches(".*/pages/adventures/detail/\\?adventure=.*"));
                Thread.sleep(1000);
                adventureDetailsPage.waitForReservationForm();
                adventureDetailsPage.createReservation(result[i][2], result[i][3], result[i][4]);
                System.out.println(adventureDetailsPage.getReservationSuccess().getText());
                }
                historyPage.navigateToHistory();
                List<WebElement> transactionIDs = historyPage.getBookingTransactionIDs();
                wait.until(ExpectedConditions.visibilityOfAllElements(transactionIDs));
                for(WebElement id : transactionIDs){
                    System.out.println(id.getText());
                }            
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
