package com.browserstack.benchmark.saucelabs;


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
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

/**
 * Benchmark JUnit tests that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed.
 *
 * @author Jyothin Madari
 */
@Ignore
@RunWith(ConcurrentParameterized.class)
public class SauceLabsParallelTestBase implements SauceOnDemandSessionIdProvider {
  
  public static final Logger LOGGER = Logger.getLogger(SauceLabsParallelTestBase.class.getName());
  
  private static final String LOG_FILE = "./src/test/resources/sl_test.html";
  
  private static final String CAPABILITY_TYPE_DEVICE_NAME = "deviceName";
  private static final String CAPABILITY_TYPE_NAME = "name";
  private static final String CAPABILITY_TYPE_BUILD = "build";
  
  private static final String SAUCE_LABS_ENDPOINT = "@ondemand.saucelabs.com:443/wd/hub";
  private static final String TESTOBJECT_ENDPOINT = "@us1.appium.testobject.com/wd/hub";
  private static final String TESTOBJECT_API_KEY = "67DAB462ED5C49C893C602E8B4B79B81";
  
  @Rule
  public TestWatcher watchman = new TestWatcher() {
    
    @Override
    protected void succeeded(Description description) {
      LOGGER.info(LogFormatter.createMessage(
          buildTag,
          testId,
          sessionId,
          os,
          version,
          browser,
          deviceName,
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
          os,
          version,
          browser,
          deviceName,
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
  public static String username = System.getenv("SAUCE_USERNAME");
  public static String accessKey = System.getenv("SAUCE_ACCESS_KEY");
  public static String endpointUri;
  public static String testObjectApiKey = TESTOBJECT_API_KEY;
  public static String fullUrl;
  public static DesiredCapabilities capabilities;
  
  /**
   * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.
   * To use the authentication supplied by environment variables or from an external file, use the no-arg 
   * {@link SauceOnDemandAuthentication} constructor.
   */
  public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accessKey);
  
  /**
   * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
   */
  @Rule
  public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
  
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
  protected String octaneScore;
  protected String individualScores;
  
  protected String os; // platform
  protected String version;
  protected String browser; // browserName
  protected String deviceName;
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
  public SauceLabsParallelTestBase(String os, String version, String browser, String deviceName) {
    super();
    this.os = os;
    this.version = version;
    this.browser = browser;
    this.deviceName = deviceName;
  }
  
  /**
   * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. 
   * The values in the String array are used as part of the invocation of the test constructor
   */
  @ConcurrentParameterized.Parameters
  public static LinkedList<String[]> browsersStrings() {
    LinkedList<String[]> browsers = new LinkedList<String[]>();
    // os, long_version, api_name, ..., ...
    browsers.add(new String[]{"Mac 10.12", "10.0.", "safari", null});
    browsers.add(new String[]{"Mac 10.12", "58", "chrome", null}); //  58.0.3029.81.
    browsers.add(new String[]{"Mac 10.12", "53.0.", "firefox", null});
    browsers.add(new String[]{"Windows 10", "15.15063.", "microsoftedge", null});
    browsers.add(new String[]{"Windows 10", "58", "chrome", null});
    browsers.add(new String[]{"Windows 10", "53.0.", "firefox", null});
    browsers.add(new String[]{"Windows 10", "11.103.10586.0.", "internet explorer", null});

//    browsers.add(new String[]{"Android", "7.1.1", "chrome", "Google Pixel Real"});
//    browsers.add(new String[]{"iOS", "10.1.1", "safari", "iPhone 7"});

    return browsers;
  }

  @BeforeClass
  public static void setupClass() {
    //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
    //You can set this manually on manual runs.
    buildTag = System.getenv("BUILD_TAG");
    
    File logFile = new File(LOG_FILE);
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

    //get the uri to send the commands to.
    if("iOS".equals(os) || "Android".equals(os)) {
      endpointUri = TESTOBJECT_ENDPOINT;
      
      capabilities.setCapability("platformName", os);
      capabilities.setCapability("platformVersion", version);
      capabilities.setCapability("testobjectApiKey", testObjectApiKey);
    } else {
      endpointUri = SAUCE_LABS_ENDPOINT;
    }

    capabilities.setCapability(CapabilityType.PLATFORM, os);
    capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
    capabilities.setCapability(CapabilityType.VERSION, version);
    capabilities.setCapability(CAPABILITY_TYPE_DEVICE_NAME, deviceName);

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