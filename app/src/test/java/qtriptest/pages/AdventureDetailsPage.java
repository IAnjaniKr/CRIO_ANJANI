package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventureDetailsPage {

    private RemoteWebDriver driver;

    

    public AdventureDetailsPage(RemoteWebDriver driver){
        this.driver = driver;
        driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 120), this);
    }

    @FindBy(xpath="//a[contains(text(),'Reservations')]")
    private WebElement topNavReservations;

    @FindBy(css="#reservation-panel-available")
    private WebElement reservationForm;

    @FindBy(id = "adventure-name")
    private WebElement adventureName;

    @FindBy(css = "input[name='name']")
    private WebElement nameInput;

    @FindBy(css = "input[name='date']")
    private WebElement dateInput;

    @FindBy(css = "input[name='person']")
    private WebElement personInput;

    @FindBy(id = "reservation-cost")
    private WebElement totalCost;

    @FindBy(className = "reserve-button")
    private WebElement reserveButton;

    @FindBy(id="reserved-banner")
    private WebElement reservationSuccess;

    @FindBy(xpath = "//a/strong")
    private WebElement reservationLink;

    public WebElement getReservationSuccess(){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOf(reservationSuccess));
        return reservationSuccess;
    }

    public WebElement getAdventureName() {
        return adventureName;
    }

    public WebElement getNameInput() {
        return nameInput;
    }

    public WebElement getDateInput() {
        return dateInput;
    }

    public WebElement getPersonInput() {
        return personInput;
    }

    public WebElement getTotalCost() {
        return totalCost;
    }

    public WebElement getReserveButton() {
        return reserveButton;
    }

    public WebElement getTopNavReservations(){
        return topNavReservations;
    }

    public void waitForReservationForm(){
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#reservation-panel-available")));
        wait.until(ExpectedConditions.visibilityOf(reservationForm));
        wait.until(ExpectedConditions.elementToBeClickable(reservationForm));
    }


    public void selectDate(String inputDate) throws InterruptedException {
        String[] dateComponents = inputDate.split("-");
        String day = dateComponents[0];
        String month = dateComponents[1];
        String year = dateComponents[2];
       SeleniumWrapper.sendKeys(dateInput,day+month+year);
    }
    

    public void createReservation(String Name, String Date, String personCount) throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver,90);
        wait.until(ExpectedConditions.elementToBeClickable(this.getNameInput()));
        SeleniumWrapper.sendKeys(this.getNameInput(),Name);
        this.selectDate(Date);
        wait.until(ExpectedConditions.elementToBeClickable(this.getPersonInput()));
        SeleniumWrapper.sendKeys(this.getPersonInput(),personCount);
        wait.until(ExpectedConditions.elementToBeClickable(this.getReserveButton()));
        SeleniumWrapper.click(this.getReserveButton(),driver);
    }

    
    public WebElement getreservationLink(){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOf(reservationLink));
        return reservationLink;
    }

    public String[][] splitDataSets(String[] inputDataSet) {
        String[][] result = new String[inputDataSet.length][5];
        for (int i = 0; i < inputDataSet.length; i++) {
            result[i] = inputDataSet[i].split(";");
        }
        return result;
    }   
    
}



