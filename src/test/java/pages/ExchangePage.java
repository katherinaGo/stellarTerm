package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExchangePage extends BasePage {

    private static final String EXCHANGE_URL = "https://staging--hopeful-brahmagupta-343062.netlify.app/exchange/USD-www.anchorusd.com/XLM-native";
    private static final String LOGIN_BTN_PATH = "//input[@class='LoginPage__button']";
    private static final String LOGIN_BTN_POPUP_PATH = "//span[@class='offer_message']/a[contains(text(), 'Log in')]";
    private static final String SECRET_KEY_FIELD_PATH = "//input[@name='inputSecretKey']";
    private static final String ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH = "//input[@class='LoginPage__accept__checkbox']";
    private static final String SUBMIT_BTN_PATH = "//input[@type='submit']";
    private static final String BUY_BTN_PATH = "//button[@class='offer_button Buy']";
    private static final String SELL_BTN_PATH = "//button[@class='offer_button Sell']";
    private static final String FEDERATION_PATH = "//span[@class='federation']";
    private static final String AMOUNT_INPUT_PATH = "//input[@name='amount']";
    private static final String PRICE_INPUT_PATH = "//input[@name='price']";
    private static final String ORDER_CREATED_PATH = "//div[@class='OfferMakerResultMessage_message success']";
    private static final String PRICE_PATH = "//td[@class='ManageOffers__table__row__item'][contains(text(), '%s')]";
    private static final String CANCEL_BTN_PATH = "//button[@title='Remove offer']";
    private static final String NO_OFFERS_TITLE_PATH = "//td[@class='ManageOffers__table__row__none']";

    public ExchangePage(WebDriver driver) {
        super(driver);
    }

    public ExchangePage openExchangePage() {
        openUrl(EXCHANGE_URL);
        waitForElementVisible(By.xpath(LOGIN_BTN_POPUP_PATH));
        return this;
    }

    public ExchangePage loginWithSecretKeyFromPopUp(String secretKey) {
        clickLoginBtnToOpenPopUP();
        inputSecretKey(secretKey);
        checkAcceptTermsConditionsCheckbox();
        clickLoginBtn();
        waitForElementVisible(By.xpath(FEDERATION_PATH));
        return this;
    }

    public void checkIfLoggedInSuccessfully() {
        boolean isLoggedIn = findElement(FEDERATION_PATH).isDisplayed();
        assertTrue(isLoggedIn, "User is not logged in from the Exchange page.");
    }

    public ExchangePage makeOffer(String amount, int buy0Sell1Offer) {
        inputAmount(amount, buy0Sell1Offer);
        clickBuySellButton(buy0Sell1Offer);
        return this;
    }

    public ExchangePage makeOfferNotFilled(String price, String amount, int buy0Sell1Offer) {
        changePrice(price, buy0Sell1Offer);
        inputAmount(amount, buy0Sell1Offer);
        clickBuySellButton(buy0Sell1Offer);
        waitForElementVisible(By.xpath(ORDER_CREATED_PATH));
        return this;
    }

    public void checkIfOfferDisplayedInList(String expectedPrice) {
        String actualPrice = findElement(String.format(PRICE_PATH, expectedPrice)).getAttribute("innerText").replaceAll("()\\.0+$|(\\..+?)0+$", "$2");
        ;
        assertEquals(expectedPrice, actualPrice, "Offer is not created, or another one.");
    }

    public void checkIfOfferCreated() {
        waitForElementVisible(By.xpath(ORDER_CREATED_PATH));
        boolean isOfferCreated = findElement(ORDER_CREATED_PATH).isDisplayed();
        assertTrue(isOfferCreated, "Offer wasn't created.");
    }

    public ExchangePage cancelOrder() {
        clickCancelOfferButton();
        return this;
    }

    public void checkIfOrderWasCanceled() {
    }

    private void clickCancelOfferButton() {
        findElement(CANCEL_BTN_PATH).click();
    }

    private void changePrice(String price, int buy1Sell2Offer) {
        findElement(PRICE_INPUT_PATH).clear();
        findElements(PRICE_INPUT_PATH).get(buy1Sell2Offer).sendKeys(price);
    }

    private void inputAmount(String amount, int buy1Sell2Offer) {
        findElements(AMOUNT_INPUT_PATH).get(buy1Sell2Offer).sendKeys(amount);
    }

    private void clickBuySellButton(int buy0Sell1Offer) {
        if (buy0Sell1Offer == 0) {
            findElement(BUY_BTN_PATH).click();
        } else {
            findElement(SELL_BTN_PATH).click();
        }
    }

    private void clickLoginBtnToOpenPopUP() {
        findElement(LOGIN_BTN_POPUP_PATH).click();
    }

    private void clickLoginBtn() {
        findElement(LOGIN_BTN_PATH).click();
    }

    private void inputSecretKey(String secretKey) {
        findElement(SECRET_KEY_FIELD_PATH).sendKeys(secretKey);
    }

    private void checkAcceptTermsConditionsCheckbox() {
        findElement(ACCEPT_TERMS_CONDITIONS_CHECKBOX_PATH).click();
    }

    private void clickLogInButton() {
        findElement(SUBMIT_BTN_PATH).click();
    }
}