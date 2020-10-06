package test.base;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.*;
import steps.SendSteps;
import steps.SighUpSteps;

import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class BaseTest {

    protected static WebDriver driver;
    protected AccountPage accountPage;
    protected SignUpPage signUpPage;
    protected SighUpSteps sighUpSteps;
    protected SendSteps sendSteps;
    protected AccessAccountPage accessAccountPage;
    protected SendPage sendPage;
    protected BasePage basePage;
    protected HeaderPage headerPage;
    protected BalancesPage balancesPage;
    protected AcceptAssetPage acceptAssetPage;
    protected Properties properties;

    @SneakyThrows
    @BeforeMethod
    public void setUp(ITestContext context) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        properties = new Properties();
        FileReader reader = new FileReader("src/test/resources/local.properties");
        properties.load(reader);
//        driver = new ChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        context.setAttribute("driver", driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        basePage = new BasePage(driver);
        accountPage = new AccountPage(driver);
        signUpPage = new SignUpPage(driver);
        sendSteps = new SendSteps(driver);
        sighUpSteps = new SighUpSteps(driver);
        accessAccountPage = new AccessAccountPage(driver);
        sendPage = new SendPage(driver);
        headerPage = new HeaderPage(driver);
        balancesPage = new BalancesPage(driver);
        acceptAssetPage = new AcceptAssetPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        basePage.quit();
    }
}