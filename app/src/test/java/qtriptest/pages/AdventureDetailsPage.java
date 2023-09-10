package qtriptest.pages;


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
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
    }

    @FindBy(id="reservation-panel-available")
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
        WebDriverWait wait = new WebDriverWait(driver, 60);
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

    public void waitForReservationForm(){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(reservationForm));
    }


    public void selectDate(String inputDate) throws InterruptedException {
        String[] dateComponents = inputDate.split("-");
        String day = dateComponents[0];
        String month = dateComponents[1];
        String year = dateComponents[2];
        dateInput.clear();
        dateInput.sendKeys(day);
        Thread.sleep(1000);
        dateInput.sendKeys(month);
        Thread.sleep(1000);
        dateInput.sendKeys(year);
        Thread.sleep(1000);
    }
    

    public void createReservation(String Name, String Date, String personCount) throws InterruptedException{
        this.getNameInput().clear();
        this.getNameInput().sendKeys(Name);
        this.selectDate(Date);
        this.getPersonInput().clear();
        this.getPersonInput().sendKeys(personCount);
        this.getReserveButton().click();
    }

    
    public WebElement getreservationLink(){
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



