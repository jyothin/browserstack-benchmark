package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GooglePage {
  
  public static final String URL = "https://www.google.com";
  public static final String TITLE = "Google";
  
  public WebDriver driver;
  
  public static GooglePage visitPage(WebDriver driver) {
    GooglePage page = new GooglePage(driver);
    page.visitPage();
    return page;
  }
  
  public GooglePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public String getTitle() {
    return driver.getTitle();
  }
  
}
