package com.browserstack.benchmark.pages;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ExamplePage {
  
  public static final Logger LOGGER = Logger.getLogger(ExamplePage.class.getName());
  
  public static final String URL = "https://example.com";
  public static final String REF_SCREENSHOT_FILENAME = "./src/test/resources/reference_screenshot.png";
  
  public WebDriver driver;
  
  public static ExamplePage visitPage(WebDriver driver) {
    ExamplePage page = new ExamplePage(driver);
    page.visitPage();
    return page;
  }
  
  public ExamplePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  public void visitPage() {
    driver.get(URL);
  }
  
  public File saveScreenshot(Dimension size) {
    driver.manage().window().setSize(size);
    TakesScreenshot takesScreenShot = (TakesScreenshot) driver;
    File screenShotFile = takesScreenShot.getScreenshotAs(OutputType.FILE);
    return screenShotFile;
  }
  
  public File getRefScreenshot() throws IOException {
    return new File(REF_SCREENSHOT_FILENAME);
  }
  
}

