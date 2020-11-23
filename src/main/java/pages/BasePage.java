package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Browser;

public class BasePage {

    private final By h1tag = By.name("h1");

    public String getH1() {
        return Browser.getDriver().findElement(h1tag).getText();
    }

    public void waitAndClick(By element) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        retryingFindClick(element);
    }

    public void checkAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Browser.getDriver().switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //to avoid stale element exception
    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                Browser.getDriver().findElement(by).click();
                result = true;
                break;
            } catch(Exception e) {
            }
            attempts++;
        }
        return result;
    }

    public void waitAndEnterText(By element, String text) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(text);
    }

    public void waitForSeconds(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String waitAndGetText(By element) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 5);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
    }

}
