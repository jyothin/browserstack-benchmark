package Saucelabs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Common.Constants;
import Pages.ExamplePage;
import Pages.GooglePage;
import Pages.SeleniumPlaygroundFramePage;
import Pages.SeleniumPlaygroundPage;
import Pages.SeleniumPlaygroundTestPage;
import Pages.SeleniumPlaygroundUploadPage;
import Pages.W3cPage;
import net.jcip.annotations.NotThreadSafe;

//@Ignore
@NotThreadSafe
public class TestSuite2 extends SauceLabsParallelTestBase {
  
  private static final Logger LOGGER = Logger.getLogger(TestSuite2.class.getName());
  
  public TestSuite2(String os, String osVersion, String browser, String browserVersion, 
      String deviceName, String realMobile) {
    super(os, osVersion, browser, browserVersion, deviceName, realMobile);
  }
  
  private SeleniumPlaygroundPage pageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    SeleniumPlaygroundPage page = SeleniumPlaygroundPage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }
  
  private SeleniumPlaygroundFramePage framePageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    SeleniumPlaygroundFramePage page = SeleniumPlaygroundFramePage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }
  
  private SeleniumPlaygroundTestPage testPageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    SeleniumPlaygroundTestPage page = SeleniumPlaygroundTestPage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }
  
  private SeleniumPlaygroundUploadPage uploadPageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    SeleniumPlaygroundUploadPage page = SeleniumPlaygroundUploadPage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }
  
  private W3cPage w3cPageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    W3cPage page = W3cPage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }
  
  public ExamplePage examplePageLoad() {
    long startPageLoadTime = System.currentTimeMillis();
    ExamplePage page = ExamplePage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    return page;
  }

  /**
   * Runs a simple test verifying the page title.
   * @throws InvalidElementStateException
   */
  @Test
  public void verifyTitleTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String title = page.getTitle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.TITLE, title);
  }
  
  @Test
  public void verifyCurrentUrlTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String currentUrl = page.getCurrentUrl();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.URL, currentUrl);
  }
  
  @Test
  @Ignore("Error in test")
  public void verifyBackForwardTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.forward(Constants.BROWSERSTACK_URL);
    WebDriverWait wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(Constants.BROWSERSTACK_TITLE));
    //assertTrue(page.getCurrentUrl().contains("www.browserstack.com"));
    page.back();
    wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(SeleniumPlaygroundPage.TITLE));
    //assertTrue(page.isOnPage());
    page.forward();
    wait = new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.titleIs(Constants.BROWSERSTACK_TITLE));
    String currentUrl = page.getCurrentUrl();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(currentUrl.contains(Constants.BROWSERSTACK_NAKED_URL));
  }
  
  @Test
  public void verifyRefreshTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    page.setTheQ(Constants.TEST_STRING);
    
    long startTestTime = System.currentTimeMillis();
    page.refresh();
    testTime = System.currentTimeMillis() - startTestTime;
    
    String string = page.forceFindAndGetTheQ();
    assertTrue(string.isEmpty());
  }
  
  @Test
  public void verifyDisplayTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String header = page.getTheDraggableHeader();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.DRAG_AND_DROP_TEXT, header.trim());
  }
  
  @Test
  public void verifyGetElementByIdTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String name = page.getTheQByName();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.Q_TEXT, name);
  }
  
  @Test
  public void verifyGetElementByCssTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String ui = page.getTheUiDraggableCss();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, ui);
  }
  
  @Test
  public void verifyGetElementByClassNameTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String ui = page.getTheUiDraggable();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, ui);
  }
  
  @Test
  public void verifyGetImgSrcTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    W3cPage page = w3cPageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String src = page.getImgSrc();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(src.contains(W3cPage.LOGO_IMG_ASSET));
  }
  
  @Test
  public void verifyGetElementByLinkTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    boolean displayed = page.isTheCheeseDisplayed();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(displayed);
  }
  
  @Test
  public void verifyGetElementByPartialLinkTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    boolean displayed = page.isTheEggDisplayed();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(displayed);
  }
  
  @Test
  public void verifyGetElementByXpathTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String style = page.getTheDraggableXpathStyle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, style);
  }
  
  @Test
  public void verifyGetElementByNameTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String id = page.getTheQNameById();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.Q_ID, id);
  }
  
  @Test
  public void verifyGetElementByTagNameTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String rel = page.getTheLinkByRel();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.STYLESHEET, rel);
  }
  
  @Test
  public void verifyGetElementsTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    int size = page.getElementsSize();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.UL_SIZE, size);
  }
  
  @Test
  public void verifyGetElementElementsTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    int size = page.getElementElementsSize();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.UL_SIZE, size);
  }
  
  @Test
  public void verifyGetElementElementTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String style = page.getTheDraggableClassStyle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.DRAGGABLE_POSITION, style);
  }
  
  @Test
  public void verifyGetTagTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String name = page.getTheDraggableIdTagName();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.P, name);
  }
  
  @Test
  public void verifyGetCssValueTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String fontSize = page.getTheDraggableIdFontSize();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.FONT_SIZE, fontSize);
  }
  
  @Test
  public void verifyIsSelectedTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.selectCar();
    testTime = System.currentTimeMillis() - startTestTime;
    
    String selected = page.getSelectedCar();
    assertEquals(SeleniumPlaygroundPage.SELECTED_TEXT, selected);
  }
  
//  @Ignore("TBD: Fix assertion")
  @Test(expected = NoSuchElementException.class)
  public void verifyIsEnabledTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    boolean enabled = page.isEnabled(); 
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertFalse(enabled);
  }
  
  @Test
  public void verifyAlertTextTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String text = page.getAlertText();
    testTime = System.currentTimeMillis() - startTestTime;
    
    page.driver.switchTo().alert().accept();
    assertEquals(SeleniumPlaygroundPage.ALERT_TEXT, text);
  }
  
  @Test
  public void verifyAlertAcceptTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = SeleniumPlaygroundPage.visitPage(driver);
    
    long startTestTime = System.currentTimeMillis();
    page.acceptAlert();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(true);
  }
  
  @Test
  public void verifyAlertDismissTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.dismissAlert();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(true);
  }
  
  @Test
  public void verifySendKeysClearTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();

    long startTestTime = System.currentTimeMillis();
    page.setTheQ(Constants.TEST_STRING);
    page.clearTheQ();
    String q = page.getTheQ();
    testTime = System.currentTimeMillis() - startTestTime;

    assertEquals(Constants.EMPTY, q);
  }
  
  @Test
  public void verifySendKeysTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.setTheQ(Constants.TEST_STRING);
    String text = page.getTheQ();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(Constants.TEST_STRING, text);
  }

  @Test
  public void verifyChangeTitleTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    boolean bTitle = page.changeTitle(Constants.TEST_STRING);
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(bTitle);
  }
  
  @Test
  public void verifyWindowHandleTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String windowHandle = page.getWindowHandle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(windowHandle.length() != 0);
  }
  
  @Test
  public void verifyWindowHandlesTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    ArrayList<String> windowHandles = new ArrayList<String>(page.getWindowHandles());
    testTime = System.currentTimeMillis() - startTestTime;
    
    LOGGER.info(Integer.toString(windowHandles.size()));
    if(windowHandles.size() == 1) {
      LOGGER.warning(Constants.WARNING_NO_POPUPS);
      assertTrue(true);
    } else {
      page.driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
      assertEquals(SeleniumPlaygroundPage.NEW_WINDOW, page.driver.findElement(By.cssSelector("body")).getText());
    }
  }

  @Test
  public void verifyWindowSizeAndMaximizeTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Dimension dSize = new Dimension(Constants.WINDOW_SIZE_X, Constants.WINDOW_SIZE_Y);
    long startTestTime = System.currentTimeMillis();
    page.setWindowSize(dSize);
    Dimension newSize = page.getWindowSize();
    page.setWindowMaximize();
    Dimension maxSize = page.getWindowSize();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertNotEquals(newSize, maxSize);
  }
  
  @Test
  public void verifyWindowPositionTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Point oldPoint = page.getWindowPosition();
    Point newPoint = new Point(oldPoint.x+Constants.WINDOW_POS_X, oldPoint.y+Constants.WINDOW_POS_Y);
    long startTestTime = System.currentTimeMillis();
    page.setWindowPosition(newPoint);
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertNotEquals(oldPoint, newPoint);
  }
  
  @Test
  public void verifyExecuteScriptTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String title = page.executeScript();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundPage.TITLE, title);
  }
  
  @Test
  public void verifyPageSourceTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String source = page.getPageSource();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertNotNull(source);
  }
  
  @Test
  public void verifyActiveElementTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.setTheQ("Testing");
    page.switchToActiveElement();
    page.clearTheQ();
    String output = page.getTheQ();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(output.isEmpty());
  }
  
  @Test
  public void verifyElementLocationTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    Point point = page.getTheQLocation();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
  }
  
  @Test
  public void verifyElementSizeTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    Point point = page.getTheQLocation();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
  }
  
  @Test
  public void verifyElementMoveTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    Point point = page.getTheQLocation();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(point.x > 0);
    assertTrue(point.y > 0);
  }
  
  @Test
  public void verifyElementMoveInTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundTestPage page = testPageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String iText = page.moveInHoverMe();
    String oText = page.moveOutHoverMe();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundTestPage.HOVER_IN, iText);
    assertEquals(SeleniumPlaygroundTestPage.HOVER_OUT, oText);
  }
  
  @Test
  public void verifyClickAndHoldAndReleaseTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.setTheQ(GooglePage.TITLE);
    page.actionClickAndHoldAndRelease(GooglePage.TITLE);
    String title = page.getTitle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(GooglePage.TITLE, title);
  }
  
  @Test
  public void verifyActionMoveToClickTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.setTheQ(GooglePage.TITLE);
    page.actionMoveToClick(GooglePage.TITLE);
    String title = page.getTitle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(GooglePage.TITLE, title);
  }
  
  @Test
  public void verifyActionClickTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.setTheQ(GooglePage.TITLE);
    page.actionClick(GooglePage.TITLE);
    String title = page.getTitle();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(GooglePage.TITLE, title);
  }
  
  @Test
  public void verifyDragAndDropTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    Point initialLocation = page.getTheDraggableLocation();
    page.actionDragAndDrop();
    Point finalLocation = page.getTheDraggableLocation();
    page.actionDragAndDropBy(new Point(Constants.DRAG_BY_X, Constants.DRAG_BY_Y));
    Point finalLocationMoved = page.getTheDraggableLocation();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertNotEquals(finalLocation.x, initialLocation.x);
    assertNotEquals(finalLocation.y, initialLocation.y);
    assertEquals(finalLocation.x+Constants.DRAG_BY_X, finalLocationMoved.x);
    assertEquals(finalLocation.y+Constants.DRAG_BY_Y, finalLocationMoved.y);
  }
  
  @Test
  public void verifyContextClickTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.actionContextClick();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(true);
  }
  
  @Test
  public void verifyContextClickElementTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.actionContextClickElement();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(true);
  }

//  @Ignore("TBD: failed")
  @Test
  public void verifyKeyDownAndUpTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.actionKeyDownAndUp();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(false); // intentionally set to false
  }
  
//  @Ignore("TBD: failed")
  @Test
  public void verifyHoldAndReleaseTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.actionHoldAndRelease();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(false); // intentionally set to false
  }
  
  @Test
  public void verifyActionSendKeysTest() throws InvalidElementStateException {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.actionSendKeys(Constants.TEST_STRING);
    String text = page.getTheQ();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(Constants.TEST_STRING, text);
  }
  
  @Test
  public void verifyScreenshotTest() throws InvalidElementStateException {
    boolean result = false;
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    ExamplePage page = examplePageLoad();
    
    long startTestTime = System.currentTimeMillis();
    File screenshot = page.saveScreenshot(new Dimension(Constants.SCREENSHOT_SIZE_W, Constants.SCREENSHOT_SIZE_H));
    testTime = System.currentTimeMillis() - startTestTime;
    
    BufferedImage bScreenshot = null;
    DataBuffer dScreenshot = null;
    int sizeScreenshot = -1;
    try {
      bScreenshot = ImageIO.read(screenshot);
      dScreenshot = bScreenshot.getData().getDataBuffer();
      sizeScreenshot = dScreenshot.getSize();
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
      //e.printStackTrace();
      result = false;
    }
    
    BufferedImage bRefScreenshot;
    DataBuffer dRefScreenshot = null;
    int sizeRefScreenshot = -1;
    try {
      LOGGER.info(page.getRefScreenshot().getPath());
      bRefScreenshot = ImageIO.read(page.getRefScreenshot());
      dRefScreenshot = bRefScreenshot.getData().getDataBuffer();
      sizeRefScreenshot = dRefScreenshot.getSize();
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
      //e.printStackTrace();
      result = false;
    }
    
    if(sizeScreenshot >= 0 && sizeRefScreenshot >= 0 && sizeScreenshot == sizeRefScreenshot &&
        dScreenshot != null && dRefScreenshot != null) {
      for(int i=0; i<sizeRefScreenshot; i++) {
        if(dRefScreenshot.getElem(i) != dScreenshot.getElem(i)) {
          result = false;
          break;
        }
      }
      result = true;
    } else {
      result = false;
    }
    
    assertTrue(result);
    
  }

//  @Ignore("TBD: false")
  @Test
  public void verifyImplicitWaitTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.implicitWait();
    testTime = System.currentTimeMillis() - startTestTime;
    
    if(testTime > 3*1000 && testTime < 4*1000) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
//    assertTrue(testTime > (3 * 1000) & testTime < (4 * 1000));
    LOGGER.info(Long.toString(testTime));
  }
  
//  @Ignore("TBD: false")
  @Test
  public void verifyExplicitWaitTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.explicitWait();
    testTime = System.currentTimeMillis() - startTestTime;
    
    if(testTime > 3*1000 && testTime < 4*1000) {
      assertTrue(true);
    } else {
      assertTrue(false);
    }
//    assertTrue(testTime > (3 * 1000) && testTime < (4 * 1000));
    LOGGER.info(Long.toString(testTime));
  }
  
  @Test
  public void verifyAddCookieAndGetCookieTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    
    long startTestTime = System.currentTimeMillis();
    page.addCookie(cookie);
    Cookie crumb = page.getCookie(cookie.getName());
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(cookie.getValue(), crumb.getValue());
  }
  
  @Test
  public void verifyGetCookiesTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    
    long startTestTime = System.currentTimeMillis();
    ArrayList<Cookie> crumbs = new ArrayList<Cookie>(page.getCookies());
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(crumbs.contains(cookie));
  }
  
  @Test
  public void verifyDeleteCookieTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    
    long startTestTime = System.currentTimeMillis();
    page.deleteCookie(cookie);
    testTime = System.currentTimeMillis() - startTestTime;
    
    ArrayList<Cookie> crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
  }
  
  @Test
  public void verifyDeleteCookieNamedTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    
    long startTestTime = System.currentTimeMillis();
    page.deleteCookieNamed(cookie);
    testTime = System.currentTimeMillis() - startTestTime;
    
    ArrayList<Cookie> crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
  }
  
  @Test
  public void verifyDeleteCookiesTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    Cookie cookie = new Cookie("cookie", "Cookie Monster");
    page.addCookie(cookie);
    
    long startTestTime = System.currentTimeMillis();
    page.deleteCookies();
    testTime = System.currentTimeMillis() - startTestTime;
    
    ArrayList<Cookie> crumbs = new ArrayList<Cookie>(page.getCookies());
    assertTrue(!crumbs.contains(cookie));
  }
  
  @Test
  public void verifySwitchToFrameTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    page.clickFramePage();
    
    SeleniumPlaygroundFramePage framePage = new SeleniumPlaygroundFramePage(driver);
    
    long startTestTime = System.currentTimeMillis();
    framePage.swtichToFrameMenu();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertTrue(framePage.isClickMeDisplayed());
  }
  
  @Test
  public void verifySwitchToDefaultContentTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundFramePage page = framePageLoad();
    page.swtichToFrameMenu();
    assertTrue(page.isClickMeDisplayed());
    
    try {
      long startTestTime = System.currentTimeMillis();
      page.defaultContent();
      testTime = System.currentTimeMillis() - startTestTime;
    } catch(NoSuchElementException e) {
      assertTrue(e.getMessage().contains("no such element"));
    }
    
  }
  
  @Test
  public void verifyCloseCurrentWindowTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();

    long startTestTime = System.currentTimeMillis();
    page.closeCurrentWindow();
    testTime = System.currentTimeMillis() - startTestTime;
    
    String message;
    try {
      driver.get(SeleniumPlaygroundPage.URL);
      message = "";
    } catch(Exception e) {
      message = e.getMessage();
    }
    
    LOGGER.info(message);
    assertTrue(message.contains("no such element"));
  }
  
  @Test
  public void verifyDoubleClickTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundTestPage page = testPageLoad();

    long startTestTime = System.currentTimeMillis();
    page.doubleClick();
    testTime = System.currentTimeMillis() - startTestTime;
    
    float random = page.getRandomNumber();
    assertTrue(random >= 0.0 & random <= 1.0);
  }
  
  @Test
  public void verifyLocalStorageKeyTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String value = page.getLocalStorageByKey(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME);
    testTime = System.currentTimeMillis() - startTestTime;

    assertEquals(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME_VALUE, value);
  }
  
  @Test
  public void verifyClearLocalStorageTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.clearLocalStorage();
    testTime = System.currentTimeMillis() - startTestTime;
    
    int value =  page.getLocalStorageSize();

    assertTrue(value == 0);
  }
  
  @Test
  public void verifyGetLocalStorageSizeTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    page.clearLocalStorage();
    
    long startTestTime = System.currentTimeMillis();
    int size = page.getLocalStorageSize();
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(0, size);
  }
  
  @Test
  public void verifyPrintLocalStorageTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    String values = page.printLocalStorage();
    testTime = System.currentTimeMillis() - startTestTime;

    assertTrue(
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME) & 
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_FIRSTNAME_VALUE) &
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_LASTNAME) &
        values.contains(SeleniumPlaygroundPage.LOCAL_STORAGE_KEY_LASTNAME_VALUE)
        );
  }
  
  @Test
  public void verifyWaitForElementVisibilityTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundTestPage page = testPageLoad();
    
    long startTestTime = System.currentTimeMillis();
    boolean visibile = page.isDelayTextVisible();
    testTime = System.currentTimeMillis() - startTestTime;

    assertTrue(visibile);
  }
  
  @Test
  public void verifyControlKeyDownTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundPage page = pageLoad();
    
    long startTestTime = System.currentTimeMillis();
    page.selectMultipleCars();
    testTime = System.currentTimeMillis() - startTestTime;
    
    String selected = page.getSelectedCar();
    
    LOGGER.info(selected);
    assertTrue(selected.contains(SeleniumPlaygroundPage.VOLVO) & selected.contains(SeleniumPlaygroundPage.AUDI));
  }
  
  @Test
  public void verifyFileUploadTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    SeleniumPlaygroundUploadPage page = uploadPageLoad();
    
    page.setFileDetector(new LocalFileDetector());
    
    File file = new File(Constants.UPLOAD_FILENAME);
    LOGGER.info(file.getAbsolutePath());
    
    long startTestTime = System.currentTimeMillis();
    String body = page.uploadFile(file.getAbsolutePath());
    testTime = System.currentTimeMillis() - startTestTime;
    
    assertEquals(SeleniumPlaygroundUploadPage.BODY, body);
  }
  
}
