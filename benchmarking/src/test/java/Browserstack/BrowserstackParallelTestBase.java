package Browserstack;

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

import Common.LogFormatter;
import Common.StabilityException;

/**
 * Benchmark JUnit tests that runs tests against Browserstack using multiple browsers in parallel.
 *
 * @author Jyothin Madari
 */
@Ignore
@RunWith(Parameterized.class)
public class BrowserstackParallelTestBase {
  
  public static final Logger LOGGER = Logger.getLogger(BrowserstackParallelTestBase.class.getName());
  
  private static final String LOG_FILE = "./src/test/resources/bs_test.html";
  
  private static final String BROWSERSTACK_ENDPOINT = "@hub-cloud.browserstack.com/wd/hub";
  
  private static final String CAPABILITY_TYPE_OS_VERSION = "os_version";
  private static final String CAPABILITY_TYPE_BROWSER = "browser";
  private static final String CAPABILITY_TYPE_BROWSER_VERSION = "browser_version";
  private static final String CAPABILITY_TYPE_REAL_MOBILE = "realMobile";
  private static final String CAPABILITY_TYPE_DEVICE = "device";
  private static final String CAPABILITY_TYPE_DEBUG = "browserstack.debug";
  private static final String CAPABILITY_TYPE_BUILD = "build";
  private static final String CAPABILITY_TYPE_NAME = "name";
  private static final String CAPABILITY_TYPE_OS = "os";
  
  public static String build;
  public static String logFile;
  public static String username = "";
  public static String accessKey = "";
  public static String endpointUri;
  public static String fullUrl;
  public static DesiredCapabilities capabilities;
  
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
  protected String device;
  protected String realMobile;
  //protected String seleniumVersion;
  //protected String chromedriverVersion;
  //protected String iedriverVersion;
  
  protected String sessionId;
  
  protected WebDriver driver;
  
  @Rule
  public TestName name = new TestName() {
    public String getMethodName() {
      return String.format("%s", super.getMethodName());
    }
  };
  
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
  
  public BrowserstackParallelTestBase(String os, String osVersion, String browser,
      String browserVersion, String device, String realMobile) {
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
  @Parameterized.Parameters
  public static LinkedList<String[]> browsersStrings() {
    LinkedList<String[]> browsers = new LinkedList<String[]>();
    // os, os_version, browser, browser_version, device, real_mobile
//    browsers.add(new String[]{"OS X", "Sierra", "safari", "10.1", "", ""});
//    browsers.add(new String[]{"OS X", "Sierra", "chrome", "58", "", ""});
//    browsers.add(new String[]{"OS X", "Sierra", "firefox", "53.0", "", ""});
//    browsers.add(new String[]{"Windows", "10", "edge", "15.0", "", ""});
//    browsers.add(new String[]{"Windows", "10", "chrome", "58", "", ""});
//    browsers.add(new String[]{"Windows", "10", "firefox", "53.0", "", ""});
//    browsers.add(new String[]{"Windows", "10", "ie", "11.0", "", ""});

    browsers.add(new String[]{"Android", "7.1", "chrome", "", "Google Pixel", "true"});
    browsers.add(new String[]{"iOS", "10.0", "safari", "", "iPhone 7", "true"});
    
    return browsers;
  }

  
  @BeforeClass
  public static void setupClass() {
    username = System.getenv("BROWSERSTACK_USERNAME");
    accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

    //get the uri to send the commands to.
    endpointUri = BROWSERSTACK_ENDPOINT;
    
    //If available add build tag. When running under Jenkins BUILD is automatically set.
    //You can set this manually on manual runs.
    build = System.getenv("BUILD");
    
    File logFileFd = new File(LOG_FILE);
    if(!logFileFd.exists()) {
      System.out.println("Creating log file");
      try {
        logFileFd.createNewFile();
        System.out.println("Created log file: " + logFileFd.getPath());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        //e.printStackTrace();
      }
    }
    
    try {
      FileHandler fileHandler = new FileHandler(logFileFd.getPath());
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
    
    capabilities.setCapability(CAPABILITY_TYPE_OS, os);
    capabilities.setCapability(CAPABILITY_TYPE_OS_VERSION, osVersion);
    capabilities.setCapability(CAPABILITY_TYPE_BROWSER, browser);
    capabilities.setCapability(CAPABILITY_TYPE_BROWSER_VERSION, browserVersion);
    
    if("true".equals(realMobile)) {
      capabilities.setCapability(CAPABILITY_TYPE_REAL_MOBILE, true);
    }
    capabilities.setCapability(CAPABILITY_TYPE_DEVICE, device);

    capabilities.setCapability(CAPABILITY_TYPE_DEBUG, false);

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
  
  public String getSessionId() {
    return sessionId;
  }
  
}
