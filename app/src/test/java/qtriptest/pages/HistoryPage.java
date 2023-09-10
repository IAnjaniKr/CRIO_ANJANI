
package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HistoryPage {

    private RemoteWebDriver driver;

    public HistoryPage(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
    }

    String reservationsHistoryUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/";

    public void navigateToHistory(){
        driver.navigate().to(reservationsHistoryUrl);
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.urlToBe(reservationsHistoryUrl));
    }
    
    @FindBy(xpath="//tbody[@id='reservation-table']//th")
    private WebElement bookingTransactionID;

    @FindBy(xpath="//tbody[@id='reservation-table']//th")
    private List<WebElement> bookingTransactionIDs;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[1]")
    private WebElement bookingName;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[2]")
    private WebElement bookingAdventure;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[3]")
    private WebElement bookingPersonCount;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[4]")
    private WebElement bookingDate;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[5]")
    private WebElement bookingPrice;
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[6]")
    private WebElement bookingTime;	
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[7]")
    private WebElement bookingAction;	
    
    @FindBy(xpath="//tbody[@id='reservation-table']//td[8]")
    private WebElement bookingCancel;

    @FindBy(css="#no-reservation-banner")
    private WebElement noReservationBanner;

    public WebElement getnoReservationBanner(){
        return noReservationBanner;
    }

    public WebElement getBookingTransactionID(){
        return bookingTransactionID;
    }

    public WebElement getBookingName(){
        return bookingName;
    }

    public WebElement getBookingAdventure(){
        return bookingAdventure;
    }

    public WebElement getBookingPersonCount(){
        return bookingPersonCount;
    }

    public WebElement getBookingDate(){
        return bookingDate;
    }

    public WebElement getBookingPrice(){
        return bookingPrice;
    }

    public WebElement getBookingTime(){
        return bookingTime;
    }

    public WebElement getBookingAction(){
        return bookingAction;
    }

    public WebElement getBookingCancel(){
        return bookingCancel;
    }

    public List<WebElement> getBookingTransactionIDs() {
        return bookingTransactionIDs;
    }



}