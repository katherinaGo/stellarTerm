package tests;

import org.testng.annotations.Test;
import tests.base.BaseTest;

public class TradeTest extends BaseTest {

    private String amount = "0.000012";
    private String price = "0.5";
    private static int buyOffer = 0;
    private static int sellOffer = 1;

    @Test
    public void checkIfLoggedInOnExchangePage() {
        exchangePage.openExchangePage()
                .loginWithSecretKeyFromPopUp(VALID_SECRET_KEY)
                .checkIfLoggedInSuccessfully();
    }

    @Test
    public void makeBuyOffer() {
        exchangePage.openExchangePage()
                .loginWithSecretKeyFromPopUp(VALID_SECRET_KEY)
                .makeOffer(amount, buyOffer)
                .checkIfOfferCreated();
    }

    @Test
    public void makeSellOffer() {
        exchangePage.openExchangePage()
                .loginWithSecretKeyFromPopUp(VALID_SECRET_KEY)
                .makeOffer(amount, sellOffer)
                .checkIfOfferCreated();
    }

    @Test
    public void makeNotFilledOffer() {
        exchangePage.openExchangePage()
                .loginWithSecretKeyFromPopUp(VALID_SECRET_KEY)
                .makeOfferNotFilled(price, amount, buyOffer)
                .checkIfOfferDisplayedInList(price);
    }

    @Test
    public void cancelOrder() {
        exchangePage.openExchangePage()
                .loginWithSecretKeyFromPopUp(VALID_SECRET_KEY)
                .cancelOrder()
                .checkIfOrderWasCanceled();
    }
}