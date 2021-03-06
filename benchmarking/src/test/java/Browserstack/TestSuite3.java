package Browserstack;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import Pages.OctanePage;

@Ignore
public class TestSuite3 extends BrowserstackParallelTestBase {
  
  public TestSuite3(String os, String osVersion, String browser,
      String browserVersion, String device, String realMobile) {
    super(os, osVersion, browser, browserVersion, device, realMobile);
  }
  
  @Test
  public void octaneTest() {
    testId = new String(Thread.currentThread().getStackTrace()[1].getMethodName()).hashCode();
    long startPageLoadTime = System.currentTimeMillis();
    OctanePage octanePage = OctanePage.visitPage(driver);
    long endPageLoadTime = System.currentTimeMillis();
    pageLoadTime = endPageLoadTime - startPageLoadTime;
    
    long startTestTime = System.currentTimeMillis();
    octanePage.runTest();
    octanePage.explicitWait();
    testTime = System.currentTimeMillis() - startTestTime;
    
    
    String score = octanePage.getScore();
    octaneScore = score.substring(14, score.length());
    
    LogEntries logEntries = octanePage.getIndividualScores();
    
    StringBuilder sb = new StringBuilder();
    for(LogEntry logEntry: logEntries) {
      String entry = logEntry.getMessage();
      int startIndex = entry.indexOf("\"");
      int endIndex = entry.lastIndexOf("\"");
      sb.append(entry.substring(startIndex+1, endIndex));
      sb.append(",");
    }
    individualScores = sb.toString();
  }
  
}

