package Tests;

import java.io.File;

import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Tests.AbstractBaseTests.TestBase;

/**
 * Test.
 */
public class ScreenshotTest extends TestBase {

    private final String TEST_URL = "http://docs.aws.amazon.com/devicefarm/latest/developerguide/welcome.html";

    @Test
    public void testScreenshot() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm.png"));
      
    }
    
    @Test
    public void testScreenshot1() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm1.png"));
      
    }
    
    @Test
    public void testScreenshot2() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm2.png"));
      
    }
    
    @Test
    public void testScreenshot3() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm3.png"));
      
    }
    
    @Test
    public void testScreenshot4() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm4.png"));
      
    }
    
    @Test
    public void testScreenshot5() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm5.png"));
      
    }
    
    @Test
    public void testScreenshot6() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm6.png"));
      
    }
    
    @Test
    public void testScreenshot7() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm7.png"));
      
    }
    
    @Test
    public void testScreenshot8() throws InterruptedException {
      Thread.sleep(5000);
      driver.get(TEST_URL);
      Thread.sleep(5000);
      // This will store the screenshot under /tmp on your local machine
      String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      screenshot.renameTo(new File(screenshotDir, "device_farm8.png"));
      
    }
    
}
