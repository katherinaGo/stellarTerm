package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private static final String HOME_PAGE_URL = "https://staging--hopeful-brahmagupta-343062.netlify.app/#";
    private static final String ACCOUNT_PAGE_URL = "https://staging--hopeful-brahmagupta-343062.netlify.app/account/";
    private static final String LOG_OUT_BTN_PATH = "//a//span[contains(text(), 'Log out')]";
    private static final String ACCESS_YOUR_ACCOUNT_TITLE_PATH = "//span[@class='LoginPage__title']";
    private static final String LOG_IN_BTN_PATH = "//a[@class='s-button HomePage__lead__actions__button']";

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage openLoginPage() {
        openUrl(ACCOUNT_PAGE_URL);
        return this;
    }

    public AccountPage navigateToHomePage() {
        openUrl(HOME_PAGE_URL);
        return this;
    }

    public AccountPage logout() {
        clickLogOut();
        pressReloadButton();
        return this;
    }

    private void clickLogOut() {
        waitForElementClickable(By.xpath(LOG_OUT_BTN_PATH));
        findElement(LOG_OUT_BTN_PATH).click();
    }

    private void pressReloadButton() {
        driver.switchTo().alert().accept();
        waitForElementVisible(By.xpath(ACCESS_YOUR_ACCOUNT_TITLE_PATH));
    }

    private void clickLogInButton() {
        waitForElementClickable(By.xpath(LOG_IN_BTN_PATH));
        findElement(LOG_IN_BTN_PATH).click();
    }
}