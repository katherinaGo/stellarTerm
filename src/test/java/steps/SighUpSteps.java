package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.AccountPage;
import pages.SignUpPage;

public class SighUpSteps {
    private SignUpPage signUpPage;
    private AccountPage accountPage;

    public SighUpSteps(WebDriver driver) {
        signUpPage = new SignUpPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Step("Open main page")
    public SighUpSteps navigateToHomePage() {
        accountPage.navigateToHomePage();
        return this;
    }

    @Step("Create new account: generate new public/secret key pair and logs in")
    public SighUpSteps createNewAccount() {
        signUpPage
                .openSignUpPageFromHome()
                .generateNewAccount()
                .loginWithNewAccount()
                .isLoggedIn();
        return this;
    }

    @Step("Generate new account, but logs in with invalid secret key")
    public SighUpSteps signUpWithInvalidSecretWhenGenerateKeys(String invalidSecretKey) {
        signUpPage
                .openSignUpPageFromHome()
                .generateNewAccount()
                .loginWithInvalidSecretKey(invalidSecretKey);
        return this;
    }

    @Step("Check displaying error when secret key is invalid")
    public SighUpSteps validateIfInvalidSecretKeyErrorDisplayed() {
        String expectedError = "Invalid secret key. Hint: it starts with the letter S and is all uppercase";
        String actualError = signUpPage.isInvalidSecretKeyErrorDisplayed();
        Assert.assertEquals(expectedError, actualError, "No error when logged in with invalid secret key.");
        return this;
    }
}