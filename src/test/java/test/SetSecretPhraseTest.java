package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import test.base.BaseTest;
import test.base.TestListener;

@Listeners(TestListener.class)
public class SetSecretPhraseTest extends BaseTest {

    @Test
    public void checkIfSecretPhraseCanBeGenerated() {
        accountPage.openLoginPage();
        accessAccountPage.generateSecretPhrase()
                .isPhraseDisplayed();
    }

    @Test
    public void checkIfSecretPhraseCanBeSet() {
        accountPage.openLoginPage();
        accessAccountPage.setSecretPhrase()
                .isSetPhraseDisplayed();
    }
}