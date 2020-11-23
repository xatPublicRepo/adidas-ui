package webdriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/*
 * Factory to instantiate a WebDriver object. It returns an instance of the driver (local invocation).
 */
public class WebDriverFactory {

    /* Browsers constants */
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String EDGE = "edge";

    private WebDriverFactory(){}

    public static WebDriver getInstance(String browserName) {

        WebDriver webDriver = null;

        if (CHROME.equals(browserName)) {
            Class<? extends WebDriver> driverClass = ChromeDriver.class;
            ChromeDriverManager.getInstance(driverClass).setup();
            webDriver = new ChromeDriver();
        }
        else if (FIREFOX.equals(browserName)) {
            Class<? extends WebDriver> driverClass = FirefoxDriver.class;
            FirefoxDriverManager.getInstance(driverClass).setup();
            webDriver = new FirefoxDriver();

        }
        else if (INTERNET_EXPLORER.equals(browserName)) {
            Class<? extends WebDriver> driverClass = InternetExplorerDriver.class;
            InternetExplorerDriverManager.getInstance(driverClass).setup();
            webDriver = new InternetExplorerDriver();
        }
        else if (EDGE.equals(browserName)) {
            Class<? extends WebDriver> driverClass = EdgeDriver.class;
            EdgeDriverManager.getInstance(driverClass).setup();
            webDriver = new EdgeDriver();
        }
        else {
            throw new IllegalArgumentException("Unsupported browser!");
        }

        return webDriver;
    }
}
