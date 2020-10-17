package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.*;

public class BalancesPage extends BasePage {

    private static final String SEND_EURT_PATH = "//a[@href='/account/send?asset=EURT-k.tempocrypto.com']";
    private static final String PUBLIC_KEY_PATH = "//div[@class='AccountIdBlock_content']//strong[contains(text(), 'G')]";
    private static final String COPY_PUBLIC_PATH = "//div[@class='AccountIdBlock_main']//div[@class='CopyButton']";
    private static final String FEDERATION_FROM_HEADER_PATH = "//span[@class='federation']";
    private static final String COPY_FEDERATION_PATH = "//div[@class='Account_alert_right']//span[contains(text(), 'COPY')]";
    private static final String EDIT_FEDERATION_BTN_PATH = "//div[@class='Account_alert_right']//span[contains(text(), 'EDIT')]";
    private static final String EDIT_INPUT_PATH = "//input[@name='inputPriceAsset']";
    private static final By SAVE_BTN_CLASS = By.className("s-button");
    private static final By ERROR_FEDERATION_CLASSNAME = By.className("Federation_warning");
    private static final String ASSET_PATH = "//table[@class='BalancesTable']//div[@class='AssetCardMain__main']";
    private static final String TRUSTLINES_PATH = "//div[@class='reserved_item']/span[contains(text(), 'Trustlines')]";
    private static final String TRUSTLINE_XLM_RESERVED_PATH = "//a[@href='/account/addTrust/']//span[@class='reserve_with_icon']";

    private String expectedPublicKey;
    private String actualPublicKey;
    private String actualFederationAddress;
    private String expectedFederationAddress;
    private String amountOfAssetsFromList;
    private int amountOfAssets;
    private String xlmReserveForfAssets;
    private String amountOfAssetsFromReservedBalance;
    private String actualXlmAmountReservedForTruslines;

    public BalancesPage(WebDriver driver) {
        super(driver);
    }

    public void clickSendBtnNearNeededAsset() {
        clickSendBtn();
    }

    public BalancesPage clickCopyBtnToGetPublicKey() {
        setExpectedPublicKey();
        setActualPublicKey();
        return this;
    }

    public void checkIfCopiedPublicCorrect() {
        boolean isPublicKeyCorrespond = actualPublicKey.equals(expectedPublicKey);
        assertTrue(isPublicKeyCorrespond, "Copied public key doesn't correspond.");
    }

    public BalancesPage clickCopyBtnToGetFederationAddress() {
        setExpectedFederationAddress();
        setActualFederationAddress();
        return this;
    }

    public void checkIfCopiedFederationCorrect() {
        boolean isFederationCorrespond = actualFederationAddress.equals(expectedFederationAddress);
        assertTrue(isFederationCorrespond, "Copied federation address doesn't correspond.");
    }

    public BalancesPage editFederationAddressToValid(String newFederationAddress) {
        setExpectedFederationAddress();
        clickEditBtn();
        inputNewFederation(newFederationAddress);
        saveNewFederation();
        setActualFederationAddress();
        return this;
    }

    public BalancesPage editFederationAddressToInvalid(String invalidFederationAddress) {
        clickEditBtn();
        inputNewFederation(invalidFederationAddress);
        saveNewFederation();
        return this;
    }

    public void checkIfCorrespondFederationErrorDisplayed(String expectedError) {
        String actualError = findElementBy(ERROR_FEDERATION_CLASSNAME).getAttribute("innerText");
        boolean isCorrectErrorDisplayed = actualError.equals(expectedError);
        assertTrue(isCorrectErrorDisplayed, "Invalid federation error is nor correct.");
    }

    public void checkIfNewFederationSaved() {
        boolean isNewFederationSet = !actualFederationAddress.equals(expectedFederationAddress);
        assertTrue(isNewFederationSet);
    }

    public BalancesPage countAmountOfAssets() {
        amountOfAssetsFromList = String.valueOf(setAmountOfAssetsFromList());
        System.out.println(String.format("Amount of assets in 'Balances' section: %s", amountOfAssetsFromList));
        amountOfAssetsFromReservedBalance = setAmountOfAssetsFromReservedBalance();
        System.out.println(String.format("Amount of assets in 'Reserved Balance>Trustlines' section: %s", amountOfAssetsFromReservedBalance));

        return this;
    }

    public void checkIfCorrectAssetsAmountDisplayedInReservedBalance() {
        boolean isBalanceReservedCorrectlyToAmountOfAssets = amountOfAssetsFromReservedBalance.contains(amountOfAssetsFromList);
        assertTrue(isBalanceReservedCorrectlyToAmountOfAssets, "Amount of assets doesn't not correspond to reserved balance.");
    }

    public void checkIfCorrectXlmReservedForAddedTrustlines() {
        countReservedXLMToTrustlines();
        boolean isCorrectXLMToAssetsAmount = actualXlmAmountReservedForTruslines.contains(xlmReserveForfAssets);
        System.out.println(xlmReserveForfAssets);
        System.out.println(actualXlmAmountReservedForTruslines);
        assertTrue(isCorrectXLMToAssetsAmount, "Incorrect XLM amount reserved for truslines.");
    }

    private int setAmountOfAssetsFromList() {
        amountOfAssets = driver.findElements(By.xpath(ASSET_PATH)).size() - 1;
        return amountOfAssets;
    }

    private String setAmountOfAssetsFromReservedBalance() {
        return findElement(TRUSTLINES_PATH).getAttribute("innerText");
    }

    private void countReservedXLMToTrustlines() {
        xlmReserveForfAssets = String.valueOf((amountOfAssets * 0.5)).replaceAll("()\\.0+$|(\\..+?)0+$", "$2");
        actualXlmAmountReservedForTruslines = findElement(TRUSTLINE_XLM_RESERVED_PATH).getAttribute("innerText");
    }

    private void clickEditBtn() {
        findElement(EDIT_FEDERATION_BTN_PATH).click();
    }

    private void inputNewFederation(String newFederationAddress) {
        findElement(EDIT_INPUT_PATH).clear();
        findElement(EDIT_INPUT_PATH).sendKeys(newFederationAddress);
    }

    private void saveNewFederation() {
        waitForElementClickable(SAVE_BTN_CLASS);
        findElementBy(SAVE_BTN_CLASS).click();
    }

    private void setActualFederationAddress() {
        findElement(COPY_FEDERATION_PATH).click();
        actualFederationAddress = getValueFromClipboard();
    }

    private void setExpectedFederationAddress() {
        findElement(FEDERATION_FROM_HEADER_PATH).click();
        expectedFederationAddress = getValueFromClipboard();
    }

    private void clickSendBtn() {
        findElement(SEND_EURT_PATH).click();
    }

    private void setActualPublicKey() {
        findElement(COPY_PUBLIC_PATH).click();
        actualPublicKey = getValueFromClipboard();
    }

    private void setExpectedPublicKey() {
        expectedPublicKey = findElement(PUBLIC_KEY_PATH).getAttribute("innerText");
    }
}