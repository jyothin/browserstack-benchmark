package Aws;

import java.io.File;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.testng.annotations.Test;

public class ScreenshotTest extends AwsSequentialTestBase {

  private final String TEST_URL = "http://stormy-beyond-9729.herokuapp.com/";

  @Test(enabled=false)
  public void testScreenshot() throws InterruptedException {
    Thread.sleep(1000);
    driver.get(TEST_URL);
    Thread.sleep(1000);
    // This will store the screenshot under /tmp on your local machine
    String screenshotDir = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    screenshot.renameTo(new File(screenshotDir, "screenshot.png"));
  }
}
