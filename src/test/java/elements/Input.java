package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Input {

    private WebElement element;
    private static final String INPUT_PATH = "//input[@name='%s']";

    public Input(WebDriver driver, String field) {
        element = driver.findElement(By.xpath(String.format(INPUT_PATH, field)));
    }

    public void write(String text) {
        element.sendKeys(text);
    }
}