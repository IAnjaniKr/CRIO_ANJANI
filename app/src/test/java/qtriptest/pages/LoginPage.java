package qtriptest.pages;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    public String loginPageUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";
    private RemoteWebDriver driver;

    public LoginPage(RemoteWebDriver driver){
        this.driver = driver;
        driver.manage().window().maximize();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
    }

    @FindBy(xpath="//input[@id='floatingInput']")
    public WebElement emailInput;

    @FindBy(css="#floatingPassword")
    public WebElement passwordInput;

    @FindBy(xpath="//button[normalize-space()='Login to QTrip']")
    public WebElement loginButton;


    public void navigateToLoginPage() {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(loginPageUrl);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean performLogin(String username, String password) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        System.out.println("Current URL after login attempt: " + driver.getCurrentUrl()); // Add this line
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app"));
            return true; // Successful login
        } catch (Exception e) {
            return false; // Login failed
        }
    }
    
    

}
