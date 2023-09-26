package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private RemoteWebDriver driver;
    public String registerUrl="https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    public RegisterPage (RemoteWebDriver driver){
        this.driver = driver;
        driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 120), this);
    }

    @FindBy(css="#floatingInput")
    public WebElement emailInput;

    @FindBy(id="floatingPassword")
    public WebElement passwordInput;

    @FindBy(css="[name='confirmpassword']")
    public WebElement confirmPassword;

    @FindBy(xpath="//button[contains(.,'Register Now')]")
    public WebElement registerNowButton;

    @FindBy(xpath="//button[contains(.,'Logout')]")
    public WebElement logoutButton;

    @FindBy(css="h2.formtitle")
    public WebElement regiterTitle;

    public void navigateToRegisterPage(){
        SeleniumWrapper.navigate(driver,registerUrl);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains("/pages/register/"));
    }

    public boolean verifyRegisterPage(){
        String title = regiterTitle.getText();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
        wait.until(ExpectedConditions.urlContains("/pages/register/"));
        if(title.equals("Register")){
            return true;
        }
        }catch(Exception e){
            return false;
        }
        return false;
    }

    public boolean performRegister(String randomEmail, String password){
        SeleniumWrapper.sendKeys(emailInput,randomEmail);
        SeleniumWrapper.sendKeys(passwordInput,password);
        SeleniumWrapper.sendKeys(confirmPassword,password);
        SeleniumWrapper.click(registerNowButton,driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));
            return true; // registration login
        } catch (Exception e) {
            return false; // registration failed
        }
    }  

}
