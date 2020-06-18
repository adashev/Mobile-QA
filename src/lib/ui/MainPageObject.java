package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
   protected AppiumDriver driver;

   public MainPageObject(AppiumDriver driver) {
      this.driver = driver;
   }

   public void assertElementPresent(By by, String error_message) {
      List<WebElement> elements = driver.findElements(by);
      Assert.assertFalse(error_message, elements.isEmpty());
   }

   public void addArticleToReadingList(String searchName, String linksName) {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchName, "Cannot find search field", 5);
      waitForElementAndClick(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", linksName)),
            "Cannot find 'Leo Tolstoy' link", 5);
      waitForElementPresent(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='%s']", linksName)),
            "Cannot find article title", 10);
      waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options", 5);
      waitForElementAndClick(By.xpath("//*[contains(@text, 'Add to reading list')]"), "Cannot find option", 5);
   }

   public String waitForElementAndGetAttribute(By by, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      return element.getText();
   }

   public void assetElementNotPresent(By by, String error_message) {
      int amountOfElements = getAmountOfElements(by);
      if (amountOfElements > 0) {
         String message = "An element '" + by.toString() + "\'supposed to ne not present";
         throw new AssertionError(message + " " + error_message);
      }
   }

   public int getAmountOfElements(By by) {
      return driver.findElements(by).size();
   }

   public void swipeElementToLeft(By by, String error_message) {
      WebElement element = waitForElementPresent(by, error_message, 10);
      int leftX = element.getLocation().getX();
      int rightX = leftX + element.getSize().getWidth();
      int upperY = element.getLocation().getY();
      int lowerY = upperY + element.getSize().getHeight();
      int middleY = (upperY + lowerY) / 2;
      TouchAction action = new TouchAction(driver);
      action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();
   }

   public void swipeUp(int timeOfSwipe) {
      TouchAction action = new TouchAction(driver);
      Dimension size = driver.manage().window().getSize();//получили размеры экрана
      int x = size.width / 2;
      int startY = (int) (size.height * 0.8);
      int endY = (int) (size.height * 0.2);
      action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
   }

   public void swipeUpQuick() {
      swipeUp(500);
   }

   public void swipeUpToFindElement(By by, String error_message, int maxSwipes) {
      int alreadySwiped = 0;
      driver.findElements(by).size();
      while (driver.findElements(by).size() == 0) {
         if (alreadySwiped > maxSwipes) {
            waitForElementPresent(by, "Cannot find element by swiping up.\n" + error_message, 0);
            return;
         }
         swipeUpQuick();
         ++alreadySwiped;
      }
   }

   public boolean isWordPresentInSearch(List<WebElement> articles) {
      for (WebElement artc : articles) {
         if (!artc.getText().toLowerCase().contains("java")) {
            return false;
         }
      }
      return true;
   }

   public List<WebElement> waitForElementsPresent(By by, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
   }

   public WebElement waitForPresentTextSearch() {
      WebElement searchSrcTextElement = waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            "Cannot find search_src_text element", 10);
      Assert.assertEquals("Unexpected text of element!", "Search…", searchSrcTextElement.getText());
      return searchSrcTextElement;
   }

   public WebElement waitForElementPresent(By by, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
   }

   public WebElement waitForElementPresent(By by, String error_message) {
      return waitForElementPresent(by, error_message, 5);
   }

   public WebElement waitForElementAndClick(By by, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.click();
      return element;
   }

   public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.sendKeys(value);
      return element;
   }

   public boolean waitForElementNotPresent(By by, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
   }

   public WebElement waitForElementAndClear(By by, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.clear();
      return element;
   }
}
