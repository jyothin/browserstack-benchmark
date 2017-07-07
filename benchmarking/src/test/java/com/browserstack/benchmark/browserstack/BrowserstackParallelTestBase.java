package com.browserstack.benchmark.browserstack;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.ImeActivationFailedException;
import org.openqa.selenium.ImeNotAvailableException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchCookieException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnableToSetCookieException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.InvalidCoordinatesException;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.browserstack.benchmark.common.LogFormatter;
import com.browserstack.benchmark.common.StabilityException;

/**
 * Benchmark JUnit tests that runs tests against Browserstack using multiple browsers in parallel.
 *
 * @author Jyothin Madari
 */
@Ignore
//@RunWith(Parallelized.class)
@RunWith(Parameterized.class)
public class BrowserstackParallelTestBase {
  
  public static final Logger LOGGER = Logger.getLogger(BrowserstackParallelTestBase.class.getName());

  private static final String LOG_FILE = "./src/test/resources/bs_test.html";
  
  private static final String CAPABILITY_TYPE_BUILD = "build";
  private static final String CAPABILITY_TYPE_NAME = "name";
  private static final String CAPABILITY_TYPE_OS = "os";
  private static final String CAPABILITY_TYPE_OS_VERSION = "os_version";
  private static final String CAPABILITY_TYPE_BROWSER = "browser";
  private static final String CAPABILITY_TYPE_BROWSER_VERSION = "browser_version";
  private static final String CAPABILITY_TYPE_REAL_MOBILE = "realMobile";
  private static final String CAPABILITY_TYPE_DEVICE = "device";
  
  private static final String BROWSERSTACK_ENDPOINT = "@hub-cloud.browserstack.com/wd/hub";
  
  @Rule
  public TestWatcher watchman = new TestWatcher() {
    
    @Override
    protected void succeeded(Description description) {
      LOGGER.info(LogFormatter.createMessage(
          buildTag,
          testId,
          sessionId,
          os+" "+osVersion,
          browserVersion,
          browser,
          device,
          methodName,
          startTime,
          pageLoadTime,
          testTime,
          stopTime,
          totalTime,
          "",
          "",
          "",
          octaneScore,
          individualScores
          ));
    }
    
    @Override
    protected void failed(Throwable e, Description description) {
      String exceptionType = "";
      String exception = "";
      String message = "";
      if(e instanceof ElementNotInteractableException ||
          e instanceof ElementNotSelectableException ||
          e instanceof ElementNotVisibleException ||
          //e instanceof ErrorInResponseException ||
          e instanceof ImeActivationFailedException ||
          e instanceof ImeNotAvailableException ||
          e instanceof InvalidArgumentException ||
          e instanceof InvalidCookieDomainException ||
          e instanceof InvalidElementStateException ||
          e instanceof InvalidSelectorException ||
          e instanceof InvalidArgumentException ||
          e instanceof MoveTargetOutOfBoundsException ||
          e instanceof InvalidCoordinatesException ||
          e instanceof NoAlertPresentException ||
          e instanceof NoSuchCookieException ||
          e instanceof NoSuchElementException ||
          e instanceof NoSuchFrameException ||
          e instanceof NoSuchWindowException ||
          //e instanceof RemoteDriverServerException ||
          e instanceof StaleElementReferenceException ||
          e instanceof TimeoutException ||
          e instanceof UnableToSetCookieException ||
          e instanceof UnexpectedTagNameException ||
          e instanceof UnexpectedTagNameException ||
          e instanceof WebDriverException) {
        exceptionType = "SeleniumException";
        exception = e.getClass().getSimpleName();
        message = e.getMessage();
      } else if(e instanceof Exception) {
        exceptionType = "StabilityException";
        // Get type of stability exception
        StabilityException stabilityException = new StabilityException();
        exception = stabilityException.getClass().getSimpleName();
        message = stabilityException.getMessage();
      } else {
        exceptionType = "AssertionError";
        exception = e.getClass().getSimpleName();
        message = e.getMessage();
      }
      LOGGER.severe(LogFormatter.createMessage(
          buildTag,
          testId,
          sessionId,
          os+" "+osVersion,
          browserVersion,
          browser,
          device,
          methodName,
          startTime,
          pageLoadTime,
          testTime,
          stopTime,
          totalTime,
          exceptionType,
          exception,
          message,
          octaneScore,
          individualScores
          ));
    }
    
  };
  
  public static String buildTag;
  public static String username = System.getenv("BROWSERSTACK_USERNAME");
  public static String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
  public static String endpointUri;
  public static String fullUrl;
  public static DesiredCapabilities capabilities;
  
  @Rule
  public TestName name = new TestName() {
    public String getMethodName() {
      return String.format("%s", super.getMethodName());
    }
  };
  
  protected String methodName;
  protected int testId;
  
  /*
   * Timestamps
   */
  protected long startStartTimestamp;
  protected long startTime;
  protected long pageLoadTime;
  protected long testTime;
  protected long stopTime;
  protected long totalTime;
  
  /*
   * Scores
   */
  protected String octaneScore = "";
  protected String individualScores = "";
  
  protected String os;
  protected String osVersion;
  protected String browser;
  protected String browserVersion;
  protected String realMobile;
  protected String device;
  //protected String seleniumVersion;
  //protected String chromedriverVersion;
  //protected String iedriverVersion;
  
  protected String sessionId;
  
  protected WebDriver driver;
  
  /**
   * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the
   * operating system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be
   * the same as that of the elements within the {@link #browsersStrings()} method.
   * @param os
   * @param version
   * @param browser
   * @param deviceName
   * @param deviceOrientation
   */
  public BrowserstackParallelTestBase(String os, String osVersion, String browser,
      String browserVersion, String realMobile, String device) {
    super();
    this.os = os;
    this.osVersion = osVersion;
    this.browser = browser;
    this.browserVersion = browserVersion;
    this.realMobile = realMobile;
    this.device = device;
  }
  
  /**
   * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. 
   * The values in the String array are used as part of the invocation of the test constructor
   */
  @Parameterized.Parameters
  public static LinkedList<String[]> browsersStrings() {
    LinkedList<String[]> browsers = new LinkedList<String[]>();
    // os, os_version, browser, browser_version, real_mobile, device
    browsers.add(new String[]{"OS X", "Sierra", "safari", "10.1", null, null});
    browsers.add(new String[]{"OS X", "Sierra", "chrome", "58", null, null});
    browsers.add(new String[]{"OS X", "Sierra", "firefox", "53.0", null, null});
    browsers.add(new String[]{"Windows", "10", "edge", "15.0", null, null});
    browsers.add(new String[]{"Windows", "10", "chrome", "58", null, null});
    browsers.add(new String[]{"Windows", "10", "firefox", "53.0", null, null});
    browsers.add(new String[]{"Windows", "10", "ie", "11.0", null, null});

//    browsers.add(new String[]{"Android", "7.1.1", "chrome", "", "true", "Google Pixel"});
//    browsers.add(new String[]{"iOS", "10.1.1", "safari", "", "true", "iPhone 7"});

    return browsers;
  }

  @BeforeClass
  public static void setupClass() {
    //get the uri to send the commands to.
    endpointUri = BROWSERSTACK_ENDPOINT;
    
    //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
    //You can set this manually on manual runs.
    buildTag = System.getenv("BUILD_TAG");
    
    //URL url = ParallelTestBase.class.getResource(LOG_FILE);
    File logFile = new File(LOG_FILE);
    //if(url == null) {
    if(!logFile.exists()) {
      System.out.println("Creating log file");
      try {
        logFile.createNewFile();
        System.out.println("Created log file: " + logFile.getPath());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        //e.printStackTrace();
      }
    }
    
    try {
      FileHandler fileHandler = new FileHandler(logFile.getPath());
      fileHandler.setFormatter(new LogFormatter());
      LOGGER.addHandler(fileHandler);
      
    } catch (SecurityException e) {
      System.out.println(e.getMessage());
      //e.printStackTrace();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      //e.printStackTrace();
    }
    
    LOGGER.setUseParentHandlers(false);
    
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the 
   * {@link #browser}, {@link #version} and {@link #os} instance variables, and which is configured to run against 
   * ondemand.saucelabs.com, using the username and access key populated by the {@link #authentication} instance.
   *
   * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
   */
  @Before
  public void setUp() throws Exception {
    capabilities = new DesiredCapabilities();
    
    capabilities.setCapability(CAPABILITY_TYPE_OS, os);
    capabilities.setCapability(CAPABILITY_TYPE_OS_VERSION, osVersion);
    capabilities.setCapability(CAPABILITY_TYPE_BROWSER, browser);
    capabilities.setCapability(CAPABILITY_TYPE_BROWSER_VERSION, browserVersion);
    
    capabilities.setCapability(CAPABILITY_TYPE_REAL_MOBILE, realMobile);
    capabilities.setCapability(CAPABILITY_TYPE_DEVICE, device);

    methodName = name.getMethodName();
    capabilities.setCapability(CAPABILITY_TYPE_NAME, methodName);
    
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.BROWSER, Level.ALL);
    capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    
    //Getting the build name.
    //Using the Jenkins ENV var. You can use your own. If it is not set test will run without a build id.
    if (buildTag != null) {
      capabilities.setCapability(CAPABILITY_TYPE_BUILD, buildTag);
    }
    
    fullUrl = "https://" + username + ":" + accessKey + endpointUri;
    
    startStartTimestamp = System.currentTimeMillis();
    driver = new RemoteWebDriver(
      new URL(fullUrl),
      capabilities);
    startTime = System.currentTimeMillis() - startStartTimestamp;

    sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
  }

  @After
  public void tearDown() throws Exception {
    long stopStartTimestamp = System.currentTimeMillis();
    driver.quit();
    long stopEndTimestamp = System.currentTimeMillis();
    stopTime = stopEndTimestamp - stopStartTimestamp;
    
    totalTime = stopEndTimestamp - startStartTimestamp;
    
  }

//  @Test
//  public void test() {
//    //fail("Not yet implemented");
//  }
  
  public String getSessionId() {
    return sessionId;
  }
  
}