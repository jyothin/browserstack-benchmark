package com.browserstack.benchmark;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SampleTest {
  
  public static final Logger LOGGER = Logger.getLogger(SampleTest.class.getName());
  
  public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
  public static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
  public static final String BROWSERSTACK_URL = "https://" + 
      USERNAME + ":" +
      ACCESS_KEY + ":"+
      "@hub-cloud.browserstack.com/wd/hub";
  
  public static void main( String[] args ) throws InterruptedException, MalformedURLException {
    
    System.setProperty("webdriver.chrome.driver",  "/Users/Jyothin/bin/chromedriver");
    
    WebDriver driver = null;
    
    String buildTag = System.getenv("BUILD_TAG");
    if (buildTag == null) {
      buildTag = "SampleTest";
    }
    
    //DesiredCapabilities chromeCaps = new ChromeOptions();
    //driver = new ChromeDriver(chromeCaps);
    
//      DesiredCapabilities caps = new DesiredCapabilities();
//      caps.setCapability("os", "android");
//      caps.setCapability("os_version", "");
//      caps.setCapability("browserName", "android");
////      caps.setCapability("device", "Google Pixel");
//      caps.setCapability("device",  "Samsung Galaxy Note 4");
//      caps.setCapability("realMobile", "true");
//      driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
//      driver.get("http://www.google.com");
//      System.out.println(driver.getTitle());
//      driver.findElement(By.name("q")).sendKeys("BrowserStack");
//      driver.findElement(By.name("btnG")).click();
//      System.out.println(driver.getTitle());
//      Thread.sleep(5 * 1000);
//      driver.get("http://example.com");
//      System.out.println(driver.getTitle());
//      Thread.sleep(5 * 1000);
    
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("build", buildTag);
    caps.setCapability("os", "OS X");
    caps.setCapability("os_version", "Sierra");
    caps.setCapability("browser", "chrome");
    caps.setCapability("browser_version", "58");
    driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
    driver.get("http://www.example.org");
    Dimension dimensions = new Dimension(1024, 768);
    driver.manage().window().setSize(dimensions);
    TakesScreenshot takesScreenShot = (TakesScreenshot) driver;
    File screenShotFile = takesScreenShot.getScreenshotAs(OutputType.FILE);
    BufferedImage bScreenshot = null;
    DataBuffer dScreenshot = null;
    int sizeScreenshot = -1;
    try {
      bScreenshot = ImageIO.read(screenShotFile);
      dScreenshot = bScreenshot.getData().getDataBuffer();
      sizeScreenshot = dScreenshot.getSize();
    } catch (IOException e) {
      LOGGER.severe(e.getMessage());
      //e.printStackTrace();
    }
    LOGGER.info(Integer.toString(sizeScreenshot));
    
    File file = new File("src/main/resources/screenshot.png");
    LOGGER.info(file.getAbsolutePath());
    if(!file.exists()) {
      try {
        file.createNewFile();
        ImageIO.write(bScreenshot, "png", file);
        LOGGER.info("Written to file: " + file.getAbsolutePath());
      } catch (IOException e) {
        LOGGER.warning(e.getMessage());
        //e.printStackTrace();
      }
    }
    
    driver.quit();
    
    System.out.println( "Hello World!" );
  }
}
