package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {

    private RemoteWebDriver driver;

    public AdventurePage(RemoteWebDriver driver){
      this.driver = driver;
      driver.manage().window().maximize();
      this.driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
      PageFactory.initElements(new AjaxElementLocatorFactory(driver, 120), this);
    }

    public String adventurePageUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures";
    
    

    @FindBy(id="duration-select")
    public WebElement durationFilter;


    @FindBy(id="category-select")
    public WebElement categoryFilter;

    @FindBy(xpath="(//div[normalize-space(text())='Clear'])[1]")
    public WebElement clearDuration;

    @FindBy(xpath="(//div[normalize-space(text())='Clear'])[2]")
    public WebElement clearCategory;

    @FindBy(className="activity-card")
    public List<WebElement> listOfActivityCards;

    @FindBy(className="activity-card")
    public WebElement activityCards;

    @FindBy(css=".category-filter div")
    public WebElement categoryTag;

    @FindBy(xpath="//*[text()='Duration']/following::p")
    public WebElement durationText;

    @FindBy(css="#search-adventures")
    public WebElement searchAdventure;

    int initialCountOfActivityCard;

    public void navigateToAdventurePage(){
        SeleniumWrapper.navigate(driver, adventurePageUrl);
    }

    public void searchAdventureMethod(WebElement inputBox,String adventure){
      SeleniumWrapper.sendKeys(inputBox, adventure);
    }

     public List<WebElement> getActivityCards(){
      WebDriverWait wait = new WebDriverWait(driver, 60);
      wait.until(ExpectedConditions.visibilityOfAllElements(listOfActivityCards));
      return listOfActivityCards;
    }
     
    public WebElement activityCards(){
      WebDriverWait wait = new WebDriverWait(driver, 60);
      wait.until(ExpectedConditions.visibilityOf(activityCards));
      return activityCards;
    }
    

    public boolean checkTheNavigationOfSelectedCity(String city) {
      initialCountOfActivityCard = listOfActivityCards.size();
      String[] urlParts = driver.getCurrentUrl().split(Pattern.quote("?city="));
      String currentUrl = driver.getCurrentUrl();
      System.out.println("Current URL: " + currentUrl);
      if (urlParts.length > 1) {
          return urlParts[1].equals(city);
      }
      return false;
  }
  

    public String checkTag(){ 
      WebDriverWait wait = new WebDriverWait(driver, 60); 
      wait.until(ExpectedConditions.visibilityOf(categoryTag));
      return categoryTag.getText();
    }

  

public void selectFilter(WebElement element, String optionText) {
  WebDriverWait wait = new WebDriverWait(driver, 60);
  wait.until(ExpectedConditions.elementToBeClickable(element));
  // Click on the dropdown to open it
  SeleniumWrapper.click(element, driver);
  By optionLocator = By.xpath("//option[text()='" + optionText + "']");
  wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
  // Select the option based on optionText
  Select select = new Select(element);
  select.selectByVisibleText(optionText);
}


  public void clearFilter(WebElement filterType){
      WebDriverWait wait = new WebDriverWait(driver,60);
      wait.until(ExpectedConditions.elementToBeClickable(filterType));
      SeleniumWrapper.click(filterType,driver);
    }

    public void clickActivityCard(){
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOf(activityCards));
      wait.until(ExpectedConditions.elementToBeClickable(activityCards));
      SeleniumWrapper.click(activityCards,driver);
    }
}