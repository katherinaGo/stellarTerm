package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

public class BuyLumensPage extends BasePage {

    private static final String BUY_LUMENS_PATH = "//span[contains(text(), 'Buy Lumens')]";
    private static final String DESTINATION_ADDRESS_PATH = "//input[@name='target_address']";

    public BuyLumensPage(WebDriver driver) {
        super(driver);
    }

    public BuyLumensPage clickOnBuyLumensBtn() {
        findElementBy(By.xpath(BUY_LUMENS_PATH)).click();
        return this;
    }

    public BuyLumensPage checkIfBuyLumensPageOpened(String expectedLobstrPage) {
        switchTabFocus();
        String actualLobstrPage = driver.getCurrentUrl();
        assertEquals(actualLobstrPage, expectedLobstrPage, "Page '" + expectedLobstrPage + "' is not opened");
        return this;
    }

    public void checkIfCorrectPublicKeyDisplayedOnOpenedPage(String expectedPublicKey) {
        switchTabFocus();
        waitForElementVisible(By.xpath(DESTINATION_ADDRESS_PATH));
        String actualPublicKey = findElement(DESTINATION_ADDRESS_PATH).getAttribute("value");
        assertEquals(actualPublicKey, expectedPublicKey, "Public key doesn't correspond.");
    }
}