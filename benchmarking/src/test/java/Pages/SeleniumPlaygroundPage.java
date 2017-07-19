package Pages;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumPlaygroundPage {

  public static final String URL = "http://stormy-beyond-9729.herokuapp.com/";
  public static final String TITLE = "Selenium playground";
  public static final String STYLESHEET = "stylesheet";
  
  public static final String NEW_WINDOW = "You have opened a new window";
  public static final String ALERT_TEXT = "Welcome to selenium playground";
  
  public static final String DRAG_AND_DROP_TEXT = "Drag & Drop";
  public static final String DRAGGABLE_POSITION = "position: relative;";
  
  public static final String P = "p";
  public static final String FONT_SIZE = "16px";
  
  public static final int UL_SIZE = 5;
  
  public static final String Q_ID = "q";
  public static final String Q_TEXT = "post[title]";
  
  public static final String VOLVO = "Volvo";
  public static final String AUDI = "Audi";
  public static final String SELECTED_TEXT = "Audi";
  
  public static final String LOCAL_STORAGE_KEY_FIRSTNAME = "firstname";
  public static final String LOCAL_STORAGE_KEY_FIRSTNAME_VALUE = "Jason";
  public static final String LOCAL_STORAGE_KEY_LASTNAME = "lastname";
  public static final String LOCAL_STORAGE_KEY_LASTNAME_VALUE = "Bourne";

  public WebDriver driver;

  @FindBy(tagName = "link")
  private WebElement theLink;
  
  // test page
  @FindBy(linkText = "Test page")
  private WebElement theTestLink;

  // Frame page
  @FindBy(linkText = "Frame page")
  private WebElement theFrameLink;

  // openModal
  @FindBy(id = "openModal")
  private WebElement theOpenModelLink;

  // Drag & Drop
  @FindBy(css = "h2")
  private WebElement theDraggableHeader;
  
  @FindBy(css = ".ui-draggable")
  private WebElement theUiDraggableCss;
  
  @FindBy(className = "ui-draggable")
  private WebElement theUiDraggable;
  
  @FindBy(xpath = "//div/p")
  private WebElement theDraggableXpath;
  
  @FindBy(className = "ui-widget-content")
  private WebElement theDraggableClass;
  
  @FindBy(id = "draggable")
  private WebElement theDraggableId;

  @FindBy(id = "navcontainer")
  private WebElement theNavContainer;
  
//  @FindBy(id = "active")
//  private WebElement theActive;
  
  @FindBy(linkText = "Milk")
  private WebElement theMilk;
  
  @FindBy(linkText = "Eggs")
  private WebElement theEggs;
  
  @FindBy(linkText = "Cheese")
  private WebElement theCheese;
  
  @FindBy(partialLinkText = "Eg")
  private WebElement theEg;

  // Change page title
  @FindBy(id = "q")
//  @CacheLookup
  private WebElement theQ;
  
  @FindBy(name = "post[title]")
  private WebElement theQName;
  
  //@FindBy(cssSelector = "input[type'submit']")
  @FindBy(css = "input[type='submit']")
  private WebElement theSubmit;

  // Multiple select
  @FindBy(id = "getcars")
  private WebElement theGetCars;
  
  @FindBy(id = "selectedcars")
  private WebElement theSelectedCar;
  
  @FindBy(id = "multiplecars")
  private WebElement theMultipleCars;
  
  @FindBy(css = "select.select-box")
  private WebElement selectBox;
  
  @FindBy(css = "#multiplecars option[value='volvo']")
  private WebElement theVolvo;
  
  @FindBy(css = "#multiplecars option[value='volvo']")
  private WebElement theAudi;
  
  //HTML Local Storage
  @FindBy(id = "printstorage")
  private WebElement thePrintStorage;
  
  @FindBy(id = "storageval")
  private WebElement theStorageVal;
  
  //Ordered List Example
  
  //Unordered List Example
  
  //Test Form
  
  //TBD: Where does this fit?
//  @FindBy(id = "alert")
//  @CacheLookup
//  private WebElement theAlert;

  public static SeleniumPlaygroundPage visitPage(WebDriver driver) {
    SeleniumPlaygroundPage page = new SeleniumPlaygroundPage(driver);
    page.visitPage();
    return page;
  }

  public SeleniumPlaygroundPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void visitPage() {
    driver.get(URL);
  }

  public String getTitle() {
    return driver.getTitle();
  }
  
  public boolean isOnPage() {
    return driver.getTitle() == SeleniumPlaygroundPage.TITLE;
  }

  public String getCurrentUrl() {
    return driver.getCurrentUrl();
  }
  
  public void forward(String url) {
    driver.get(url);
  }
  
  public void forward() {
    driver.navigate().forward();
  }
  
  public void back() {
    driver.navigate().back();
  }
  
  public String getTheLinkByRel() {
    return theLink.getAttribute("rel");
  }
  
  public String getAlertText() {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("alert('" +ALERT_TEXT+ "');");
    return driver.switchTo().alert().getText();
  }
  
  public void acceptAlert() {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("alert('" +ALERT_TEXT+ "');");
    driver.switchTo().alert().accept();
  }
  
  public void dismissAlert() {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    jsExecutor.executeScript("alert('" +ALERT_TEXT+ "');");
    driver.switchTo().alert().dismiss();
  }
  
  public String getWindowHandle() {
    return driver.getWindowHandle();
  }
  public void setWindowSize(Dimension size) {
    driver.manage().window().setSize(size);
  }
  
  public Dimension getWindowSize() {
    return driver.manage().window().getSize();
  }
  
  public void setWindowMaximize() {
    driver.manage().window().maximize();
  }
  
  public Point getWindowPosition() {
    return driver.manage().window().getPosition();
  }
  
  public void setWindowPosition(Point point) {
    driver.manage().window().setPosition(point);
  }
  
  public String executeScript() {
    return (String) ((JavascriptExecutor)(driver)).executeScript("return document.title;");
  }
  
  public String getPageSource() {
    return (String) driver.getPageSource();
  }
  
  public void switchToActiveElement() {
    driver.switchTo().activeElement();
  }
  
  public void actionClickAndHoldAndRelease(String title) {
    Actions action = new Actions(driver);
    //action.clickAndHold(theSubmit).build().perform();
    action.moveToElement(theSubmit).clickAndHold().release().build().perform();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.titleIs(title));
  }
  
  public void actionMoveToClick(String title) {
    Actions action = new Actions(driver);
    action.moveToElement(theSubmit).click().build().perform();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.titleIs(title));
  }

  public void actionClick(String title) {
    Actions action = new Actions(driver);
    action.click(theQ).build().perform();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.titleIs(title));
  }
  
  public void actionDragAndDrop() {
    Actions action = new Actions(driver);
    action.dragAndDrop(theDraggableId, theQ).build().perform();
  }
  
  public void actionDragAndDropBy(Point location) {
    Actions action = new Actions(driver);
    action.dragAndDropBy(theDraggableId, location.x, location.y).build().perform();
  }
  
  public void actionContextClick() {
    Actions action = new Actions(driver);
    action.contextClick().build().perform();
  }
  
  public void actionContextClickElement() {
    Actions action = new Actions(driver);
    action.contextClick(theQ).build().perform();
  }
  
  public void actionKeyDownAndUp() {
    Actions action = new Actions(driver);
    action.keyDown(Keys.CONTROL)
      .click(theMilk)
      .click(theEggs)
      .keyUp(Keys.CONTROL)
      .build()
      .perform();
  }
  
  public void actionHoldAndRelease() {
    Actions action = new Actions(driver);
    action.clickAndHold(theDraggableId)
      .moveToElement(theQ)
      .release()
      .build()
      .perform();
  }
  
  public void actionSendKeys(String text) {
    Actions action = new Actions(driver);
    action.sendKeys(theQ, text).build().perform();
  }
  
  public void refresh() {
    driver.navigate().refresh();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState")
            .toString().equals("complete");
      }
    };
    wait.until(jsLoad);
  }
  
  public void implicitWait() {
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  }
  
  public void explicitWait() {
    WebDriverWait wait = new WebDriverWait(driver, 3);
    wait.withTimeout(4, TimeUnit.SECONDS);
  }
  
  public void addCookie(Cookie cookie) {
    driver.manage().addCookie(cookie);
  }
  
  public Cookie getCookie(String name) {
    return driver.manage().getCookieNamed(name);
  }
  
  public Set<Cookie> getCookies() {
    return driver.manage().getCookies();
  }
  
  public void deleteCookie(Cookie cookie) {
    driver.manage().deleteCookie(cookie);
  }
  
  public void deleteCookieNamed(Cookie cookie) {
    driver.manage().deleteCookieNamed(cookie.getName());
  }
  
  public void deleteCookies() {
    driver.manage().deleteAllCookies();
  }
  
  public void closeCurrentWindow() {
    driver.close();
  }
  
  public String getLocalStorageByKey(String key) {
    JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
    return jsExecutor.executeScript("window.localStorage.getItem('" + key + "');").toString();
  }
  
  public void clearLocalStorage() {
    JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
    jsExecutor.executeScript("window.localStorage.clear();");
  }
  
  public int getLocalStorageSize() {
    JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
    String size = jsExecutor.executeScript("window.localStorage.length;").toString();
    return Integer.parseInt(size);
  }
  
  public boolean isEnabled() throws NoSuchElementException {
    return selectBox.isEnabled();
  }
  
  // testPage
  // framePage
  public void clickFramePage() {
    theFrameLink.click();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.titleIs(SeleniumPlaygroundFramePage.TITLE));
  }
  
  // openModal
  public Set<String> getWindowHandles() {
    theOpenModelLink.click();
    return driver.getWindowHandles();
  }
  
  // Drag & Drop
  public String getTheDraggableHeader() {
    return theDraggableHeader.getText();
  }
  
  public String getTheUiDraggableCss() {
    return theUiDraggableCss.getAttribute("style");
  }
  
  public String getTheUiDraggable() {
    return theUiDraggable.getAttribute("style");
  }
  
  public String getTheDraggableXpathStyle() {
    return theDraggableXpath.getAttribute("style");
  }
  
  public String getTheDraggableClassStyle() {
    return driver.findElement(By.className("ui-widget-content")).findElement(By.id("draggable")).getAttribute("style");
  }
  
  public String getTheDraggableIdTagName() {
    return theDraggableId.getTagName();
  }
  
  public String getTheDraggableIdFontSize() {
    return theDraggableId.getCssValue("font-size");
  }
  
  public Point getTheDraggableLocation() {
    return theDraggableId.getLocation();
  }
  
  //Multiple Elements
//  public boolean isTheActiveDisplayed() {
//    return theNavContainer.isDisplayed() & theActive.isDisplayed();
//  }
  
  public boolean isTheCheeseDisplayed() {
    return theCheese.isDisplayed();
  }
  
  public boolean isTheEggDisplayed() {
    return theEg.isDisplayed();
  }
  
  public int getElementsSize() {
    return driver.findElements(By.cssSelector("#navcontainer ul li")).size();
  }
  
  public int getElementElementsSize() {
    return driver.findElement(By.id("navcontainer")).findElements(By.cssSelector("ul li")).size();
  }
  
  // Change page title
  public void setTheQ(String text) {
    theQ.sendKeys(text);
  }
  
  public String getTheQ() {
    return theQ.getAttribute("value");
  }
  
  public String forceFindAndGetTheQ() {
    return driver.findElement(By.id("q")).getAttribute("value");
  }
  
  public void clearTheQ() {
    theQ.clear();
  }
  
  public String getTheQByName() {
    return theQ.getAttribute("name");
  }
  
  public String getTheQNameById() {
    return theQName.getAttribute("id");
  }

  public boolean changeTitle(String text) {
    theQ.sendKeys(text);
    theQ.submit();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    return wait.until(ExpectedConditions.titleIs(text));
  }
  
  public Point getTheQLocation() {
    return theQ.getLocation();
  }
  
  // Multiple select
  public String getSelectedCar() {
    return theSelectedCar.getText();
  }
  
  public void selectCar() {
    theMultipleCars.findElement(By.cssSelector("option[value='audi']")).click();
    theGetCars.click();
  }
  
  public void selectMultipleCars() {
    Actions action = new Actions(driver);
    action.keyDown(Keys.CONTROL).build().perform();
    theVolvo.click();
    theAudi.click();
    action = new Actions(driver);
    action.keyUp(Keys.CONTROL).build().perform();
    theGetCars.click();
  }
  
  //HTML Local Storage
  public String printLocalStorage() {
    thePrintStorage.click();
    return theStorageVal.getText();
  }
  
  //Ordered List Example
  
  //Unordered List Example
  
  //Test Form
  
}
