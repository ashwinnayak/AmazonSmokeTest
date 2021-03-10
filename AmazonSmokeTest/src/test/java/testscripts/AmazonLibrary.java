package testscripts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class AmazonLibrary extends DriverScript {

    // Stores current window handle
    public static String currentWindowHandle;

    // Store method return result
    public static String methodReturnResult = null;

    // Site name
    public static String testSiteName = "Amazon.in";

    // Expected page titles
    public static String appLoginPageTitle = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";

    /*
     * .............. Name of the WebElements present on the WebPage
     * .................
     */

    /* .............. Locators for the test ................. */

    // Create a browser instance and navigate to the Test Site
    public static String navigateToAppWebsite() throws MalformedURLException, InterruptedException {

	APPLICATION_LOGS.debug("Creating a browser instance and navigating to the test site ...");

	// Disable log messages
	java.util.logging.Logger.getLogger("org.apache.http.impl.client").setLevel(java.util.logging.Level.WARNING);

	if (wbdv == null) {

	    try {

		if (CONFIG.getProperty("is_remote").equals("true")) {

		    // Generate Remote address
		    String remote_address = "http://" + CONFIG.getProperty("remote_ip") + ":4444/wd/hub";
		    remote_url = new URL(remote_address);

		    if (CONFIG.getProperty("test_browser").contains("Internet Explorer")) {

			dc = DesiredCapabilities.internetExplorer();
			dc.setCapability("silent", true);

		    }

		    else {

			ProfilesIni allProfiles = new ProfilesIni();
			// FirefoxProfile profile =
			// allProfiles.getProfile("default");
			// profile.setPreference("plugins.hide_infobar_for_missing_plugin",
			// true);
			dc = DesiredCapabilities.firefox();
			// dc.setCapability(FirefoxDriver.PROFILE, profile);
			// dc.setJavascriptEnabled(true);

		    }

		    wbdv = new RemoteWebDriver(remote_url, dc);
		    driver = new EventFiringWebDriver(wbdv);
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		}

		else {

		    if (CONFIG.getProperty("test_browser").toLowerCase().contains("internet explorer")
			    || CONFIG.getProperty("test_browser").toLowerCase().contains("ie")) {
			dc = DesiredCapabilities.internetExplorer();
			dc.setCapability("silent", true);
			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			wbdv = new InternetExplorerDriver(dc);
			driver = new EventFiringWebDriver(wbdv);
		    }

		    else if (CONFIG.getProperty("test_browser").toLowerCase().contains("firefox")
			    || CONFIG.getProperty("test_browser").toLowerCase().contains("ff")) {

			ProfilesIni allProfiles = new ProfilesIni();
			FirefoxProfile profile = allProfiles.getProfile("default");
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			wbdv = new FirefoxDriver(profile);
			driver = new EventFiringWebDriver(wbdv);

		    }

		    else if (CONFIG.getProperty("test_browser").toLowerCase().contains("safari")) {

			dc = DesiredCapabilities.safari();

		    }

		    else if (CONFIG.getProperty("test_browser").toLowerCase().contains("chrome")) {
			// Edit the path to your chrome driver in your system below!
			System.setProperty("chromedriver", "D:\\AllAutomationSetup\\BrowserDrivers\\chromedriver.exe");
			dc = DesiredCapabilities.chrome();
			wbdv = new ChromeDriver(dc);
			driver = new EventFiringWebDriver(wbdv);

		    }

		}

	    }

	    catch (Throwable initBrowserException) {

		APPLICATION_LOGS
			.debug("Error came while creating a browser instance : " + initBrowserException.getMessage());

		return failTest + " : Error came while creating a browser instance : "
			+ initBrowserException.getMessage();

	    }

	}

	APPLICATION_LOGS.debug("Created browser instance successfully");

	try {

	    // Implicitly wait for 30 seconds for browser to open
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	    // Delete all browser cookies
	    driver.manage().deleteAllCookies();

	    // Navigate to Curaspan application
	    driver.navigate().to(CONFIG.getProperty("testSiteURL"));

	    // Maximize browser window
	    // driver.manage().window().maximize();

	    // Handle certificate error
	    if (CONFIG.getProperty("test_browser").contains("Internet Explorer")) {

		if (driver.getTitle().contains(navigationBlockedTitle)) {

		    driver.navigate().to("javaScript:document.getElementById('overridelink').click()");

		    FunctionLibrary.waitForPageToLoad();

		}
	    }

	    // Maximize browser window
	    APPLICATION_LOGS.debug("Maximizing Browser window...");
	    driver.manage().window().maximize();
	    APPLICATION_LOGS.debug("Browser window is maximized");
	    //
	    // if (CONFIG.getProperty("test_browser").contains("Firefox")) {
	    // WebElement html = driver.findElement(By.tagName("html"));
	    // html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
	    // }

	}

	catch (Throwable navigationError) {

	    APPLICATION_LOGS.debug("Error came while navigating to the test site : " + navigationError.getMessage());
	    return failTest + " : Error came while navigating to the test site.";
	}

	Thread.sleep(3000L);

	// Verify Login page appears
	expectedTitle = appLoginPageTitle;
	methodReturnResult = FunctionLibrary.assertTitle(expectedTitle);
	if (methodReturnResult.contains(failTest)) {

	    // Log result
	    APPLICATION_LOGS.debug("Not navigated to the test site - " + testSiteName);
	    System.err.println("Not navigated to the test site - " + testSiteName);
	    return methodReturnResult;

	}

	APPLICATION_LOGS.debug("Navigated to the test site - " + testSiteName);
	return "Pass : Navigated to the test site - " + testSiteName;

    }

}
