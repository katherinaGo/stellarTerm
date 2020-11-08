package tests;

import io.qameta.allure.Description;
import org.testng.annotations.*;
import tests.base.BaseTest;

public class AuthTest extends BaseTest {

    @DataProvider(name = "Invalid secret keys")
    public Object[][] invalidSecretKeys() {
        return new Object[][]{
                {""}, {"GNK"}, {"SNHCDJNKDJVNKJDNVKJNKVJNKDJNKVJN"}
        };
    }

    @Test
    @Description("Generate new public/secret keys pair and check if it's non-activated account")
    public void createNewAccount() {
        sighUpSteps.navigateToHomePage()
                .createNewAccount();
    }

    @Test(dataProvider = "Invalid secret keys")
    @Description("After generating new public/secret keys pair login with invalid secret and check correct error displayed")
    public void signUpWithInvalidSecretKey(String invalidSecretKey) {
        sighUpSteps
                .navigateToHomePage()
                .signUpWithInvalidSecretWhenGenerateKeys(invalidSecretKey)
                .validateIfInvalidSecretKeyErrorDisplayed();
    }

    @Test
    @Description("Log in with valid secret key and check if logged in")
    public void loginWithValidActivatedSecretKey() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(VALID_SECRET_KEY)
                .isLoggedInWithActivatedSecretKey();
    }

    @Test
    @Description("Log in with not activated account (with 0 xlm on balance)")
    public void loginWithNonActivatedSecretKey() {
        accountPage.openLoginPage();
        accessAccountPage.loginWithSecretKey(INVALID_SECRET_KEY)
                .isLoggedInWithNonActivatedSecretKey();
    }
}