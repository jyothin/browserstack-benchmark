package Saucelabs;

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
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import Common.LogFormatter;
import Common.StabilityException;

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
  
  private static final String CAPABILITY_TYPE_BUILD = "build";
  private static final String CAPABILITY_TYPE_NAME = "name";
  private static final String CAPABILITY_TYPE_DEVICE_NAME = "deviceName";
  private static final String CAPABILITY_TYPE_SCREENSHOTS = "recordScreenshots";
  
  private static final String SAUCE_LABS_ENDPOINT = "@ondemand.saucelabs.com:443/wd/hub";
  private static final String TESTOBJECT_ENDPOINT = "@eu1.appium.testobject.com/wd/hub";
  private static final String TESTOBJECT_API_KEY = "C118D66142464D22BADE2E618CB527DF";
  
  @Rule
  public TestWatcher watchman = new TestWatcher() {
    
    @Override
    protected void succeeded(Description description) {
      LOGGER.info(LogFormatter.createMessage(
          build,
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
      if(e instanceof SessionNotCreatedException ||
          e instanceof UnreachableBrowserException ||
          e instanceof UnknownServerException
        ) {
        exceptionType = "StabilityException";
        exception = e.getClass().getSimpleName();
        message = e.getMessage();
      } else if(e instanceof WebDriverException) {
        exceptionType = "SeleniumException";
        exception = e.getClass().getSimpleName();
        message = e.getMessage();
      } else if(e instanceof Exception) {
        exceptionType = "StabilityException";
        StabilityException stabilityException = new StabilityException();
        exception = stabilityException.getClass().getSimpleName();
        message = stabilityException.getMessage();
      } else if("AssertionError".equals(e.getClass().getSimpleName())){
        exceptionType = "AssertionError";
        exception = e.getClass().getSimpleName();
        message = e.getMessage();
      } else {
        exceptionType = "StabilityException";
        StabilityException stabilityException = new StabilityException();
        exception = stabilityException.getClass().getSimpleName();
        message = stabilityException.getMessage();
      }
      LOGGER.severe(LogFormatter.createMessage(
          build,
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

  public static String build;
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
  
  /* Takes care of sending the result of the tests over to TestObject. */
//  @Rule
//  public TestObjectTestResultWatcher resultReportingTestObjectWatcher = new TestObjectTestResultWatcher();
  
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
  protected String osVersion; // platform
  protected String browser; // browserName
  protected String browserVersion; // version
  protected String device;
  protected String realMobile;
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
   * @param osVersion
   * @param browser
   * @param browserVersion
   * @param device
   */
  public SauceLabsParallelTestBase(String os, String osVersion, String browser, String browserVersion, 
      String device, String realMobile) {
    super();
    this.os = os;
    this.osVersion = osVersion;
    this.browser = browser;
    this.browserVersion = browserVersion;
    this.device = device;
    this.realMobile = realMobile;
  }
  
  /**
   * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. 
   * The values in the String array are used as part of the invocation of the test constructor
   */
  @ConcurrentParameterized.Parameters
  public static LinkedList<String[]> browsersStrings() {
    LinkedList<String[]> browsers = new LinkedList<String[]>();
    // os, long_version, api_name, ..., ...
//    browsers.add(new String[]{"Mac", "10.12", "safari", "10.0.", "", ""});
//    browsers.add(new String[]{"Mac" ,"10.12", "chrome", "58", "", ""}); //  58.0.3029.81.
//    browsers.add(new String[]{"Mac", "10.12", "firefox", "53.0.", "", ""});
//    browsers.add(new String[]{"Windows", "10", "microsoftedge", "15.15063.", "", ""});
//    browsers.add(new String[]{"Windows", "10", "chrome", "58", "", ""});
//    browsers.add(new String[]{"Windows", "10", "firefox", "53.0.", "", ""});
//    browsers.add(new String[]{"Windows", "10", "internet explorer", "11.103.10586.0.", "", ""});

//    browsers.add(new String[]{"Android", "7.1", "chrome", "", "Google Pixel", "true"}); // Google_Pixel_real
    browsers.add(new String[]{"iOS", "10.0", "safari", "", "iPhone 7 32GB", "true"}); // iPhone_7_32GB_10_real

    return browsers;
  }

  @BeforeClass
  public static void setupClass() {
    //If available add build tag. When running under Jenkins BUILD is automatically set.
    //You can set this manually on manual runs.
    build = System.getenv("BUILD");
    
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
      capabilities.setCapability("platformVersion", osVersion);
      capabilities.setCapability("testobjectApiKey", testObjectApiKey);
      capabilities.setCapability("testobject_api_key", testObjectApiKey);
      capabilities.setCapability("testobject_suite_name", build);
      capabilities.setCapability(CAPABILITY_TYPE_DEVICE_NAME, device);
    } else {
      endpointUri = SAUCE_LABS_ENDPOINT;
    }

    capabilities.setCapability(CapabilityType.PLATFORM, os+" "+osVersion);
    capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
    capabilities.setCapability(CapabilityType.VERSION, browserVersion);
    
    capabilities.setCapability(CAPABILITY_TYPE_SCREENSHOTS, false);

    methodName = name.getMethodName();
    capabilities.setCapability(CAPABILITY_TYPE_NAME, methodName);
    
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.BROWSER, Level.ALL);
    capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    
    //Getting the build name.
    //Using the Jenkins ENV var. You can use your own. If it is not set test will run without a build id.
    if (build != null) {
      capabilities.setCapability(CAPABILITY_TYPE_BUILD, build);
    }
    
    fullUrl = "https://" + username + ":" + accessKey + endpointUri;
    
    startStartTimestamp = System.currentTimeMillis();
    driver = new RemoteWebDriver(
      new URL(fullUrl),
      capabilities);
    startTime = System.currentTimeMillis() - startStartTimestamp;
    
//    if("iOS".equals(os) || "Android".equals(os)) {
//      resultReportingTestObjectWatcher.setRemoteWebDriver(driver);
//    }

    sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
  }

  @After
  public void tearDown() throws Exception {
    long stopStartTimestamp = System.currentTimeMillis();
    if(driver != null ) { driver.quit(); }
    long stopEndTimestamp = System.currentTimeMillis();
    stopTime = stopEndTimestamp - stopStartTimestamp;
    
    totalTime = stopEndTimestamp - startStartTimestamp;
    
  }
  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }
  
//  @Test
//  public void test() {
//    //fail("Not yet implemented");
//  }

  public String getSessionId() {
    return sessionId;
  }

}
