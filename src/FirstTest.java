import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

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
      Thread.sleep(2000);
      WebElement element_to_enter_search_line = driver.findElementByXPath("//*[contains(@text, 'Search…')]");
      element_to_enter_search_line.sendKeys("Appium");
      Thread.sleep(2500);
//      System.out.println("First test run");
   }
}
