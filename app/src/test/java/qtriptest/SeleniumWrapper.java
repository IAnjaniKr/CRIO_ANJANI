package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {

    

    public static boolean click(WebElement elementToClick, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 80);
            boolean elementVisible = wait.until(ExpectedConditions.visibilityOf(elementToClick)).isDisplayed();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToClick);
            if (elementVisible) {
                elementToClick.click();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean sendKeys(WebElement inputBox, String keysToSend){
       boolean isInputBoxEnabled = inputBox.isEnabled();
       if(isInputBoxEnabled){
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
       }
        return isInputBoxEnabled;
    }
    
    

    public static boolean navigate(WebDriver driver, String url){
      if(!driver.getCurrentUrl().equals(url)){
        driver.navigate().to(url);
      }
      return driver.getCurrentUrl().equals(url);
    }

    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
        WebElement element = null;
        for (int i = 0; i <= retryCount; i++) {
            try {
                element = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(by));
                break;
            } catch (org.openqa.selenium.TimeoutException e) {
                if (i < retryCount) {
                    System.out.println("Element not found. Retrying...");
                } else {
                    System.out.println("Element not found after retries.");
                }
            }
        }
        return element;
    }
}
