package qtriptest.tests;
import qtriptest.DP1;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utility.RandomEmailGenerator;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_01 {
    
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
    @Test(enabled=true)
    public void verifyAdventureFlow_negative_test1(){
        Assertion assertion = new Assertion();
        try{
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage();
            //AdventurePage adventurePage = new AdventurePage(driver);
            assertion.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://qtripdynamic-qa-frontend.vercel.app/"));
        }catch(Exception e){
          logStatus("Page test", "navigation to adventure page", "failed");
			e.printStackTrace();
       }   
    }
    
    
    @Test(enabled=true,dataProvider="data-provider", dataProviderClass = DP1.class,groups = "Login Flow",description = "This test case is for the login flow",priority = 1)
    public void TestCase01(String UserName, String Password){
        System.out.println("--->>" + UserName);
        System.out.println("--->>" + Password);
        Assertion assertion = new Assertion();
        String randomEmail = randomEmailGenerator.generateRandomEmail()+UserName;
        try{
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegisterPage();
        registerPage.verifyRegisterPage();
        registerPage.performRegister(randomEmail,Password);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin(randomEmail,Password);
        homePage.verifyUserLoggedIn(randomEmail);
        homePage.clickOnLogoutButton();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.navigate().refresh();
        wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));
        homePage.verifyUserLoggedOut();
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
