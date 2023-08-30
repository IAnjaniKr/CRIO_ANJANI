package qtriptest.tests;
//import qtriptest.DP1;
import qtriptest.DP1;
//import qtriptest.DP1;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.utility.RandomEmailGenerator;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;
import com.beust.jcommander.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_01 {
    //DP1 excelHandler = new DP111("app/src/test/resources/DatasetsforQTrip.xlsx");
    static RemoteWebDriver driver;
    RandomEmailGenerator randomEmailGenerator = new RandomEmailGenerator(); 

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s |  %s |  %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true, enabled = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"),capabilities);
        logStatus("driver", "Initializing driver", "Success");
    }
    @Test(enabled=false)
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
    @Test(enabled=false)
    public void verifyAdventureFlow_negative_test2(){
        Assertion assertion = new Assertion();
        try{
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHomePage();
            //AdventurePage adventurePage = new AdventurePage(driver);
            assertion.assertTrue(driver.getCurrentUrl().equalsIgnoreCase("https://qtripdynamic-qa-frontend.vercel.app/"));
            //Thread.sleep(3000);
            homePage.enterCityNameToSearch("Pune");
           // Thread.sleep(3000);
            if(homePage.getAutoSuggestionValue().equalsIgnoreCase("Pune")){
                System.out.print("Pune is available");
            }else{
                System.out.print(homePage.getAutoSuggestionValue());
            }
    }catch(Exception e){
        logStatus("Page test", "navigation to adventure page", "failed");
			e.printStackTrace();
       }
    }

    @Test(dataProvider="data-provider", dataProviderClass = DP1.class)
   // @Parameters({"UserName", "Password"})
    // @Test
    public void TestCase01(String UserName, String Password){
        //System.out.println("--->>" + number);
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

    @Test(enabled=false)
    public void verifyAdventureFlow_test(){
        Assertion assertion = new Assertion();
        try{
        AdventurePage adventurePage = new AdventurePage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        homePage.enterCityNameToSearch("Goa");
        if(homePage.getAutoSuggestionValue()=="Goa"){
            System.out.print("Goa is available");
            homePage.clickOnSearchedCity();
        }else{
            System.out.println("Autosuggested City Name: "+homePage.getAutoSuggestionValue());
        }
        homePage.clickOnSearchedCity();
        adventurePage.checkTheNavigationOfSelectedCity("Goa");
        adventurePage.selectFilter(adventurePage.durationFilter, "2-6 Hours");
        
        Thread.sleep(3000);
        System.out.println("Check Tag: "+adventurePage.checkTag());
        int sizeWithFilter = adventurePage.getActivityCards().size();
        System.out.println("sizeWithFilter: "+sizeWithFilter);
        Thread.sleep(3000);
        adventurePage.clearFilter(adventurePage.clearDuration);
        adventurePage.selectFilter(adventurePage.categoryFilter,"Cycling Routes");
        Thread.sleep(3000);
        System.out.println("Check Tag: "+adventurePage.checkTag());
        sizeWithFilter = adventurePage.getActivityCards().size();
        System.out.println("sizeWithFilter: "+sizeWithFilter);
        Thread.sleep(3000);
        adventurePage.clearFilter(adventurePage.clearCategory);
        
        int sizeWithoutFilter = adventurePage.getActivityCards().size();
        System.out.println("sizeWithoutFilter: "+sizeWithoutFilter);
        assertion.assertNotEquals(sizeWithFilter,sizeWithoutFilter);
    }catch(Exception e){
        logStatus("Page test", "navigation to adventure page", "failed");
			e.printStackTrace();
       }


    }

    // Quit webdriver after Unit Tests
	@AfterTest(enabled = true)
	public void quitDriver() throws MalformedURLException {
		driver.close();
		driver.quit();
		logStatus("driver", "Quitting driver", "Success");
	}

    // //@DataProvider(name = "data-provider")
    // public Object[][] provideTestData() throws IOException {
    //     return DP1.dataProviderMethod();
    // }
}
