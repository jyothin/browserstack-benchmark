package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class W3cPage {
  
  public static final String URL = "https://www.w3.org";
  
  public static final String LOGO_IMG_ASSET = "www.w3.org/2008/site/images/logo-w3c-mobile-lg";
  
  public WebDriver driver;
  
  @FindBy(id = "w3c_mast")
  private WebElement theW3cMast;
  
  public static W3cPage visitPage(WebDriver driver) {
    W3cPage page = new W3cPage(driver);
    page.visitPage();
    return page;
  }
  
  public W3cPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public String getImgSrc() {
    WebElement img = theW3cMast.findElement(By.tagName("img"));
    return img.getAttribute("src");
  }
  
}
