package qtriptest.pages;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;



public class HomePage {


    private RemoteWebDriver driver;

    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        driver.manage().window().maximize();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
    }
    public String homePageUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    public String registerUrl="https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    public void navigateToHomePage(){
        driver.get(homePageUrl);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlToBe(homePageUrl));
    }

    @FindBy(css=".register")
    public WebElement registerButton;
    
    @FindBy(xpath = "//a[normalize-space()='Login Here']")
    public WebElement loginLink;

    @FindBy(xpath="//a[normalize-space()='Reservations']")
    public WebElement reservationsLink;

    // @FindBy(xpath="//input[normalize-space(@placeholder)='Search a City']")
    // public WebElement searchCityInput;
    @FindBy(id="autocomplete")
    public WebElement searchCityInput;
    
    @FindBy(xpath = "//ul[@id='results']")
    //@FindBy(css="#results>h5")
    public WebElement searchResults;

    @FindBy(css="li.nav-item.auth div")
    public WebElement logoutButton;


    public void clickOnRegisterButton(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButton.click();
        wait.until(ExpectedConditions.urlToBe(registerUrl)); 
    }


    public void enterCityNameToSearch(String cityName) throws InterruptedException {
        searchCityInput.clear();
        searchCityInput.sendKeys(cityName);
        Thread.sleep(3000);
        searchCityInput.sendKeys(" ");
        Thread.sleep(3000);
    }
    
    public String getAutoSuggestionValue(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(searchResults));
        return searchResults.getText();
    }

    public void clickOnSearchedCity(){
        if(this.getAutoSuggestionValue()!=null){
            searchResults.click();
        }
    }
    
    public void verifyUserLoggedIn(String randomEmail){
       WebDriverWait wait = new WebDriverWait(driver, 60);
       wait.until(ExpectedConditions.textToBePresentInElement(logoutButton, "Logout"));
       System.out.println(randomEmail+" is logged in and button text is "+logoutButton.getText());
    }
    
    public void clickOnLogoutButton(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.textToBePresentInElement(logoutButton, "Logout"));
        if(logoutButton!=null){
            logoutButton.click();
        }
    }
    
     public void verifyUserLoggedOut(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.textToBePresentInElement(registerButton, "Register"));
        wait.until(ExpectedConditions.textToBePresentInElement(loginLink, "Login Here"));
       // return logoutButton.getText();
     }


}
