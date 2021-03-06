package Pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OctanePage {

  public static final String URL = "https://chromium.github.io/octane/";
  
  public static final int TIME_TO_COMPLETE_TEST = 60;
  
  @FindBy(id = "main-banner")
  private WebElement mainBanner;
  
  @FindBy(id = "Box-Typescript")
  private WebElement theBoxTypescript;
  
  public WebDriver driver;
  
  public static OctanePage visitPage(WebDriver driver) {
    OctanePage page = new OctanePage(driver);
    page.visitPage();
    return page;
  }
  
  public OctanePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public void runTest() throws JavascriptException {
    JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
    jsExecutor.executeScript("Run();");
  }
  
  public void explicitWait() {
    WebDriverWait wait = new WebDriverWait(driver, TIME_TO_COMPLETE_TEST);
    wait.until(ExpectedConditions.or(
        ExpectedConditions.not(ExpectedConditions.invisibilityOf(theBoxTypescript)),
        ExpectedConditions.numberOfWindowsToBe(2)));
    ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
    if(windowHandles.size() == 2) {
      driver.switchTo().activeElement();
      driver.findElement(By.linkText("Continue")).click();
      wait.until(ExpectedConditions.not(ExpectedConditions.invisibilityOf(theBoxTypescript)));
    }
  }
  
  public String getScore() {
    return mainBanner.getText();
  }

  public LogEntries getIndividualScores() {
    return driver.manage().logs().get(LogType.BROWSER);
  }
  
}
