package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AccessAccountPage extends BasePage {

    private static final String SECRET_KEY_FIELD_PATH = "//input[@name='inputSecretKey']";
    private static final String ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH = "//input[@class='LoginPage__accept__checkbox']";
    private static final String SUBMIT_BTN_PATH = "//input[@type='submit']";
    private static final String WALLET_ACCOUNT_ID_TITLE_PATH = "//div//p[contains(text(), 'Your Wallet Account ID')]";
    private static final String ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH = "//*[@class = 'titleDesc'][starts-with(text(), 'Account activation')]";
    private static final String CONFIGURE_SECRET_PHRASE_BTN_PATH = "//p[@class='SecretPhrase_button']";
    private static final String GENERATE_PHRASE_BTN_PATH = "//span[@class='SecretPhraseSetup_generate']";
    private static final String saveBtn = "//button[@class='s-button']";
    private static final String GENERATED_SECRET_PHRASE_BTN_PATH = "//p[@class='SecretPhrase_phrase']";
    private static final String SECRET_PHRASE_INPUT_PATH = "//input[@type='text']";
    private static final String NO_THANKS_INFLATION_BTN_PATH = "//a[@class='Inflation_noThanks']";

    private static final String CUSTOM_SECRET_PHRASE = "Don't Quit Your Day Dream.";
    private static String secretPhrase;

    public AccessAccountPage(WebDriver driver) {
        super(driver);
    }

    public void inputSecretKey(String secretKey) {
        findElement(SECRET_KEY_FIELD_PATH).sendKeys(secretKey);
    }

    public AccessAccountPage loginWithSecretKey(String secretKey) {
        inputSecretKey(secretKey);
        checkAcceptTermsConditionsCheckbox();
        clickLogInButton();
        try {
            if (findElement(NO_THANKS_INFLATION_BTN_PATH).isDisplayed()) {
                findElement(NO_THANKS_INFLATION_BTN_PATH).click();
            } else {
                System.out.println("No inflation set");
            }
        } catch (AssertionError ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    public void isLoggedInWithActivatedSecretKey() {
        boolean isLoggedIn = isAccountIDTitleDisplayed();
        Assert.assertTrue(isLoggedIn, "Not logged in with activated public key.");
    }

    public void isLoggedInWithNonActivatedSecretKey() {
        boolean isLoggedIn = isAccountActivationTitleDisplayed();
        Assert.assertTrue(isLoggedIn, "Not logged in with non-activated public key.");
    }

    public AccessAccountPage generateSecretPhrase() {
        clickConfigureSecretPhraseButton();
        clickGeneratePhraseButton();
        clickSaveButton();
        return this;
    }

    public AccessAccountPage setSecretPhrase() {
        clearDataOfWebApp();
        driver.navigate().refresh();
        clickConfigureSecretPhraseButton();
        inputSecretPhrase();
        clickSaveButton();
        return this;
    }

    public void isPhraseDisplayed() {
        boolean isPhraseDisplayed = isDisplayedSecretPhrase();
        Assert.assertTrue(isPhraseDisplayed, "Displayed incorrect phrase.");
    }

    public void isSetPhraseDisplayed() {
        boolean isSetPhraseDisplayed = compareDisplayedPhraseAndSet();
        Assert.assertTrue(isSetPhraseDisplayed, "Secret phrase wasn't set.");
    }

    private void checkAcceptTermsConditionsCheckbox() {
        findElement(ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH).click();
    }

    private void clickLogInButton() {
        findElement(SUBMIT_BTN_PATH).click();
    }

    private boolean isAccountIDTitleDisplayed() {
        waitForElementVisible(By.xpath(WALLET_ACCOUNT_ID_TITLE_PATH));
        return findElement(WALLET_ACCOUNT_ID_TITLE_PATH).isDisplayed();
    }

    private boolean isAccountActivationTitleDisplayed() {
        waitForElementVisible(By.xpath(ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH));
        return findElement(ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH).isDisplayed();
    }

    private void clickConfigureSecretPhraseButton() {
        waitForElementClickable(By.xpath(CONFIGURE_SECRET_PHRASE_BTN_PATH));
        findElement(CONFIGURE_SECRET_PHRASE_BTN_PATH).click();
        waitForElementClickable(By.xpath(GENERATE_PHRASE_BTN_PATH));
    }

    private void clickGeneratePhraseButton() {
        findElement(GENERATE_PHRASE_BTN_PATH).click();
    }

    private void clickSaveButton() {
        waitForElementClickable(By.xpath(saveBtn));
        findElement(saveBtn).click();
        waitForElementVisible(By.xpath(GENERATED_SECRET_PHRASE_BTN_PATH));
    }

    private String getDisplayedSecretPhrase() {
        secretPhrase = findElement(GENERATED_SECRET_PHRASE_BTN_PATH).getText();
        return secretPhrase;
    }

    private boolean isDisplayedSecretPhrase() {
        System.out.println("Secret phrase is: " + getDisplayedSecretPhrase());
        return findElement(GENERATED_SECRET_PHRASE_BTN_PATH).isDisplayed();
    }

    private void inputSecretPhrase() {
        waitForElementClickable(By.xpath(SECRET_PHRASE_INPUT_PATH));
        findElement(SECRET_PHRASE_INPUT_PATH).sendKeys(CUSTOM_SECRET_PHRASE);
    }

    private boolean compareDisplayedPhraseAndSet() {
        return CUSTOM_SECRET_PHRASE.equals(getDisplayedSecretPhrase());
    }
}