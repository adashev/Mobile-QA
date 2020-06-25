package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
   private static final String TITLE = "//*[@resource-id='org.wikipedia:id/view_page_title_text']";
   private static final String FOOTER = "//*[@text='View page in browser']";

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
      this.waitForElementAndClick(By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options", 5);
      this.waitForElementAndClick(By.xpath("//*[contains(@text, 'Add to reading list')]"),
            "Cannot find option", 5);
      this.waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button']"),
            "Cannot find 'Goi it'", 5);
      this.waitForElementAndClear(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            "Cannot find text input", 5);
      this.waitForElementAndSendKeys(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            foldersName, "Cannot find text input", 5);
      this.waitForElementAndClick(By.xpath("//*[@resource-id='android:id/button1'][@text='OK']"),
            "Cannot press OK", 5);
   }
}
