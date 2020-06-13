import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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
   public void firstTest() {
      waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Cannot find Search Wikipedia", 5);
      WebElement searchSrcTextElement = waitForPresentTextSearch();
      searchSrcTextElement.sendKeys("Java");
      waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 15);
      waitForElementPresent(By.xpath("//*[@text='Wikimedia list article']"), "Cannot find Wikimedia list article", 10);
   }

   @Test
   public void testCancelSearch() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Cannot find search field", 5);
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "Cannot find X", 10);
      waitForElementNotPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "X is still present on the page", 5);
   }

   @Test
   public void testCompareArticleTitle() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 5);
      WebElement titleElement = waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find article title", 10);
      String articleTitle = titleElement.getText();
      Assert.assertEquals("Unexpected title!", "Java (programming language)", articleTitle);
   }

   @Test
   public void testCancelSearchEx3() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Selenium", "Cannot find search field", 5);
      waitForElementsPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find articles", 10);
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "Cannot find X", 10);
      List<WebElement> articles = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
      Assert.assertTrue("Articles aren't missing!", articles.isEmpty());
   }

   @Test
   public void testSearchWordsInSearch() {
      waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Cannot find Search Wikipedia", 5);
      WebElement searchSrcTextElement = waitForPresentTextSearch();
      searchSrcTextElement.sendKeys("Java");
      List<WebElement> articles = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
      Assert.assertTrue("Not all headers contain the word 'Java'", isWordPresentInSearch(articles));
   }

   @Test
   public void testSwipeArticle() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Appium", "Cannot find search field", 5);
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium'", 5);
      waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"), "Cannot find article title", 10);
      swipeUpToFindElement(By.xpath("//*[@text='View page in browser']"), "Cannot find the end of the article", 20);
   }

   @Test
   public void testSaveArticleToMyList() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      waitForElementAndClick(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 5);
      waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find article title", 10);
      waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options", 5);
      waitForElementAndClick(By.xpath("//*[contains(@text, 'Add to reading list')]"),
            "Cannot find option", 5);
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"),
            "Cannot find 'Goi it'", 5);
      waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            "Cannot find text input", 5);
      String foldersName = "Learning Java";
      waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            foldersName, "Cannot find text input", 5);
      waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text='OK']"),
            "Cannot press OK", 5);
      waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X link", 5);
      waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists'", 5);
      waitForElementAndClick(By.xpath(String.format("//*[contains(@text, '%s')]", foldersName)),
            "Cannot find created folder", 5);
      String article = "Java (programming language)";
      swipeElementToLeft(By.xpath(String.format("//*[contains(@text, '%s')]", article)),
            "Cannot find saved article");
      waitForElementNotPresent(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", article)),
            "Cannot delete saved article", 10);
   }

   @Test
   public void testAmountOfNotEmptySearch() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "Linkin Park discography";
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      String searchResults = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      waitForElementPresent(By.xpath(searchResults), "Cannot find anything by the request", 15);
      int amountSearchResults = getAmountOfElements(By.xpath(searchResults));
      Assert.assertTrue("We found too few results", amountSearchResults > 0);

   }

   @Test
   public void testAmountOfEmptySearch() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "fngjntzj";
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      String results = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      String resultsEmptyLabel = "//*[@text='No results found']";
      waitForElementPresent(By.xpath(resultsEmptyLabel), "Cannot find empty result by the request " + searchLine, 15);
      assetElementNotPresent(By.xpath(results), "We'му found some results " + searchLine);
   }

   @Test
   public void testChangeScreenOrientation() {
      waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "Java";
      waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      waitForElementAndClick(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + searchLine, 15);
   }

   private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      return element.getAttribute(attribute);
   }

   private void assetElementNotPresent(By by, String error_message) {
      int amountOfElements = getAmountOfElements(by);
      if (amountOfElements > 0) {
         String message = "An element '" + by.toString() + "\'supposed to ne not present";
         throw new AssertionError(message + " " + error_message);
      }
   }

   private int getAmountOfElements(By by) {
      return driver.findElements(by).size();
   }

   protected void swipeElementToLeft(By by, String error_message) {
      WebElement element = waitForElementPresent(by, error_message, 10);
      int leftX = element.getLocation().getX();
      int rightX = leftX + element.getSize().getWidth();
      int upperY = element.getLocation().getY();
      int lowerY = upperY + element.getSize().getHeight();
      int middleY = (upperY + lowerY) / 2;
      TouchAction action = new TouchAction(driver);
      action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();
   }

   protected void swipeUp(int timeOfSwipe) {
      TouchAction action = new TouchAction(driver);
      Dimension size = driver.manage().window().getSize();//получили размеры экрана
      int x = size.width / 2;
      int startY = (int) (size.height * 0.8);
      int endY = (int) (size.height * 0.2);
      action.press(x, startY).waitAction(timeOfSwipe).moveTo(x, endY).release().perform();
   }

   protected void swipeUpQuick() {
      swipeUp(500);
   }

   protected void swipeUpToFindElement(By by, String error_message, int maxSwipes) {
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

   private boolean isWordPresentInSearch(List<WebElement> articles) {
      for (WebElement artc : articles) {
         if (!artc.getText().toLowerCase().contains("java")) {
            return false;
         }
      }
      return true;
   }

   private List<WebElement> waitForElementsPresent(By by, String error_message, long timeout) {
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

   private WebElement waitForElementPresent(By by, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      return wait.until(ExpectedConditions.presenceOfElementLocated(by));
   }

   private WebElement waitForElementPresent(By by, String error_message) {
      return waitForElementPresent(by, error_message, 5);
   }

   private WebElement waitForElementAndClick(By by, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.click();
      return element;
   }

   private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.sendKeys(value);
      return element;
   }

   private boolean waitForElementNotPresent(By by, String error_message, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver, timeout);
      wait.withMessage(error_message + "\n");
      return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
   }

   private WebElement waitForElementAndClear(By by, String error_message, long timeout) {
      WebElement element = waitForElementPresent(by, error_message, timeout);
      element.clear();
      return element;
   }
}
