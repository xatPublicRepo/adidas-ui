package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;


public class HomePage extends BasePage {
    private static Logger logger = LogManager.getLogger(HomePage.class);

    private final By phones = By.xpath("//a[contains(text(),'Phones')]");
    private final By lappy = By.xpath("//a[contains(text(),'Laptops')]");
    private final By monitor = By.xpath("//a[contains(text(),'Monitors')]");
    private final By sonyLappy = By.xpath("//a[contains(text(),'Sony vaio i5')]");
    private final By addToCart = By.xpath("//a[contains(text(),'Add to cart')]");
    private final By goHome = By.xpath("//*[@id='navbarExample']/ul/li[1]/a");
    private final By dellLappy = By.xpath("//a[contains(text(),'Dell i7 8gb')]");
    private final By goCart = By.xpath("//a[@id='cartur']");
    private final By deleteDell = By.xpath("//*[@id='tbodyid']/tr[1]/td[2]/following-sibling::td[2]/a");
    private final By placeOrder = By.xpath("//button[contains(text(),'Place Order')]");
    private final By formName = By.xpath("//*[@id='name']");
    private final By formCountry = By.xpath("//*[@id='country']");
    private final By formCity = By.xpath("//*[@id='city']");
    private final By formCard = By.xpath("//*[@id='card']");
    private final By formMonth = By.xpath("//*[@id='month']");
    private final By formYear = By.xpath("//*[@id='year']");
    private final By formPurchaseButton = By.xpath("//button[contains(text(),'Purchase')]");
    private final By thankyouOk = By.xpath("//button[contains(text(),'OK')]");
    private final By thankYouMessage = By.xpath("(//*[contains(text(),'Thank you for your purchase')]/following-sibling::p)[1]");


    public void navigateCategories() {
        waitAndClick(phones);
        waitAndClick(lappy);
        waitAndClick(monitor);
    }

    public void addSonyLaptop() {
        waitAndClick(lappy);
        waitAndClick(sonyLappy);
        waitAndClick(addToCart);
        checkAlert();
    }

    public void goBackHome() {
        waitAndClick(goHome);
    }

    public void addDellLaptop() {
        waitAndClick(lappy);
        waitAndClick(dellLappy);
        waitAndClick(addToCart);
        checkAlert();
    }

    public void goToCart() {
        waitAndClick(goCart);
    }

    public void deleteDell() {
        waitAndClick(deleteDell);
        waitForSeconds(5);
    }

    public void placingOrder() {
        waitAndClick(placeOrder);
    }

    public void fillWebFormAndPurchase() {
        waitAndEnterText(formName, "TestForAdidas");
        waitAndEnterText(formCountry, "India");
        waitAndEnterText(formCity, "Delhi");
        waitAndEnterText(formCard, "5555555555554444");
        waitAndEnterText(formMonth, "December");
        waitAndEnterText(formYear, "2020");
        waitAndClick(formPurchaseButton);
    }

    public String[] captureDetailsAndLog() {
        String message = waitAndGetText(thankYouMessage);
        String lines[] = message.split("\\r?\\n");
        String purchaseID = lines[0].substring(4);
        String amount = lines[1].substring(8);

        logger.info("*** purchaseID & amount are: "+purchaseID+" & "+amount);

        return lines;

    }

    public void closeThankYouWindow() {
        waitAndClick(thankyouOk);
    }
}
