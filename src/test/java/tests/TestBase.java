package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.Browser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utilities.Browser.extent;
import static utilities.Browser.reportPath;

public abstract class TestBase {

    private static final String SCREENSHOT_FOLDER = "reports/screenshots/";
    private static final String SCREENSHOT_FORMAT = ".png";
    private static ExtentTest extentTest;

    @BeforeClass
    public void init() {
        Browser.Initialize();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (Browser.getDriver() != null) {
            Browser.getDriver().quit();
        }

        extent.flush();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
        Date date = new Date();
        String filePathdate = dateFormat.format(date).toString();
        String actualReportPath = reportPath + "index.html";
        new File(actualReportPath).renameTo(new File(
                reportPath + "Automation_" + filePathdate + ".html"));
    }

    @BeforeMethod
    public void extentTestSetup(Method method) {
        String className = this.getClass().getSimpleName();
        extentTest = extent.createTest(className + "-" + method.getName());
    }

    @AfterMethod
    public void extentTestResultSetup(ITestResult result) throws IOException {
        String methodName = result.getName();
        extentTest.createNode(methodName);

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(methodName + " – Test Case Failed", ExtentColor.RED));
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " – Test Case Failed", ExtentColor.RED));
            // path for screen shots
            final String path = SCREENSHOT_FOLDER + result.getName() + SCREENSHOT_FORMAT;
            // generate screenshot files
            genScreenshot(path);
            // TODO: add screenshot in the report for fail case
            extentTest.fail(methodName + ": Test Failed with Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(path).build());

        }

        if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " – Test Case Skipped", ExtentColor.ORANGE));
            extentTest.skip(methodName + ": Test Skipped");
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
            extentTest.pass(methodName + ": Test Passed");
        }
    }

    public void genScreenshot(String path) {
        try {
            WebDriver returned = new Augmenter().augment(Browser.getDriver());
            if (returned != null) {
                File f = ((TakesScreenshot) returned).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(f, new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ScreenshotException se) {
            se.printStackTrace();
        }
    }
}
