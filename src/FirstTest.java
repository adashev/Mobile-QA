import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
   private AppiumDriver driver;

   @Before
   public void setUp() throws Exception {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("platformName", "Android");
      capabilities.setCapability("deviceName", "AndroidTestDevice");
      capabilities.setCapability("platformVersion", "8.0");
      capabilities.setCapability("automationName", "Appium");
      capabilities.setCapability("appPackage", "org.wikipedia");
      capabilities.setCapability("appActivity", ".main.MainActivity");
      capabilities.setCapability("app", "g:/Job/Selenium/2020/Mobile-QA/apks/org.wikipedia.apk");

      driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
   }

   @After
   public void tearDown() {
      driver.quit();
   }

   @Test
   public void firstTest() throws InterruptedException {
      waitForElementByXpathAndClick("//*[contains(@text, 'Search Wikipedia')]", "Cannot find Search Wikipedia", 5);
      waitForElementByXpathAndSendKeys("//*[contains(@text, 'Search…')]", "Java", "Cannot find Search…", 5);
      waitForElementPresentByXPath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
            "Cannot find Object-oriented programming language", 15);
      waitForElementPresentByXPath("//*[@text='Wikimedia list article']",
            "Cannot find Wikimedia list article", 10);
   }

   @Test
   public void firstCancelSearch() {
      waitForElementByIdAndClick("org.wikipedia:id/search_container", "Cannot find Search Wikipedia", 10);
      //org.wikipedia:id/search_close_btn
      waitForElementByIdAndClick("org.wikipedia:id/search_close_btn", "Cannot find X", 10);
      waitForElementNotPresent("org.wikipedia:id/search_close_btn", "X is still present on the page", 5);
   }

   private WebElement waitForElementPresentByXPath(String xpath, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      By by = By.xpath(xpath);
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
   }

   private WebElement waitForElementPresentById(String id, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      By by = By.xpath(String.format("//*[@resource-id='%s']", id));
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
   }

   private WebElement waitForElementByIdAndClick(String id, String error_message, long timeout) {
      WebElement element = waitForElementPresentById(id, error_message, timeout);
      element.click();
      return element;
   }

   private WebElement waitForElementPresentByXPath(String xpath, String error_message) {
      return waitForElementPresentByXPath(xpath, error_message, 5);
   }

   private WebElement waitForElementByXpathAndClick(String xpath, String error_message, long timeout) {
      WebElement element = waitForElementPresentByXPath(xpath, error_message, timeout);
      element.click();
      return element;
   }

   private WebElement waitForElementByXpathAndSendKeys(String xpath, String value, String error_message, long timeout) {
      WebElement element = waitForElementPresentByXPath(xpath, error_message, timeout);
      element.sendKeys(value);
      return element;
   }

   private boolean waitForElementNotPresent(String id, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      By by = By.id(id);
      return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
   }

}
