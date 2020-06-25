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
}
