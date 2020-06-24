package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

   public ArticlePageObject(AppiumDriver driver) {
      super(driver);
   }

   public WebElement waitForTitleElement() {
      return this
            .waitForElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"), "Cannot find article title on page", 15);
   }
}
