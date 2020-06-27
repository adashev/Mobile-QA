package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
   private static final String
         TITLE = "//*[@resource-id='org.wikipedia:id/view_page_title_text']",
         FOOTER = "//*[@text='View page in browser']",
         OPRTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
         ADD_TO_LIST = "//*[contains(@text, 'Add to reading list')]",
         ADD_TO_LIST_OVERLAY = "//*[@resource-id='org.wikipedia:id/onboarding_button']",
         MY_LIST_NAME_INPUT = "//*[@resource-id='org.wikipedia:id/text_input']",
         OK_BUTTON = "//*[@resource-id='android:id/button1'][@text='OK']",
         CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

   public ArticlePageObject(AppiumDriver driver) {
      super(driver);
   }

   public WebElement waitForTitleElement() {
      return this
            .waitForElementPresent(By.xpath(TITLE), "Cannot find article title on page", 15);
   }

   public String getArticleTitle() {
      WebElement element = waitForTitleElement();
      return element.getText();
   }

   public void swipeToFooter() {
      this.swipeUpToFindElement(By.xpath(FOOTER), "Cannot find the end of the article", 20);
   }

   public void addArticleToMyList(String foldersName) {
      this.waitForElementAndClick(By.xpath(OPRTIONS_BUTTON),
            "Cannot find button to open article options", 5);
      this.waitForElementAndClick(By.xpath(ADD_TO_LIST),
            "Cannot find option", 5);
      this.waitForElementAndClick(By.xpath(ADD_TO_LIST_OVERLAY),
            "Cannot find 'Goi it'", 5);
      this.waitForElementAndClear(By.xpath(MY_LIST_NAME_INPUT),
            "Cannot find text input", 5);
      this.waitForElementAndSendKeys(By.xpath(MY_LIST_NAME_INPUT),
            foldersName, "Cannot find text input", 5);
      this.waitForElementAndClick(By.xpath(OK_BUTTON),
            "Cannot press OK", 5);
   }

   public void closeArticle() {
      this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON), "Cannot find X link", 5);
   }
}

