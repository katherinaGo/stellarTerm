package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.base.BaseTest;
import test.base.TestListener;

@Listeners(TestListener.class)
public class BuyLumensTest extends BaseTest {

    private static final String ACTIVATED_PUBLIC_KEY = "GAWNF5KPQB5HZCHUBUNXBRXLHYAYOQGYL3NKGMV2CEGXSS6EDSOJJL2I";
    private static final String NON_ACTIVATED_PUBLIC_KEY = "GBG3ICUL63CD3JTXSEW27RXG7JOJUS7W6ZDRBVXVCRJ62YARELUL5TXS";
    private static final String EXPECTED_LOBSTR_URL = "https://lobstr.co/buy-crypto?target_address=%s";

    @Test
    public void checkOpeningBuyCryptoPageWhenLoggedOut() {
        accountPage.openLoginPage();
        buyLumensPage.clickOnBuyLumensBtn()
                .checkIfBuyLumensPageOpened(String.format(EXPECTED_LOBSTR_URL, ""));
    }

    @Test
    public void checkOpeningBuyCryptoPageWithCorrespondPublicWhenLoggedIn() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        buyLumensPage.clickOnBuyLumensBtn()
                .checkIfBuyLumensPageOpened(String.format(EXPECTED_LOBSTR_URL, ACTIVATED_PUBLIC_KEY))
                .checkIfCorrectPublicKeyDisplayedOnOpenedPage(ACTIVATED_PUBLIC_KEY);
    }

    @Test
    public void checkOpeningBuyCryptoPageWithCorrespondPublicWhenNonActivatedPublic() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(NON_ACTIVATED_SECRET_KEY);
        buyLumensPage.clickOnBuyLumensBtn()
                .checkIfBuyLumensPageOpened(String.format(EXPECTED_LOBSTR_URL, NON_ACTIVATED_PUBLIC_KEY))
                .checkIfCorrectPublicKeyDisplayedOnOpenedPage(NON_ACTIVATED_PUBLIC_KEY);
    }
}