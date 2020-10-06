package test;

import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.base.BaseTest;
import test.base.TestListener;

@Ignore
@Listeners(TestListener.class)
public class HeaderTest extends BaseTest {

    private static final String PUBLIC_KEY = "GBS4CPTLDKYU5SIOEIK4Z4SDHEBW7XPEYM3WUEOJEO4DT24CLQ3OXU4X";


    @Test
    public void checkOpeningBuyCryptoPageWhenLoggedOut() {
        headerPage.clickOnBuyLumensBtn();
    }

    @Test
    public void checkOpeningBuyCryptoPageWithPublicWhenLoggedIn() {
        accountPage.navigateToHomePage();

        headerPage.clickOnBuyLumensBtn()
                .checkIfPublicKeyDisplayedOnOpenedPage(PUBLIC_KEY);

    }

    @Test
    public void checkOpeningBuyCryptoPageWithNonActivatedPublicWhenLoggedIn() {

    }
}
