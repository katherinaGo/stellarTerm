package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.*;

public class AcceptAssetPage extends BasePage {

    private static final String ACCEPT_ASSET_PAGE_URL = "https://stellarterm.com/account/addTrust/";

    private static final String ACCEPT_BTN_XPATH = "//span[@class='TrustButton_accept-icon']";
    private static final String ASSETS_PATH = "//div[@class='AssetCardMain__main']";
    private static final String REMOVE_ASSET_XPATH = "//a[@class='BalancesTable__row__removeLink']";
    private static final String REMOVE_ASSET_BTN_XPATH = "//span[text()='%s']//following::*/a[@class='BalancesTable__row__removeLink']";
    private static final String ALERT_ASSET_CREATED_REMOVED = "//div[@class='popup-title'][contains(text(), 'Trustline %s')]";
    private static final String ISSUER_INPUT_XPATH = "//input[@class='s-inputGroup__item S-flexItem-share'][@placeholder='Enter the anchor domain name to see issued assets (e.g. www.anchorusd.com, apay.io, etc)']";
    private static final String FOUND_ASSETS_BY_ISSUER_XPATH = "//span[@class='AssetCardSeparateLogo_domain'][contains(text(), '%s')]";
    private static final String INPUT_ASSET_CODE_XPATH = "//input[@name='inputManuallyAssetCode']";
    private static final String INPUT_ASSET_ISSUER_XPATH = "//input[@name='inputManuallyIssuer']";
    private static final String FOUND_ASSET_HOMEDOMAIN_XPATH = "//div[@class='AssetRow_asset']//span[@class='AssetCardSeparateLogo_domain'][contains(text(), '%s')]";
    private static final String FOUND_ASSET_MANUALLY_XPATH = "//div[@class='AssetCardSeparateLogo_details']/span[@class='AssetCardSeparateLogo_issuer'][contains(text(), '%s')]";
    private static final String TRUST_BUTTON_ERROR_PATH = "//div[@class='TrustButton_error-popup']//span";
    private static final String SECTION_BTN_XPATH = "//a[@class='subNav__nav__item']/span[contains(text(), 'Activity')]";
    private static final String TRUSTLINE_BTN_XPATH = "//a[@class='ActivityNavMenu_item']/span[contains(text(), 'Trustlines')]";
    private static final String TRUSTLINES_HISTORY_TITLE_XPATH = "//span[contains(text(), 'Trustlines history')]";
    private static final String ADDED_ASSET_XPATH = "//div[@class='AssetCardInRow_code'][contains(text(), '%s')]";
    private static int assetsBeforeChanges;
    private static int assetsAfterChanges;
    private static int foundAssets;

    public AcceptAssetPage(WebDriver driver) {
        super(driver);
    }

    public AcceptAssetPage openAcceptAssetPage() {
        openUrl(ACCEPT_ASSET_PAGE_URL);
        return this;
    }

    public void checkIfNewAssetWasAdded() {
        assertEquals(assetsAfterChanges, assetsBeforeChanges + 1, "New asset wasn't added.");
    }

    public AcceptAssetPage addOrRemoveAsset(String createOrRemove) {
        assetsBeforeChanges = getAssets();
        if (createOrRemove.equals("created")) {
            clickAcceptAssetBtn();
        } else if (createOrRemove.equals("removed")) {
            clickRemoveAssetBtn();
        }
        waitForElementVisible(By.xpath(String.format(ALERT_ASSET_CREATED_REMOVED, createOrRemove)));
        assetsAfterChanges = getAssets();
        return this;
    }

    public AcceptAssetPage acceptAssetWIthNoFunds() {
        assetsBeforeChanges = getAssets();
        clickAcceptAssetBtn();
        waitForElementVisible(By.xpath(TRUST_BUTTON_ERROR_PATH));
        return this;
    }

    public AcceptAssetPage acceptAssetManually(String createOrRemove) {
        List<WebElement> acceptBtns = findElements(ACCEPT_BTN_XPATH);
        WebElement acceptBtn = acceptBtns.get(acceptBtns.size() - 1);
        acceptBtn.click();
        waitForElementVisible(By.xpath(String.format(ALERT_ASSET_CREATED_REMOVED, createOrRemove)));
        return this;
    }

    public AcceptAssetPage findAssetByIssuer(String issuerCode) {
        findElement(ISSUER_INPUT_XPATH).sendKeys(issuerCode);
        waitForElementVisible(By.xpath(String.format(FOUND_ASSETS_BY_ISSUER_XPATH, issuerCode)));
        foundAssets = getElements(String.format(FOUND_ASSETS_BY_ISSUER_XPATH, issuerCode)).size();
        return this;
    }

    public AcceptAssetPage findAssetByCodeAndIssuer(String code, String issuer) {
        inputAssetCode(code);
        inputAssetIssuer(issuer);
        waitForElementVisible(By.xpath(String.format(FOUND_ASSET_MANUALLY_XPATH, issuer)));
        return this;
    }

    public void checkIfValidAssetsFound(String issuer) {
        System.out.println("Found " + foundAssets + " assets by issuer " + issuer);
        String homeDomainOfFoundAsset = findElement(String.format(FOUND_ASSET_HOMEDOMAIN_XPATH, issuer)).getAttribute("innerText");
        assertEquals(issuer, homeDomainOfFoundAsset, "Found asset with another domain. Domain should be " + issuer);
    }

    public void checkIfAssetRemoved() {
        assertEquals(assetsAfterChanges, assetsBeforeChanges - 1, "Asset wasn't removed.");
    }

    public void checkIfValidAssetFound(String issuer) {
        String foundAssetIssuer = findElement(String.format(FOUND_ASSET_MANUALLY_XPATH, issuer)).getAttribute("innerText");
        assertEquals(foundAssetIssuer, issuer, "Asset found not valid. Issuer doesn't correspond.");
    }

    public void checkIfNoFundsErrorDisplayed(String expectedError) {
        String actualError = findElement(TRUST_BUTTON_ERROR_PATH).getAttribute("innerText");
        assertEquals(actualError, expectedError, "Incorrect error is displayed when adding asset without funds.");
    }

    public AcceptAssetPage checkIfAddingAssetDisplayedInHistory(String assetCode, String sectionName) {
        openNeededSection(sectionName);
        findElement(TRUSTLINE_BTN_XPATH).click();
        waitForElementVisible(By.xpath(TRUSTLINES_HISTORY_TITLE_XPATH));
        boolean isNeededAssetAccepted = isElementVisible(By.xpath(String.format(ADDED_ASSET_XPATH, assetCode)));
        assertTrue(isNeededAssetAccepted, "Needed asset with code " + assetCode + " wasn't added.");
        return this;
    }

    public AcceptAssetPage removedNeededAsset(String sectionName, String assetCode, String createOrRemove) {
        openNeededSection(sectionName);
        findElement(String.format(REMOVE_ASSET_BTN_XPATH, assetCode)).click();
        waitForElementVisible(By.xpath(String.format(ALERT_ASSET_CREATED_REMOVED, createOrRemove)));
        return this;
    }

    private void openNeededSection(String sectionName) {
        findElement(String.format(SECTION_BTN_XPATH, sectionName)).click();
    }

    private void inputAssetCode(String code) {
        findElement(INPUT_ASSET_CODE_XPATH).sendKeys(code);
    }

    private void inputAssetIssuer(String issuer) {
        findElement(INPUT_ASSET_ISSUER_XPATH).sendKeys(issuer);
    }

    private void clickRemoveAssetBtn() {
        findElement(REMOVE_ASSET_XPATH).click();
    }

    private void clickAcceptAssetBtn() {
        findElement(ACCEPT_BTN_XPATH).click();
    }

    private int getAssets() {
        return getElements(ASSETS_PATH).size();
    }
}