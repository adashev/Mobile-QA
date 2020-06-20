import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {
   private MainPageObject mainPageObject;

   protected void setUp() throws Exception {
      super.setUp();
      mainPageObject = new MainPageObject(driver);
   }

   @Test
   public void testSearch() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Cannot find Search Wikipedia", 5);
      WebElement searchSrcTextElement = mainPageObject.waitForPresentTextSearch();
      searchSrcTextElement.sendKeys("Java");
      mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 15);
      mainPageObject.waitForElementPresent(By.xpath("//*[@text='Wikimedia list article']"), "Cannot find Wikimedia list article", 10);
   }

   @Test
   public void testCancelSearch() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      mainPageObject.waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), "Cannot find search field", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "Cannot find X", 10);
      mainPageObject.waitForElementNotPresent(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "X is still present on the page", 5);
   }

   @Test
   public void testCompareArticleTitle() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 5);
      WebElement titleElement = mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find article title", 10);
      String articleTitle = titleElement.getText();
      Assert.assertEquals("Unexpected title!", "Java (programming language)", articleTitle);
   }

   @Test
   public void testCancelSearchEx3() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Selenium", "Cannot find search field", 5);
      mainPageObject.waitForElementsPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find articles", 10);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_close_btn']"), "Cannot find X", 10);
      List<WebElement> articles = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
      Assert.assertTrue("Articles aren't missing!", articles.isEmpty());
   }

   @Test
   public void testSearchWordsInSearch() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Cannot find Search Wikipedia", 5);
      WebElement searchSrcTextElement = mainPageObject.waitForPresentTextSearch();
      searchSrcTextElement.sendKeys("Java");
      List<WebElement> articles = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
      Assert.assertTrue("Not all headers contain the word 'Java'", mainPageObject.isWordPresentInSearch(articles));
   }

   @Test
   public void testSwipeArticle() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Appium", "Cannot find search field", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium'", 5);
      mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"), "Cannot find article title", 10);
      mainPageObject.swipeUpToFindElement(By.xpath("//*[@text='View page in browser']"), "Cannot find the end of the article", 20);
   }

   @Test
   public void testSaveArticleToMyList() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      mainPageObject.waitForElementAndClick(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 5);
      mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (programming language)']"),
            "Cannot find article Object-oriented programming language title", 10);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text, 'Add to reading list')]"),
            "Cannot find option", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"),
            "Cannot find 'Goi it'", 5);
      mainPageObject.waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            "Cannot find text input", 5);
      String foldersName = "Learning Java";
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            foldersName, "Cannot find text input", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text='OK']"),
            "Cannot press OK", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X link", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists'", 5);
      mainPageObject.waitForElementAndClick(By.xpath(String.format("//*[contains(@text, '%s')]", foldersName)),
            "Cannot find created folder", 5);
      String article = "Java (programming language)";
      mainPageObject.swipeElementToLeft(By.xpath(String.format("//*[contains(@text, '%s')]", article)),
            "Cannot find saved article");
      mainPageObject.waitForElementNotPresent(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", article)),
            "Cannot delete saved article", 10);
   }

   @Test
   public void testSaveTwoArticlesToMyListEx5() {
      mainPageObject.addArticleToReadingList("Толстой", "Leo Tolstoy");
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"), "Cannot find 'Goi it'", 5);
      mainPageObject.waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"), "Cannot find text input", 5);
      String foldersName = "Russian writers";
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            foldersName, "Cannot find text input", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text='OK']"), "Cannot press OK", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"), "Cannot find X link", 5);
      mainPageObject.addArticleToReadingList("Чехов", "Anton Chekhov");
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Russian writers']"),
            "Cannot find the folders button", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find X link", 5);
      mainPageObject.waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find 'My lists'", 5);
      mainPageObject.waitForElementAndClick(By.xpath(String.format("//*[contains(@text, '%s')]", foldersName)),
            "Cannot find created folder", 5);
      List<WebElement> listElements = mainPageObject.waitForElementsPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_action_primary']"), "Cannot find list", 10);
      listElements.get(1).click();
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/reading_list_item_remove_text']"),
            "Cannot find 'Remove from ... ' item", 5);
      mainPageObject.waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Anton Chekhov']"),
            "Cannot find 'Anton Chekhov' link", 5);
   }

   @Test
   public void testAssertTitleExistEx6() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      String articlesName = "Object-oriented programming language";
      mainPageObject.waitForElementAndClick(By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articlesName)),
            String.format("Cannot find article %s", articlesName), 5);
      mainPageObject.assertElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (programming language)']"),
            String.format("Cannot find article title '%s'", articlesName));
   }

   @Test
   public void testAmountOfNotEmptySearch() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "Linkin Park discography";
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      String searchResults = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      mainPageObject.waitForElementPresent(By.xpath(searchResults), "Cannot find anything by the request", 15);
      int amountSearchResults = mainPageObject.getAmountOfElements(By.xpath(searchResults));
      Assert.assertTrue("We found too few results", amountSearchResults > 0);

   }

   @Test
   public void testAmountOfEmptySearch() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "fngjntzj";
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      String results = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
      String resultsEmptyLabel = "//*[@text='No results found']";
      mainPageObject.waitForElementPresent(By.xpath(resultsEmptyLabel), "Cannot find empty result by the request " + searchLine, 15);
      mainPageObject.assetElementNotPresent(By.xpath(results), "We'му found some results " + searchLine);
   }

   @Test
   public void testChangeScreenOrientation() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      String searchLine = "Java";
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), searchLine, "Cannot find search field", 5);
      mainPageObject.waitForElementAndClick(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + searchLine, 15);
      String titleBeforeRotation = mainPageObject.waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find title of article", 15);
      driver.rotate(ScreenOrientation.LANDSCAPE);
      String titleAfterRotation = mainPageObject.waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find title of article", 15);
      Assert.assertEquals("Article title have been changed after scrcer rotation", titleBeforeRotation, titleAfterRotation);
      driver.rotate(ScreenOrientation.PORTRAIT);
      String titleAfterSecondRotation = mainPageObject.waitForElementAndGetAttribute(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Cannot find title of article", 15);
      Assert.assertEquals("Article title have been changed after scrcer rotation", titleBeforeRotation, titleAfterSecondRotation);
   }

   @Test
   public void testCheckSearchArticleInBackGround() {
      mainPageObject.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"), "Cannot find Search Wikipedia", 10);
      mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text, 'Search…')]"), "Java", "Cannot find search field", 5);
      mainPageObject.waitForElementPresent(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find Object-oriented programming language", 5);
      driver.runAppInBackground(3);
      mainPageObject.waitForElementPresent(By
                  .xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from background", 5);
   }
}
