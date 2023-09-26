package qtriptest;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    // Private static instance of the WebDriver
    private static RemoteWebDriver driver;

    // Private constructor to prevent external instantiation
    private DriverSingleton() {
    }

    // Public method to get or create the WebDriver instance
    public static RemoteWebDriver getDriver() {
        if (driver == null) {
            // Set up DesiredCapabilities for the RemoteWebDriver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(BrowserType.CHROME);

            try {
                // Replace the URL with the URL of your remote WebDriver server
                URL remoteUrl = new URL("http://localhost:8082/wd/hub");
                driver = new RemoteWebDriver(remoteUrl, capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    // Public method to quit the WebDriver instance
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
