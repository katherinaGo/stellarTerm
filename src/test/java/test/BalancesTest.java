package test;

import io.qameta.allure.Description;
import org.testng.annotations.*;
import pages.RetryAnalyzer;
import test.base.BaseTest;
import test.base.TestListener;

@Listeners(TestListener.class)
public class BalancesTest extends BaseTest {

    private static final String INVALID_FEDERATION_ERROR = "Symbols '+', '*', '@', '>' and 'space' are not allowed in federation name.";

    @DataProvider(name = "valid federation address")
    public Object[][] validFederationAddressData() {
        return new Object[][]{
                {"blabla"}, {"testAaa"}, {"kateG"}
        };
    }

    @DataProvider(name = "invalid federation address")
    public Object[][] invalidFederationAddressData() {
        return new Object[][]{
                {"bla bla"}, {"tes45@t"}, {"kateG+"}
        };
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Description("Check if copyButton works correct and copy to clipboard public key")
    public void checkPossibilityToCopyPublicKey() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        balancesPage.clickCopyBtnToGetPublicKey()
                .checkIfCopiedPublicCorrect();
    }

    @Test
    @Description("Check if copyButton works correct and copy to clipboard federation address")
    public void checkPossibilityToCopyFederationAddress() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        balancesPage.clickCopyBtnToGetFederationAddress()
                .checkIfCopiedFederationCorrect();
    }

    @Test(dataProvider = "valid federation address")
    @Description("Check if possible to edit existing federation address and set the new one")
    public void checkPossibilityToEditFederation(String validFederation) {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        balancesPage.clickCopyBtnToGetFederationAddress()
                .editFederationAddressToValid(validFederation)
                .checkIfNewFederationSaved();
    }

    @Test(dataProvider = "invalid federation address")
    @Description("Check if correct error displayed when set invalid federation address")
    public void checkErrorWhenSetInvalidFederationAddress(String invalidFederation) {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        balancesPage.editFederationAddressToInvalid(invalidFederation)
                .checkIfCorrespondFederationErrorDisplayed(INVALID_FEDERATION_ERROR);
    }

    @Test(description = "Check if amount of assets correspond to amount of trustlines")
    @Description("Check if amount of assets in 'Balances' section corresponds to amount of trustlines in 'Reserved Balance>Trustlines' section")
    public void checkIfBalanceForTrustlinesReservedCorrectly() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        balancesPage.countAmountOfAssets()
                .checkIfCorrectAssetsAmountDisplayedInReservedBalance();
    }

    @Test(description = "Check of reserved XLM correspond to amount of trustlines")
    @Description("Check if amount of truslines corresponds to reserved XLM (0.5XLM for each trustline)")
    public void checkIfXLMReservedCorrectlyAccordingToAssets() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        balancesPage.countAmountOfAssets()
                .checkIfCorrectXlmReservedForAddedTrustlines();
    }
}