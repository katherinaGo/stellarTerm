package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DropDown {

    private static final String DROPDOWN_PATH = "//div[@class='dropdown_selected']//span[contains(text(), '%s')]";
    private static final String DROPDOWN_CHOOSE_PATH = "//div[@class='dropdown_list']//*[contains(text(), '%s')]";
    private WebElement element;

    public DropDown(WebDriver driver, String field, String assetOrMemo) {
        driver.findElement(By.xpath(String.format(DROPDOWN_PATH, field))).click();
        element = driver.findElement(By.xpath(String.format(DROPDOWN_CHOOSE_PATH, assetOrMemo)));
    }

    public void chooseOption() {
        element.click();
    }
}