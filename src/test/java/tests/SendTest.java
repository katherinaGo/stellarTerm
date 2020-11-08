package tests;

import models.SendFields;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.RetryAnalyzer;
import tests.base.BaseTest;

public class SendTest extends BaseTest {

    private static final String VALID_RECIPIENT = "GAWNF5KPQB5HZCHUBUNXBRXLHYAYOQGYL3NKGMV2CEGXSS6EDSOJJL2I";
    private static final String ACCEPT_ALL_ADDRESS = "katya.gowin.all*lobstr.co";
    private static final String VALID_RECIPIENT_WITH_REQUIRED_MEMO = "GBDUXW4E5WRM5EM6UXBLE7Y5XGSXJX472BSSBPKFPQ3PJCJHRIA6SH4C";
    private static final String XLM_SEND = " XLM";
    private static final String amount = "0.000012";

    private static final SendFields sendFields = new SendFields(
            "0.000012",
            " KIN",
            "typed by AutoTest");

    @Test
    public void sendXLMToActivatedPublicKey() {
        sendPage.openSendPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        sendPage.fillSendInputsWithoutMemo(VALID_RECIPIENT, sendFields.getAmount())
                .confirmTransaction()
                .checkIfSendCompletedWithoutMemo(XLM_SEND, VALID_RECIPIENT);
    }

    @Test
    public void sendAssetToActivatedPublicKey() {
        sendPage.openSendPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        sendPage.inputRecipientAndAmount(VALID_RECIPIENT, amount)
                .chooseAssetToSend()
                .confirmTransaction()
                .checkIfSendCompletedWithoutMemo(sendFields.getAsset(), VALID_RECIPIENT);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void sendXLMToAddressWithMemoRequired() {
        sendPage.openSendPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        sendPage.inputRecipientAndAmount(ACCEPT_ALL_ADDRESS, amount)
                .confirmTransactionWithMemo()
                .checkIfSendCompletedWithMemo(XLM_SEND, ACCEPT_ALL_ADDRESS);
    }

    @Test
    public void checkSendingCorrespondAssetFromBalancesPage() {
        accountPage.navigateToHomePage().openLoginPage();
        sendSteps.clickSendAssetBtnFromBalancePage(VALID_SECRET_KEY);
    }

    @Test
    public void sendXLMtoAddressWithMemoRequiredButNotFilled() {
        sendPage.openSendPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        sendPage.inputRecipientAndAmount(VALID_RECIPIENT_WITH_REQUIRED_MEMO, amount)
                .checkIfMemoRequiredButEmpty(sendFields.getMemoText())
                .confirmTransactionWithMemo()
                .checkIfSendCompletedWithMemo(XLM_SEND, VALID_RECIPIENT_WITH_REQUIRED_MEMO);
    }

    @Test
    public void sendXLMtoAddressWithMemo() {
        sendPage.openSendPage();
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        sendPage.fillSendInputsWithMemo(VALID_RECIPIENT, sendFields.getAmount(), sendFields.getMemoText())
                .confirmTransactionWithMemo()
                .checkIfSendCompletedWithMemo(sendFields.getAsset(), VALID_RECIPIENT);
    }
}