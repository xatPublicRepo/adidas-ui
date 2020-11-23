package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import webdriver.WebDriverFactory;

import java.util.concurrent.TimeUnit;

/*
 * Bean representing a browser. It contains name, version and platform fields.
 */
public class Browser {

    private static String baseUrl = PropertyLoader.loadProperty("site.url");
    private static String BrowserName = PropertyLoader.loadProperty("browser.name");
    private static String BrowserVersion = PropertyLoader.loadProperty("browser.version");
    private static WebDriver webDriver;
    public static ExtentSparkReporter spark;
    public static ExtentReports extent;
    public static String reportPath = System.getProperty("user.dir") + "/reports/";

    public static void Initialize() {
        webDriver = WebDriverFactory.getInstance(BrowserName);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        goTo("");

        //reporting: extent spark
        spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();

        //configure report
        spark.config().setDocumentTitle("UI automation");
        spark.config().setReportName("Execution Report");
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);
        extent.setSystemInfo("Application name", "Extent report");
        extent.setSystemInfo("Platform", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "QA");

        extent.attachReporter(spark);

    }

    public static String getTitle() {
        return webDriver.getTitle();
    }

    public static WebDriver getDriver() {
        return webDriver;
    }

    public static void goTo(String url) {
        webDriver.get(baseUrl + url);
        webDriver.manage().window().maximize();
    }

    public static void close() {
        webDriver.close();
    }
}
