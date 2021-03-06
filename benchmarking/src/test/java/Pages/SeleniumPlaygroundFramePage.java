package Pages;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumPlaygroundFramePage {
  
  public static final Logger LOGGER = Logger.getLogger(SeleniumPlaygroundFramePage.class.getName());
  
  public static final String URL = "http://stormy-beyond-9729.herokuapp.com/frame";
  public static final String TITLE = "Frame";
  public static final String MENU = "menu";
  
  public WebDriver driver;
  
  @FindBy(linkText = "Click me")
  private WebElement theClickMe;
  
  public static SeleniumPlaygroundFramePage visitPage(WebDriver driver) {
    SeleniumPlaygroundFramePage page = new SeleniumPlaygroundFramePage(driver);
    page.visitPage();
    return page;
  }
  
  public SeleniumPlaygroundFramePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public void swtichToFrameMenu() {
    driver.switchTo().frame(MENU);
  }
  
  public boolean isClickMeDisplayed() {
    return this.theClickMe.isDisplayed();
  }
  
  public void defaultContent() {
    driver.switchTo().defaultContent();
  }
  
}

