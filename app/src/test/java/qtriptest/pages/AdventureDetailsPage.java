package qtriptest.pages;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {

    private RemoteWebDriver driver;

    

    public AdventureDetailsPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

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

    public String getFormattedDate(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = tomorrow.format(formatter);
        return formattedDate;
    }
    
    public void createReservation(){
        this.getNameInput().sendKeys("");
        this.getDateInput().sendKeys(this.getFormattedDate());
        this.getPersonInput().sendKeys("");
        this.getReserveButton().click();
    }
}



