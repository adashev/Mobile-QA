package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
   private static final String
         FOLDER_BY_NAME_TPL = "//*[contains(@text, '{FOLDER_NAME}')]",
         ARTICLE_BY_TITLE_TPL = "//*[contains(@text, '{TITLE}')]";

   private static String getFolderXpathByName(String foldersName) {
      return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", foldersName);
   }

   private static String getSavedArticleXpathByTitle(String article) {
      return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article);
   }

   public MyListsPageObject(AppiumDriver driver) {
      super(driver);
   }

   public void openFolderByName(String foldersName) {
      this.waitForElementAndClick(By.xpath(getFolderXpathByName(foldersName)),
            "Cannot find folder by name" + foldersName, 5);
   }

   public void waitForArticleToDisappearByTitle(String article) {
      String xpath = getSavedArticleXpathByTitle(article);
      this.waitForElementNotPresent(By.xpath(xpath),
            "Saved article still present with title" + article, 15);
   }

   public void waitForArticleToAppearByTitle(String article) {
      this.waitForElementPresent(By.xpath(getSavedArticleXpathByTitle(article)),
            "Saved find saved article by title" + article, 15);
   }

   public void swipeByArticleToDelete(String article) throws InterruptedException {
      this.waitForArticleToAppearByTitle(article);
      this.swipeElementToLeft(By.xpath(getSavedArticleXpathByTitle(article)),
            "Cannot find saved article");
      Thread.sleep(1500);
      this.waitForArticleToDisappearByTitle(article);
   }
}
