package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import elements.DropDown;
import elements.Input;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SendPage extends BasePage {

    private static final String SEND_URL = "https://staging--hopeful-brahmagupta-343062.netlify.app/account/send?asset=XLM-native";
    private static final String SEND_BTN_PATH = "//span[contains(text(), 'Send')]";
    private static final By RECIPIENT_FIELD_NAME = By.name("recipient");
    private static final By AMOUNT_NAME = By.name("inputSendAmount");
    private static final String NEXT_BTN_PATH = "//button[contains(text(), 'Next')]";
    private static final By RECIPIENT_ADDRESS_CLASSNAME = By.className("publicKey_resolved");
    private static final String ACTUAL_AMOUNT_TO_SEND_PATH = "//div[@class='content_text'][contains(text(), '0')]";
    private static final String SUBMIT_TRANSACTION_BTN_PATH = "//button[contains(text(), 'Submit transaction')]";
    private static final String PAYMENT_SENT_TITLE_PATH = "//div[contains(text(), 'Payment sent to ')]";
    private static final String RESOLVED_FEDERATION_PATH = "//span[contains(text(), 'resolved to')]";
    private static final String DROPDOWN_TO_CHOOSE_ASSET_PATH = "//div[@class='AssetCardSeparateLogo']";
    private static final String KIN_ASSET_PATH = "//span[contains(text(), 'KIN')]";
    private static final String MEMO_PATH = "//div[@class='content_title'][contains(text(), 'Memo text')]";
    private static final String SEND_EURT_PATH = "//span[@class='AssetCardSeparateLogo_code']";
    private static final String MEMO_VALUE_PATH = "//input[@name='memo']";

    private static final String EXPECTED_AMOUNT_TO_SEND = "0.000012";
    private String actualRecipientPublic;
    private String actualAmount;
    private boolean isMemoTitleDisplayed;

    public SendPage(WebDriver driver) {
        super(driver);
    }

    public SendPage openSendPage() {
        openUrl(SEND_URL);
        return this;
    }

    public SendPage inputRecipientAndAmount(String recipient) {
        new Input(driver, "recipient").write(recipient);
        new Input(driver, "inputSendAmount").write(actualAmount);
//        inputValidAmount();
        return this;
    }

    public SendPage fillSendInputsWithoutMemo(String recipient, String amount) {
        new Input(driver, "recipient").write(recipient);
        new Input(driver, "inputSendAmount").write(amount);
        return this;
    }

    public SendPage fillSendInputsWithMemo(String recipient, String amount, String memo) {
        new Input(driver, "recipient").write(recipient);
        new Input(driver, "inputSendAmount").write(amount);
        new DropDown(driver, "XLM", "KIN").chooseOption();
        new DropDown(driver, "No memo", "Memo text").chooseOption();
        new Input(driver, "memo").write(memo);
        return this;
    }

    public SendPage confirmTransaction() {
        clickNextBtn();
        setActualRecipientPublic();
        setActualAmount();
        clickSubmitBtn();
        return this;
    }

    public SendPage confirmTransactionWithMemo() {
        clickNextBtn();
        setActualRecipientPublic();
        setActualAmount();
        setMemo();
        clickSubmitBtn();
        return this;
    }

    public SendPage chooseAssetToSend() {
        findElement(DROPDOWN_TO_CHOOSE_ASSET_PATH).click();
        findElement(KIN_ASSET_PATH).click();
        return this;
    }

    public void checkIfSendCompletedWithoutMemo(String asset_code, String recipient) {
        boolean isSendCompleted = driver.findElement(By.xpath(PAYMENT_SENT_TITLE_PATH)).isDisplayed();
        assertEquals(actualRecipientPublic, recipient, "Recipient doesn't correspond.");
        assertEquals(actualAmount, EXPECTED_AMOUNT_TO_SEND + asset_code, "Amount doesn't correspond.");
        assertTrue(isSendCompleted, "Send transaction is not completed.");
    }

    public void checkIfSendCompletedWithMemo(String asset_code, String recipient) {
        boolean isSendCompleted = driver.findElement(By.xpath(PAYMENT_SENT_TITLE_PATH)).isDisplayed();
        assertEquals(actualRecipientPublic, recipient, "Recipient doesn't correspond.");
        assertTrue(isMemoTitleDisplayed, "Memo is not displayed.");
        assertEquals(actualAmount, EXPECTED_AMOUNT_TO_SEND + asset_code, "Amount doesn't correspond.");
        assertTrue(isSendCompleted, "Send transaction is not completed.");
    }

    public void checkIfCorrectAssetChosenToSend() {
        String actualAsset = driver.findElement(By.xpath(SEND_EURT_PATH)).getAttribute("innerText");
        System.out.println(actualAsset);
        boolean isCorrectAssetDisplayed = actualAsset.contains("EURT");
        assertTrue(isCorrectAssetDisplayed, "Asset doesn't correspond choose asset on Balances page.");
    }

    public SendPage checkIfMemoRequiredButEmpty(String memo) {
        String actualMemo = driver.findElement(By.xpath(MEMO_VALUE_PATH)).getAttribute("value");
        assertEquals(actualMemo, "", "Memo should be required, but NOT filled.");
        inputMemo(memo);
        return this;
    }

    private void inputMemo(String memo) {
        findElement(MEMO_VALUE_PATH).sendKeys(memo);
    }

    private void clickSubmitBtn() {
        findElement(SUBMIT_TRANSACTION_BTN_PATH).click();
        waitForElementVisible(By.xpath(PAYMENT_SENT_TITLE_PATH));
    }

    private void setMemo() {
        waitForElementVisible(By.xpath(MEMO_PATH));
        isMemoTitleDisplayed = findElement(MEMO_PATH).isDisplayed();
    }

    private void setActualRecipientPublic() {
        actualRecipientPublic = findElementBy(RECIPIENT_ADDRESS_CLASSNAME).getAttribute("innerText").trim();
    }

    private void setActualAmount() {
        actualAmount = findElement(ACTUAL_AMOUNT_TO_SEND_PATH).getAttribute("innerText").trim();
    }

    private void clickSendBtn() {
        findElement(SEND_BTN_PATH).click();
    }

    private void inputValidRecipient(String recipient) {
        findElementBy(RECIPIENT_FIELD_NAME).sendKeys(recipient);
    }

    private void inputValidAmount() {
        findElementBy(AMOUNT_NAME).sendKeys(EXPECTED_AMOUNT_TO_SEND);
    }

    private void clickNextBtn() {
        waitForElementVisible(By.xpath(RESOLVED_FEDERATION_PATH));
        findElement(NEXT_BTN_PATH).click();
        waitForElementClickable(By.xpath(SUBMIT_TRANSACTION_BTN_PATH));
    }
}