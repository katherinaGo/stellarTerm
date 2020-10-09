package test;

import org.testng.annotations.Test;
import test.base.BaseTest;

public class AssetTest extends BaseTest {

    private static final String issuer = "pr.network";
    private static final String assetIssuer = "GAZPKDTEZ5UM3BF4E7FL7EMXRMLH76F2TNVXRLOF6SCVXOFWSPCEWFI5";
    private static final String assetCode = "youtube";
    private static final String expectedError = "Fee would bring account below minimum reserve.";

    @Test(priority = 1)
    public void addAssetTest() {
        acceptAssetPage.openAcceptAssetPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        acceptAssetPage.addOrRemoveAsset("created")
                .checkIfNewAssetWasAdded();
    }

    @Test(priority = 2)
    public void removeAssetTest() {
        acceptAssetPage.openAcceptAssetPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        acceptAssetPage.addOrRemoveAsset("removed")
                .checkIfAssetRemoved();
    }

    @Test
    public void findAssetByIssuerTest() {
        acceptAssetPage.openAcceptAssetPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        acceptAssetPage.findAssetByIssuer(issuer)
                .checkIfValidAssetsFound(issuer);
    }

    @Test
    public void acceptAssetManually() {
        acceptAssetPage.openAcceptAssetPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY);
        acceptAssetPage.findAssetByCodeAndIssuer(assetCode, assetIssuer)
                .checkIfValidAssetFound(assetIssuer);
    }

    @Test
    public void acceptAssetWithNotEnoughFunds() {
        acceptAssetPage.openAcceptAssetPage();
        accessAccountPage.loginWithSecretKey(NO_FUNDS_SECRET_KEY);
        acceptAssetPage.acceptAssetWIthNoFunds()
                .checkIfNoFundsErrorDisplayed(expectedError);
    }
}