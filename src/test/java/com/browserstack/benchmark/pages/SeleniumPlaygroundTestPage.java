package com.browserstack.benchmark.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumPlaygroundTestPage {
  
  public WebDriver driver;
  
  public static final String URL = "http://stormy-beyond-9729.herokuapp.com/test";
  
  public static final String HOVER_IN = "Hover in";
  public static final String HOVER_OUT = "Hover out";
  
  // Something will happen after 10 secs!!
  @FindBy(id = "delaytext")
  private WebElement theDelayText;
  
  // Hidden field
  
  // Hidden element
  
  // Disabled element
  
  // Alert boxes
  
  // Double click
  @FindBy(id = "doubleclick")
  private WebElement theDoubleClick;
  
  @FindBy(id = "random1")
  private WebElement theRandom1;
  
  // Right click
  
  // Click and hold event
  
  // Hover demo
  @FindBy(id = "hovertext")
  private WebElement theHoverText;
  
  @FindBy(id = "hoverme")
  private WebElement theHoverMe;


  public static SeleniumPlaygroundTestPage visitPage(WebDriver driver) {
    SeleniumPlaygroundTestPage page = new SeleniumPlaygroundTestPage(driver);
    page.visitPage();
    return page;
  }

  public SeleniumPlaygroundTestPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  // Something will happen after 10 secs!!
  public boolean isDelayTextVisible() {
    WebDriverWait wait = new WebDriverWait(driver, 15);
    return wait.until(ExpectedConditions.visibilityOf(this.theDelayText)).isDisplayed();
  }
  
  // Hidden field
  
  // Hidden element
  
  // Disabled element
  
  // Alert boxes
  
  // Double click
  public void doubleClick() {
    Actions actions = new Actions(driver);
    actions.doubleClick(this.theDoubleClick).build().perform();
  }
  
  public float getRandomNumber() {
    String text = this.theRandom1.getText();
    return Float.parseFloat(text.substring(23, text.length()-1));
  }
  
  // Right click
  
  // Click and hold event
  
  // Hover demo
  
  public String moveInHoverMe() {
    Actions action = new Actions(driver);
    action.moveToElement(this.theHoverMe).build().perform();
    return this.theHoverText.getText();
  }
  
  public String moveOutHoverMe() {
    Actions action = new Actions(driver);
    action.moveToElement(this.theHoverText).build().perform();
    return this.theHoverText.getText();
  }
}
