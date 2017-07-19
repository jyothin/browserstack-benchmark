package Pages;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumPlaygroundUploadPage {
  
  public static final Logger LOGGER = Logger.getLogger(SeleniumPlaygroundUploadPage.class.getName());
  
  public static final String URL = "http://stormy-beyond-9729.herokuapp.com/upload";
  
  public static final String BODY = "Hello World!";
  
  public WebDriver driver;
  
  @FindBy(css = "input[name='myfile']")
  private WebElement theMyFile;
  
  @FindBy(css = "input[type='submit']")
  private WebElement theSubmit;

  @FindBy(tagName = "body")
  private WebElement body;
  
  public static SeleniumPlaygroundUploadPage visitPage(WebDriver driver) {
    SeleniumPlaygroundUploadPage page = new SeleniumPlaygroundUploadPage(driver);
    page.visitPage();
    return page;
  }
  
  public SeleniumPlaygroundUploadPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public String uploadFile(String path) {
    theMyFile.sendKeys(path);
    theSubmit.click();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.visibilityOf(body));
    return body.getText();
  }
  
  public void setFileDetector(FileDetector detector) {
    RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
    remoteWebDriver.setFileDetector(detector);
  }

}
