package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10);
    }

    public void openUrl(String URL) {
        driver.manage().window().maximize();
        driver.get(URL);
    }

    public void waitForElementVisible(final By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable e) {
            Assert.fail("Element not found, that's why can't be visible.");
            log.error(e.getLocalizedMessage());
        }
    }

    public void waitForElementClickable(final By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable e) {
            Assert.fail("Element not found, that's why can't be clickable.");
            log.error(e.getLocalizedMessage());
        }
    }

    public boolean isElementVisible(By by) {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            List<WebElement> list = driver.findElements(by);

            if (list.size() == 0) {
                return false;
            } else {
                try {
                    return list.get(0).isDisplayed();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public WebElement findElementBy(By locator) {
        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException ex) {
            Assert.fail("Element not found by locator " + locator);
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public WebElement findElement(String xPath) {
        try {
            return driver.findElement(By.xpath(xPath));
        } catch (NoSuchElementException ex) {
            Assert.fail("Element not found by locator " + xPath);
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public List<WebElement> findElements(String xPath) {
        try {
            return driver.findElements(By.xpath(xPath));
        } catch (NoSuchElementException ex) {
            Assert.fail("Element not found by locator " + xPath);
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public List<WebElement> getElements(String xPath) {
        return driver.findElements(By.xpath(xPath));
    }

    public void scrollPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hoverOnItem(String item) {
        Actions action = new Actions(driver);
        WebElement element = findElement(item);
        action.moveToElement(element).perform();
    }

    public void clearDataOfWebApp() {
        driver.manage().deleteAllCookies();
    }

    public void quit() {
        driver.quit();
    }

    public void clickOnBtn(String locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public String getValueFromClipboard() {
        String myText = null;
        try {
            myText = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .getData(DataFlavor
                            .stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
            Assert.fail();
        }
        return myText;
    }

    public void switchTabFocus() {
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
    }
}