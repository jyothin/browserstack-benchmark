package app;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class App {

  public static final String USERNAME = System.getenv("SAUCE_USERNAME");
  public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
  public static final String SAUCE_LABS_URL = "https://" + USERNAME + ":" + ACCESS_KEY + 
      "@ondemand.saucelabs.com:443/wd/hub";

  public static void main( String[] args ) throws Exception {
    
    System.setProperty("webdriver.chrome.driver",  "/Users/Jyothin/bin/chromedriver");
    
    WebDriver driver;
    
    DesiredCapabilities sauceLabsCaps = DesiredCapabilities.chrome();
    sauceLabsCaps.setCapability("platform", "Windows XP");
    sauceLabsCaps.setCapability("version", "43.0");
    driver = new RemoteWebDriver(new URL(SAUCE_LABS_URL), sauceLabsCaps);
    
    //driver.get("https://saucelabs.com/text/guinea-pig");
    driver.get("http://www.google.com");
    System.out.println(driver.getTitle());
    driver.findElement(By.name("q")).sendKeys("BrowserStack");
    driver.findElement(By.name("btnG")).click();
    System.out.println(driver.getTitle());
    Thread.sleep(5 * 1000);
    driver.get("http://example.com");
    System.out.println(driver.getTitle());
    Thread.sleep(5 * 1000);
    driver.quit();
    
    System.out.println("Hello World!");
  }
}
