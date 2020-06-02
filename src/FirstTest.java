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
      WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
      element_to_init_search.click();
      WebElement element_to_enter_search_line = waitForElementPresentByXPath(
            "//*[contains(@text, 'Search…')]",
            "Cannot find search input");
      element_to_enter_search_line.sendKeys("Java");
      waitForElementPresentByXPath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
            "Cannot find Object-oriented programming language", 15);
      waitForElementPresentByXPath("//*[@text='Wikimedia list article']",
            "Cannot find Wikimedia list article", 10);
      Thread.sleep(2500);
   }

   private WebElement waitForElementPresentByXPath(String xpath, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      By by = By.xpath(xpath);
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
   }

   private WebElement waitForElementPresentByXPath(String xpath, String error_message) {

      return waitForElementPresentByXPath(xpath, error_message, 5);
   }

}
