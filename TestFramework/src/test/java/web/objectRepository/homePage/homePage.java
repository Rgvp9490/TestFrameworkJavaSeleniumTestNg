package web.objectRepository.homePage;

import framework.performAction.autoweb.Wait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import framework.locatorReader.locatorHelper;

public class homePage {

    WebDriver driver;
    Wait wait;

    public homePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Wait(driver);
        this.wait.setPageClass(homePage.class);
        PageFactory.initElements(driver, this);
    }

    private WebElement locator(String key) {
        return locatorHelper.getElement(driver, this.getClass(), key);
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
        wait.waitForSeconds(5);
    }

    public void searchYouTube(String searchTerm) {
        wait.waitUntilElementIsVisible("searchBox");
        locator("searchBox").sendKeys(searchTerm);
        locator("searchBox").sendKeys(Keys.ENTER);
    }
}