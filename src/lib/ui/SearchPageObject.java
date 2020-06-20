package lib.ui;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {
   private static final String
         SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
         SEARCH_INPUT = "//*[@resource-id='org.wikipedia:id/search_src_text']";

   public SearchPageObject(AppiumDriver driver) {
      super(driver);
   }


}
