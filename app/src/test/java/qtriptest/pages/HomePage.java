package qtriptest.pages;
import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class HomePage {


    private RemoteWebDriver driver;
    

    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 120), this);
    }
    public String homePageUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    public String registerUrl="https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    public void navigateToHomePage(){
        WebDriverWait wait = new WebDriverWait(driver, 120);
        SeleniumWrapper.navigate(driver, homePageUrl);
        wait.until(ExpectedConditions.urlToBe(homePageUrl));
    }

    @FindBy(css=".register")
    public WebElement registerButton;
    
    @FindBy(xpath = "//a[normalize-space()='Login Here']")
    public WebElement loginLink;

    @FindBy(xpath="//a[normalize-space()='Reservations']")
    public WebElement reservationsLink;


    @FindBy(id="autocomplete")
    public WebElement searchCityInput;
    
    @FindBy(css="#results")
    public WebElement searchResults;

    @FindBy(css="li.nav-item.auth div")
    public WebElement logoutButton;


    public void clickOnRegisterButton(){
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        SeleniumWrapper.click(registerButton,driver);
        wait.until(ExpectedConditions.urlToBe(registerUrl)); 
    }


    public void enterCityNameToSearch(String cityName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOf(searchCityInput));
        wait.until(ExpectedConditions.elementToBeClickable(searchCityInput));
        SeleniumWrapper.sendKeys(searchCityInput,cityName);
    }
    
    // public WebElement getAutoSuggestionValue(){
    //     WebDriverWait wait = new WebDriverWait(driver, 220);
    //     wait.until(ExpectedConditions.visibilityOf(searchResults));
    //     wait.until(ExpectedConditions.elementToBeClickable(searchResults));
    //     return searchResults;
    // }

    public WebElement getAutoSuggestionValue(String cityName) throws InterruptedException {
        // Call enterCityNameToSearch to input the city name
        this.enterCityNameToSearch(cityName);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // Start listening for network requests
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.addEventListener('fetch', function(event) { console.log(event.detail.responseUrl); });");
        // Wait for a network request to be made to the city suggestion API
        Boolean isChildrenLengthGreaterThanZero = (Boolean) wait.until(new ExpectedCondition<Object>() {
            @Override
            public Object apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.querySelector('#results').children.length > 0");
            }
        });
        // Check if the search results have appeared
        if (isChildrenLengthGreaterThanZero) {
            System.out.println("Search results appeared");
        } else {
            System.out.println("Search results did not appear");
        }
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        wait.until(ExpectedConditions.elementToBeClickable(searchResults));
        return searchResults;
    }
    
    

    public void clickOnSearchedCity(){ 
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        wait.until(ExpectedConditions.elementToBeClickable(searchResults));
        if(searchResults.isDisplayed()){
            SeleniumWrapper.click(searchResults,driver);
        }
    }
    
    public void verifyUserLoggedIn(String randomEmail){
        WebDriverWait wait = new WebDriverWait(driver, 120);
       wait.until(ExpectedConditions.textToBePresentInElement(logoutButton, "Logout"));
       System.out.println(randomEmail+" is logged in and button text is "+logoutButton.getText());
    }
    
    public void clickOnLogoutButton(){
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.textToBePresentInElement(logoutButton, "Logout"));
        if(logoutButton!=null){
            SeleniumWrapper.click(logoutButton,driver);
        }
    }
    
     public void verifyUserLoggedOut(){
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.textToBePresentInElement(registerButton, "Register"));
        wait.until(ExpectedConditions.textToBePresentInElement(loginLink, "Login Here"));
     }

}
