package Tests.AbstractBaseTests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * An abstract base for all of the Android tests within this package
 * responsible for setting up the Appium test Driver
 */
public abstract class TestBase {

    private final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
    private final int TIMEOUT = 30;

    /**
     * Make the driver static. This allows it to be created only once
     * and used across all of the test classes.
     */
    public static AndroidDriver<MobileElement> driver;

    /**
     * This method runs before any other method.
     *
     * Appium follows a client - server model:
     * We are setting up our appium client in order to connect to Device Farm's appium server.
     *
     * We do not need to and SHOULD NOT set our own DesiredCapabilities
     * Device Farm creates custom settings at the server level. Setting your own DesiredCapabilities
     * will result in unexpected results and failures.
     *
     * @throws MalformedURLException An exception that occurs when the URL is wrong
     */
    @Before
    public void setUpAppium() throws MalformedURLException{

        URL url = new URL(URL_STRING);

	/*
	 * As mentioned in the documentation above we should not set the capabilities when 
	 * running on Device Farm's server. The settings made here will be ignored or may have
	 * unexpected results.
	 */
        driver = new AndroidDriver<MobileElement>(url, new DesiredCapabilities());

        //Use a higher value if your mobile elements take time to show up.
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * Always remember to quit.
     */
    @After
    public void tearDownAppium(){
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Restart the app after every test class to go back to the main
     * screen and to reset the behavior.
     */
    @AfterClass
    public void restartApp() {
    }
}
