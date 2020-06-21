package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
   private static final String
         SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
         SEARCH_CANCEL_BUTTON = "//*[@resource-id='org.wikipedia:id/search_close_btn']",
         SEARCH_INPUT = "//*[@resource-id='org.wikipedia:id/search_src_text']",
         SEARCH_RESULT_BY_SUBSTR_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTR}']";

   public SearchPageObject(AppiumDriver driver) {
      super(driver);
   }

   private static String getSearchResultElement(String substring) {
      return SEARCH_RESULT_BY_SUBSTR_TPL.replace("{SUBSTR}", substring);
   }

   public void waitForCancelButtonToAppear() {
      this.waitForElementPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
   }

   public void waitForCancelButtonToDisappear() {
      this.waitForElementNotPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
   }

   public void initSearchInput() {
      this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find Search Wikipedia", 5);
      this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find input after clicking search init element");
   }

   public void typeSearchLine(String searchLine) {
      this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input", 5);
   }

   public void waitForSearchResult(String substring) {
      String searchResultXpath = getSearchResultElement(substring);
      this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring " + substring);
   }

}
