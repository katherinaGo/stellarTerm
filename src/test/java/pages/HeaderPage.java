package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends BasePage {

    private static final String BUY_LUMENS_PATH = "//span[contains(text(), 'Buy Lumens')]";
//    private static final String DESTINATION_ADDRESS_PATH = "//input[@name='target_address']";


    public HeaderPage(WebDriver driver) {
        super(driver);
    }


    public HeaderPage clickOnBuyLumensBtn() {
        driver.findElement(By.xpath(BUY_LUMENS_PATH)).click();
        return this;
    }

    public HeaderPage checkIfBuyLumensPageOpened() {

        return this;
    }

    public boolean checkIfPublicKeyDisplayedOnOpenedPage(String publicKey) {
        String currentTabUrl = driver.getCurrentUrl();

        return currentTabUrl.contains(publicKey);
    }

}
