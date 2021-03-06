package Saucelabs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Common.Constants;
import Pages.GooglePage;
import Pages.SeleniumPlaygroundFramePage;
import Pages.SeleniumPlaygroundPage;
import Pages.SeleniumPlaygroundTestPage;
import Pages.SeleniumPlaygroundUploadPage;
import Pages.W3cPage;

@Ignore
public class TestSuite1 extends SauceLabsParallelTestBase {
  
  private static final Logger LOGGER = Logger.getLogger(TestSuite1.class.getName());
  
  public TestSuite1(String os, String osVersion, String browser, String browserVersion, 
      String deviceName, String realMobile) {
    super(os, osVersion, browser, browserVersion, deviceName, realMobile);
  }
  
  @Test
  public void verifySeleniumCommands() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    
    SeleniumPlaygroundPage page = new SeleniumPlaygroundPage(driver);
    SeleniumPlaygroundTestPage testPage = new SeleniumPlaygroundTestPage(driver);
    SeleniumPlaygroundFramePage framePage = new SeleniumPlaygroundFramePage(driver);
    SeleniumPlaygroundUploadPage uploadPage = new SeleniumPlaygroundUploadPage(driver);
    W3cPage w3cPage = new W3cPage(driver);
    
    long startPageLoadTime = System.currentTimeMillis();
    page.visitPage();
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    
    long startTestTime = System.currentTimeMillis();
    
    String title = page.getTitle();
    assertEquals(SeleniumPlaygroundPage.TITLE, title);
    
    String currentUrl = page.getCurrentUrl();
    assertEquals(SeleniumPlaygroundPage.URL, currentUrl);
    
    page.forward(Constants.BROWSERSTACK_URL);
    WebDriverWait wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(Constants.BROWSERSTACK_TITLE));
    page.back();
    wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(SeleniumPlaygroundPage.TITLE));
    //assertTrue(page.isOnPage());
    page.forward();
    wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(Constants.BROWSERSTACK_TITLE));
    currentUrl = page.getCurrentUrl();
    assertTrue(currentUrl.contains(Constants.BROWSERSTACK_NAKED_URL));
    
    page.visitPage();
    page.setTheQ(Constants.TEST_STRING);
    page.refresh();
    String string = page.forceFindAndGetTheQ();
    assertTrue(string.isEmpty());
    
    String header = page.getTheDraggableHeader();
    assertEquals(SeleniumPlaygroundPage.DRAG_AND_DROP_TEXT, header.trim());
    
    String name = page.getTheQByName();
    assertEquals(SeleniumPlaygroundPage.Q_TEXT, name);
    
    String ui = page.getTheUiDraggableCss();
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, ui);
    
    ui = page.getTheUiDraggable();
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, ui);
    
    w3cPage.visitPage();
    String src = w3cPage.getImgSrc();
    assertTrue(src.contains(W3cPage.LOGO_IMG_ASSET));
    
    page.visitPage();
    boolean displayed = page.isTheCheeseDisplayed();
    assertTrue(displayed);
    
    displayed = page.isTheEggDisplayed();
    assertTrue(displayed);
    
    String style = page.getTheDraggableXpathStyle();
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, style);
    
    String id = page.getTheQNameById();
    assertEquals(SeleniumPlaygroundPage.Q_ID, id);
    
    String rel = page.getTheLinkByRel();
    assertEquals(SeleniumPlaygroundPage.STYLESHEET, rel);
    
    int size = page.getElementsSize();
    assertEquals(SeleniumPlaygroundPage.UL_SIZE, size);
    
    size = page.getElementElementsSize();
    assertEquals(SeleniumPlaygroundPage.UL_SIZE, size);
    
    style = page.getTheDraggableClassStyle();
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, style);
    
    name = page.getTheDraggableIdTagName();
    assertEquals(SeleniumPlaygroundPage.P, name);
    
    String fontSize = page.getTheDraggableIdFontSize();
    assertEquals(SeleniumPlaygroundPage.FONT_SIZE, fontSize);
    
    page.selectCar();
    String selected = page.getSelectedCar();
    assertEquals(SeleniumPlaygroundPage.SELECTED_TEXT, selected);
    
    try {
      page.isEnabled();
      assertTrue(false);
    } catch(NoSuchElementException e) {
      assertTrue(true);
    }
    
    String text = page.getAlertText();
    assertEquals(SeleniumPlaygroundPage.ALERT_TEXT, text);
    driver.switchTo().alert().accept();
    
    page.acceptAlert();
    assertTrue(true);
    
    page.dismissAlert();
    assertTrue(true);
    
    page.setTheQ(Constants.TEST_STRING);
    page.clearTheQ();
    String q = page.getTheQ();
    assertEquals(Constants.EMPTY, q);
    
    page.setTheQ(Constants.TEST_STRING);
    text = page.getTheQ();
    assertEquals(Constants.TEST_STRING, text);
    page.clearTheQ();
    
    boolean bTitle = page.changeTitle(Constants.TEST_STRING);
    assertTrue(bTitle);

    String windowHandle = page.getWindowHandle();
    assertTrue(windowHandle.length() != 0);
    
    ArrayList<String> windowHandles = new ArrayList<String>(page.getWindowHandles());
    if(windowHandles.size() == 1) {
      LOGGER.warning(Constants.WARNING_NO_POPUPS);
      assertTrue(true);
    } else {
      page.driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
      //assertEquals(SeleniumPlaygroundPage.NEW_WINDOW, page.driver.findElement(By.cssSelector("body")).getText());
      assertEquals("", page.driver.findElement(By.cssSelector("body")).getText());
      
    }
    
    Dimension dDize = new Dimension(Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y);
    page.setWindowSize(dDize);
    Dimension newSize = page.getWindowSize();
    page.setWindowMaximize();
    Dimension maxSize = page.getWindowSize();
    assertNotEquals(newSize, maxSize);
    
    Point oldPoint = page.getWindowPosition();
    Point newPoint = new Point(oldPoint.x+Constants.WINDOW_POS_X, oldPoint.y+Constants.WINDOW_POS_Y);
    page.setWindowPosition(newPoint);
    assertNotEquals(oldPoint, newPoint);
    
    title = page.executeScript();
    //assertEquals(SeleniumPlaygroundPage.TITLE, title);
    assertEquals("", title);
    
    title = page.executeScript();
    assertEquals(SeleniumPlaygroundPage.TITLE, title);
    
    String source = page.getPageSource();
    assertNotNull(source);
    testTime = System.currentTimeMillis() - startTestTime;
    
    page.setTheQ("Testing");
    page.switchToActiveElement();
    page.clearTheQ();
    String output = page.getTheQ();
    assertTrue(output.isEmpty());
    
    Point point = page.getTheQLocation();
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
    
    point = page.getTheQLocation();
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
    
    point = page.getTheQLocation();
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
    
    testPage.visitPage();
    String iText = testPage.moveInHoverMe();
    String oText = testPage.moveOutHoverMe();
    assertEquals(SeleniumPlaygroundTestPage.HOVER_IN, iText);
    assertEquals(SeleniumPlaygroundTestPage.HOVER_OUT, oText);
    
    page.visitPage();
    page.setTheQ(GooglePage.TITLE);
    page.actionClickAndHoldAndRelease(GooglePage.TITLE);
    title = page.getTitle();
    assertEquals(GooglePage.TITLE, title);
    
    page.setTheQ(GooglePage.TITLE);
    page.actionMoveToClick(GooglePage.TITLE);
    title = page.getTitle();
    assertEquals(GooglePage.TITLE, title);
    
    page.setTheQ(GooglePage.TITLE);
    page.actionClick(GooglePage.TITLE);
    title = page.getTitle();
    assertEquals(GooglePage.TITLE, title);
    
    Point initialLocation = page.getTheDraggableLocation();
    page.actionDragAndDrop();
    Point finalLocation = page.getTheDraggableLocation();
    page.actionDragAndDropBy(new Point(Constants.DRAG_BY_X, Constants.DRAG_BY_Y));
    Point finalLocationMoved = page.getTheDraggableLocation();
    assertNotEquals(finalLocation.x, initialLocation.x);
    assertNotEquals(finalLocation.y, initialLocation.y);
    assertEquals(finalLocation.x+Constants.DRAG_BY_X, finalLocationMoved.x);
    assertEquals(finalLocation.y+Constants.DRAG_BY_Y, finalLocationMoved.y);
    
    page.actionContextClick();
    assertTrue(true);
    
    page.actionContextClickElement();
    assertTrue(true);
    
    page.actionKeyDownAndUp();
    assertTrue(false); // intentionally set to false
    
    page.actionHoldAndRelease();
    assertTrue(false); // intentionally set to false
    
    page.actionSendKeys(Constants.TEST_STRING);
    text = page.getTheQ();
    assertEquals(Constants.TEST_STRING, text);
    
    // screenshot test
    
    long iTimestamp = System.currentTimeMillis();
    page.implicitWait();
    long oTimestamp = System.currentTimeMillis();
    long timeDelta = oTimestamp - iTimestamp;
    if(timeDelta > 3*1000 && timeDelta < 4*1000) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
    
    iTimestamp = System.currentTimeMillis();
    page.explicitWait();
    oTimestamp = System.currentTimeMillis();
    timeDelta = oTimestamp - iTimestamp;
    if(timeDelta > 3*1000 && timeDelta < 4*1000) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    ArrayList<Cookie> crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(crumbs.contains(cookie));
    
    cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    page.deleteCookie(cookie);
    crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
    
    cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    page.deleteCookieNamed(cookie);
    crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
    
    cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    page.deleteCookies();
    crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
    
    page.clickFramePage();
    framePage.swtichToFrameMenu();
    assertTrue(framePage.isClickMeDisplayed());
    
    framePage.swtichToFrameMenu();
    assertTrue(framePage.isClickMeDisplayed());
    try {
      framePage.defaultContent();
    } catch(NoSuchElementException e) {
      assertTrue(e.getMessage().contains("no such element"));
    }
    
    page.closeCurrentWindow();
    String message;
    try {
      driver.get(SeleniumPlaygroundPage.URL);
      message = "";
    } catch(Exception e) {
      message = e.getMessage();
    }
    assertTrue(message.contains("no such element"));
    
    testPage.doubleClick();
    float random = testPage.getRandomNumber();
    assertTrue(random >= 0.0 & random <= 1.0);
    
    String value = page.getLocalStorageByKey(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME);
    assertEquals(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME_VALUE, value);
    
    page.clearLocalStorage();
    value =  page.getLocalStorageByKey(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME);
    assertNull(value);
    
    page.clearLocalStorage();
    size = page.getLocalStorageSize();
    assertEquals(0, size);
    
    String values = page.printLocalStorage();
    assertTrue(
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME) & 
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME_VALUE) &
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_LASTNAME) &
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_LASTNAME_VALUE)
        );
    
    boolean visibile = testPage.isDelayTextVisible();
    assertTrue(visibile);
    
    page.selectMultipleCars();
    selected = page.getSelectedCar();
    assertTrue(selected.contains(SeleniumPlaygroundPage.VOLVO) & selected.contains(SeleniumPlaygroundPage.AUDI));
    
    uploadPage.visitPage();
    uploadPage.setFileDetector(new LocalFileDetector());
    File file = new File(Constants.UPLOAD_FILENAME);
    String body = uploadPage.uploadFile(file.getAbsolutePath());
    assertEquals(SeleniumPlaygroundUploadPage.BODY, body);
    
    
  }
  
}

