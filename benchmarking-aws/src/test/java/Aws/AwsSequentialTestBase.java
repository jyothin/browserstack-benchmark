package Aws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import Common.LogFormatter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public abstract class AwsSequentialTestBase {
  
  public static final Logger LOGGER = Logger.getLogger(AwsSequentialTestBase.class.getName());
  
  private static final String LOG_FILE_DIR = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", "src/test/resources"));
  private static final String LOG_FILE = "aws_test.html";

  private static final String AWS_ENDPOINT = "http://127.0.0.1:4723/wd/hub";

//  private final int TIMEOUT = 10;
  
  public static String build;
  public static String endpointUri;
  public static String fullUrl;
  public static DesiredCapabilities capabilities;
  java.io.File logFile;
  Drive service;
  
//  @Rule
//  public TestName name = new TestName() {
//    public String getMethodName() {
//      return String.format("%s", super.getMethodName());
//    }
//  };
    
  public String methodName;
  public static int testId;
    
  /*
   * Timestamps
   */
  protected long startStartTimestamp;
  public static long startTime;
  public static long pageLoadTime;
  public static long testTime;
  public static long stopTime;
  public static long totalTime;
    
  /*
   * Scores
   */
  public static String octaneScore;
  public static String individualScores;

//browsers.add(new String[]{"Android", "7.1", "chrome", "", "Google Pixel", "true"}); // Google_Pixel_real
//browsers.add(new String[]{"iOS", "10.0", "safari", "", "iPhone 7 32GB", "true"}); // iPhone_7_32GB_10_real
  public static String os = "Android"; // platform
  public static String osVersion = "7.1"; // platform
  public static String browser = "chrome"; // browserName
  public static String browserVersion = ""; // version
  public static String device = "Google Pixel";
  
  public static String sessionId;
  
  //protected WebDriver driver;
  public static AndroidDriver<MobileElement> driver;
    
  @BeforeSuite
  public void setupClass() throws MalformedURLException{
    
    build = System.getenv("BUILD");
    System.out.println("BUILD: " + build);
    
    logFile = new java.io.File(LOG_FILE_DIR, LOG_FILE);
//      File logFile = new File(LOG_FILE);
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
    
  @BeforeMethod
  public void setUp() throws Exception {
    // Remove for AWS Tests
    capabilities = new DesiredCapabilities();
//    capabilities.setCapability("deviceName", "Google Pixel");
//    capabilities.setCapability("platformName", "Android");
//    capabilities.setCapability("browserName", "chrome");

    endpointUri = AWS_ENDPOINT;
    
    //methodName = name.getMethodName();
    
    fullUrl = endpointUri;
    
    startStartTimestamp = System.currentTimeMillis();
    driver = new AndroidDriver<MobileElement>(
        new URL(fullUrl), 
        capabilities);
    startTime = System.currentTimeMillis() - startStartTimestamp;
//    driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    
    sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
  }
  
  @AfterMethod
  public void tearDown(){
    long stopStartTimestamp = System.currentTimeMillis();
    if(driver != null ) { driver.quit(); }
    long stopEndTimestamp = System.currentTimeMillis();
    stopTime = stopEndTimestamp - stopStartTimestamp;
    
    totalTime = stopEndTimestamp - startStartTimestamp;
  }
  
  @AfterSuite
  public void tearDownAfterClass() {
    try {
      service = DriveAccess.getDriveService();
    } catch (IOException e) {
      //e.printStackTrace();
      System.out.println("Unable to get Drive service");
      System.exit(-1);
    }
    
    System.out.println("logfile :" + logFile.getAbsolutePath());
    String folderId = "0B1AzuZKCRm_fZGpKR1R3c0dMXzQ";
    File fileMetaData = new File();
    fileMetaData.setName(LOG_FILE);
    fileMetaData.setMimeType("text/html");
    fileMetaData.setParents(Collections.singletonList(folderId));
    FileContent mediaContent = new FileContent("text/html", logFile);
    System.out.println(mediaContent.getLength());
    try {
      File file = service.files().create(fileMetaData, mediaContent)
          .setFields("id, parents")
          .execute();
      System.out.println("File ID: " + file.getId());
    } catch (IOException e) {
      //e.printStackTrace();
      System.out.println("Unable to upload file");
    }
    
    try {
      FileList fileList = service.files().list().execute();
      for(File file: fileList.getFiles()) {
        System.out.println(file.getName());
      }
    } catch (IOException e) {
      //e.printStackTrace();
      System.out.println("Unable to get file list");
    }
    
  }
  
  public String getSessionId() {
    return sessionId;
  }
  
}
