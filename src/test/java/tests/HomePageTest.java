package tests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utilities.Browser;
import org.apache.logging.log4j.Logger;

public class HomePageTest extends TestBase{
    private static Logger logger = LogManager.getLogger(HomePageTest.class);
    private HomePage homepage = new HomePage();

    @Test (priority=1)
    public void testPageTitle() {
        Assert.assertEquals(Browser.getTitle(), "STORE");
        logger.info("Title verified!");
    }

    @Test (priority=2)
    public void navigateCategories() {
        homepage.navigateCategories();
    }

    @Test (priority=3)
    public void addLaptops() {
        homepage.addSonyLaptop();
        homepage.goBackHome();
        homepage.addDellLaptop();
    }

    @Test (priority=4)
    public void deleteFromCart() {
        homepage.goToCart();
        homepage.deleteDell();
    }

    @Test (priority=5)
    public void completePurchase() {
        homepage.placingOrder();
        homepage.fillWebFormAndPurchase();
    }

    @Test (priority=6)
    public void captureAndLogPurchasedetails() {
        String[] lines = homepage.captureDetailsAndLog();
        String amount = lines[1].substring(8);
        Assert.assertEquals(amount, "790 USD");
        homepage.closeThankYouWindow();
    }

}
