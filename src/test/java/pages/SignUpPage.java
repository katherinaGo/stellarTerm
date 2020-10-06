package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SignUpPage extends BasePage {

    private static final String CREATE_ACCOUNT_BTN_PATH = "//*[@class='HomePage__lead__actions__sign-up-button HomePage__lead__actions__button s-button']";
    private static final String GENERATE_NEW_ACCOUNT_BTN_PATH = "//button[@class='s-button']";
    private static final String NEW_STELLAR_ACCOUNT_TITLE_PATH = "//*[text()='New Stellar account']";
    private static final String GENERATE_ANOTHER_KEY_PAIR_BTN_PATH = "//*[text()='Generate another keypair']";
    private static final String PUBLIC_KEY_FIELD_PATH = "//div[starts-with(text(), 'G')]";
    private static final String SECRET_KEY_FIELD_PATH = "//div[starts-with(text(), 'S')]";
    private static final String SAVE_KEYS_CHECKBOX_PATH = "//div[@class='SignUpGenerateKeyPair_accept']/input[@type='checkbox']";
    private static final String CONTINUE_BTN_PATH = "//button[@class='s-button']";
    private static final String secretKeyInput_PATH = "//input[@name = 'inputSecretKey']";
    private static final String ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH = "//input[@class='LoginPage__accept__checkbox']";
    private static final String SUBMIT_BTN_PATH = "//input[@type='submit']";
    private static final String ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH = "//*[@class = 'titleDesc'][starts-with(text(), 'Account activation')]";
    private static final String INVALID_SECRET_KEY_ERROR_PATH = "//div//span[contains(text(), 'Invalid secret key. Hint: it starts with the letter S and is all uppercase')]";

    private static String publicKey;
    private static String secretKey;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public SignUpPage openSignUpPageFromHome() {
        clickCreateAccountButton();
        clickGenerateNewAccountButton();
        return this;
    }

    public SignUpPage generateNewAccount() {
        clickGenerateAnotherKeyPairButton();
        setPublicKey();
        setSecretKey();
        String publicKey = getPublicKey();
        String secretKey = getSecretKey();
        System.out.println("New account key pair:\n" + publicKey + "\n" + secretKey);
        return this;
    }

    public SignUpPage loginWithNewAccount() {
        checkSaveKeysCheckbox();
        clickContinueButton();
        inputSecretKey();
        checkAcceptTermsConditionsCheckbox();
        clickLogInButton();
        return this;
    }

    public SignUpPage loginWithInvalidSecretKey(String invalidSecretKey) {
        checkSaveKeysCheckbox();
        clickContinueButton();
        inputInvalidSecretKey(invalidSecretKey);
        checkAcceptTermsConditionsCheckbox();
        clickLogInButton();
        return this;
    }

    public boolean isDisplayedAccountActivationRequiredTitle() {
        waitForElementVisible(By.xpath(ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH));
        return findElement(ACCOUNT_ACTIVATION_REQUIRED_TITLE_PATH).isDisplayed();
    }

    public String isInvalidSecretKeyErrorDisplayed() {
        String error = isErrorMessageDisplayed();
        System.out.println("When logs in with invalid secret key the following error is displayed: " + error);
        return error;
    }

    public void isLoggedIn() {
        boolean isLoggedInWithNewAccount = isDisplayedAccountActivationRequiredTitle();
        Assert.assertTrue(isLoggedInWithNewAccount, "Account wasn't created.");
    }

    private String isErrorMessageDisplayed() {
        waitForElementVisible(By.xpath(INVALID_SECRET_KEY_ERROR_PATH));
        return findElement(INVALID_SECRET_KEY_ERROR_PATH).getText();
    }

    private void clickCreateAccountButton() {
        findElement(CREATE_ACCOUNT_BTN_PATH).click();
        waitForElementClickable(By.xpath(GENERATE_NEW_ACCOUNT_BTN_PATH));
    }

    private void clickGenerateNewAccountButton() {
        findElement(GENERATE_NEW_ACCOUNT_BTN_PATH).click();
        waitForElementVisible(By.xpath(NEW_STELLAR_ACCOUNT_TITLE_PATH));
    }

    private String getPublicKey() {
        return publicKey;
    }

    private String getSecretKey() {
        return secretKey;
    }

    private void setPublicKey() {
        publicKey = findElement(SignUpPage.PUBLIC_KEY_FIELD_PATH).getText();
    }

    private void setSecretKey() {
        secretKey = findElement(SignUpPage.SECRET_KEY_FIELD_PATH).getText();
    }

    private void clickGenerateAnotherKeyPairButton() {
        findElement(GENERATE_ANOTHER_KEY_PAIR_BTN_PATH).click();
        waitForElementVisible(By.xpath(PUBLIC_KEY_FIELD_PATH));
    }

    private void checkSaveKeysCheckbox() {
        findElement(SAVE_KEYS_CHECKBOX_PATH).click();
    }

    private void clickContinueButton() {
        findElement(CONTINUE_BTN_PATH).click();
        waitForElementClickable(By.xpath(secretKeyInput_PATH));
    }

    private void inputSecretKey() {
        findElement(secretKeyInput_PATH).sendKeys(secretKey);
    }

    private void inputInvalidSecretKey(String invalidSecretKey) {
        findElement(secretKeyInput_PATH).sendKeys(invalidSecretKey);
    }

    private void checkAcceptTermsConditionsCheckbox() {
        findElement(ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH).click();
    }

    private void clickLogInButton() {
        waitForElementClickable(By.xpath(SUBMIT_BTN_PATH));
        findElement(SUBMIT_BTN_PATH).click();
    }
}