package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.AccessAccountPage;
import pages.BalancesPage;
import pages.SendPage;

public class SendSteps {

    private SendPage sendPage;
    private AccessAccountPage accessAccountPage;
    private BalancesPage balancesPage;

    public SendSteps(WebDriver driver) {
        accessAccountPage = new AccessAccountPage(driver);
        balancesPage = new BalancesPage(driver);
        sendPage = new SendPage(driver);
    }

    @Step("Check if asset from 'Balances' section can be send")
    public SendSteps clickSendAssetBtnFromBalancePage(String ACTIVE_SECRET_KEY) {
        accessAccountPage.loginWithSecretKey(ACTIVE_SECRET_KEY);
        balancesPage.clickSendBtnNearNeededAsset();
        sendPage.checkIfCorrectAssetChosenToSend();
        return this;
    }
}
