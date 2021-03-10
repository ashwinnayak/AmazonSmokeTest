/**
    FunctionLibrary is a java class which contains basic webdriver methods e.g.
    1) Dealing with check-boxes - Check/UnCheck Check-box etc.
    2) Dealing with Input-boxes - Clear Input-box, Entering value into Input-box etc.
    3) Dealing with links - Click link
    4) Verify texts/conditions appear on the webpage - Verify text, verify partial text, verify number, verify page source etc.
    5) Wait methods - Wait for an element to load, wait for page to load, wait for new window to appear etc.

 */

package testscripts;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsRegistryException;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class FunctionLibrary extends DriverScript {

    public static String currentDateAndTimeGenerator() {

	String TimeFormat = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	return TimeFormat;

    }

    /**
     * Waits for check-box to appear on the page. Once appeared, highlight the
     * element and uncheck it if not checked already.
     * <p>
     * This method <code>returns</code>:
     * <ul>
     * <li>Pass if already unchecked or successfully unchecked</li>
     * <li>Fail if any exception occurs in between</li>
     * </ul>
     *
     * @param locator
     *                     Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String uncheckCheckBox(By locator, String elemName) {

	APPLICATION_LOGS.debug("Unchecking the checkbox : " + elemName);

	try {

	    // Highlight check-box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Wait for check-box to appear on the page
	    waitForElementToLoad(locator);

	    // UnCheck check-box if already checked
	    if (driver.findElement(locator).isSelected()) {
		driver.findElement(locator).click();
	    }

	    // Log the result
	    APPLICATION_LOGS.debug("Unchecked '" + elemName + "'");

	    return "Pass : Unchecked '" + elemName + "'";

	}

	catch (Throwable uncheckCheckBoxException) {

	    // Log the exception
	    APPLICATION_LOGS
		    .debug("Error came while unchecking '" + elemName + "' : " + uncheckCheckBoxException.getMessage());

	    return "Fail : Error came while unchecking '" + elemName + "' : " + uncheckCheckBoxException.getMessage();

	}

    }

    /**
     * Asserts whether expected text is present on the page or not. Returns Pass if
     * present on the page. Returns Fail if not present on the page.
     *
     * @param expText
     *                    Expected text
     *
     * @return Pass/Fail
     */

    public static String verifyTextPresent(String expText) {

	APPLICATION_LOGS.debug("Verifying Text : '" + expText + "' " + "present in the Page Source");

	try {

	    // Verify page source contains expected text
	    Assert.assertTrue(driver.getPageSource().contains(expText));

	    // Log result
	    APPLICATION_LOGS.debug("'" + expText + "' present in the Page Source");

	    return "Pass : '" + expText + "' present in the Page Source";

	}

	catch (Throwable verifyTextPresentError) {

	    // report error
	    APPLICATION_LOGS
		    .debug("Error while Verifying Text from Page Source : " + verifyTextPresentError.getMessage());

	    return "Fail : Error while Verifying Text from Page Source : " + verifyTextPresentError.getMessage();

	}

    }

    /**
     * Waits for element to appear on the page. Once appeared, highlight the element
     * and clicks on it. Returns Pass if able to click on the element. Returns Fail
     * if any exception occurs in between.
     *
     * @param locator
     *                     Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String clickLink(By locator, String elemName) {

	APPLICATION_LOGS.debug("Clicking on : " + elemName);

	try {

	    // Wait for link to appear on the page
	    waitForElementToLoad(locator);

	    // Highlight link
	    FunctionLibrary.highlightElement(driver, locator);

	    // Click on the link
	    driver.findElement(locator).click();

	    // Log result
	    APPLICATION_LOGS.debug("Clicked on : " + elemName);

	    return "Pass : Clicked on : " + elemName;

	}

	catch (Throwable clickLinkException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clicking on - " + elemName + " : " + clickLinkException.getMessage());

	    return "Fail : Error while clicking on - " + elemName + " : " + clickLinkException.getMessage();

	}

    }

    /**
     * Waits for element to appear on the page. Once appeared, highlight the element
     * and clicks on it. Returns Pass if able to click on the element. Returns Fail
     * if any exception occurs in between.
     *
     * @param parLoc
     *                     Parent Element locator
     * @param childLoc
     *                     Child Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String clickLink(By parentLoc, By childLoc, String elemName) {

	APPLICATION_LOGS.debug("Clicking on : " + elemName);

	try {

	    // Wait for parent element to load
	    waitForElementToLoad(parentLoc);

	    // Highlight element
	    FunctionLibrary.highlightElement(driver, childLoc);

	    // Click on the child element which is under parent element
	    driver.findElement(parentLoc).findElement(childLoc).click();

	    // Log result
	    APPLICATION_LOGS.debug("Clicked on : " + elemName);

	    return "Pass : Clicked on : " + elemName;

	}

	catch (Throwable clickLinkException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clicking on - " + elemName + " : " + clickLinkException.getMessage());

	    return "Fail : Error while clicking on - " + elemName + " : " + clickLinkException.getMessage();

	}

    }

    /**
     * Waits for input box to appear on the page. Once appeared, highlight and
     * clears the box. Returns Pass if Input box got cleared successfully. Returns
     * Fail if input box didn't clear or any exception occurs in between.
     *
     * @param locator
     *                     Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String clearField(By locator, String elemName) {

	APPLICATION_LOGS.debug("Clearing field : " + elemName);

	try {

	    // Wait for the input-box to load on the page
	    waitForElementToLoad(locator);

	    // Highlight the input-box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Clear the input-box
	    driver.findElement(locator).clear();

	    // Check whether input-box has been cleared or not
	    if (!driver.findElement(locator).getAttribute("value").isEmpty()) {
		driver.findElement(locator).clear();
	    }

	    // Log result
	    APPLICATION_LOGS.debug("Cleared : " + elemName);

	    return "Pass : Cleared : " + elemName;

	}

	catch (Throwable clearFieldException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clearing - " + elemName + " : " + clearFieldException.getMessage());
	    return "Fail : Error while clearing - " + elemName + " : " + clearFieldException.getMessage();

	}

    }

    /*
     *
     * public static String input(By locator,String elemName,String Value) method
     * specification :-
     *
     * 1) Inputs/sends value 2) locator -> identify the web element by
     * id,x-path,name,etc. 3) elemName -> the name of the web element where we
     * intend to input/send values 4) Value -> the string value which we intend to
     * input/send 5) waitForElementToLoad(locator) -> waits for web element to load
     * 6) driver.findElement(locator).sendKeys(Value) -> inputs/sends the value to
     * the intended web element
     *
     * @param : Locator for the input-box, name of the web element, value to be
     * inputted
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String input(By locator, String elemName, String Value) {

	APPLICATION_LOGS.debug("Sending Values in : " + elemName);

	try {

	    // Wait for the input box to appear on the page
	    waitForElementToLoad(locator);

	    // Highlight the input box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Send values to the input box
	    driver.findElement(locator).sendKeys(Value);

	    // Log result
	    APPLICATION_LOGS.debug("Inputted '" + Value + "' text into : '" + elemName + "'");

	    return "Pass : Inputted '" + Value + "' text into : '" + elemName + "'";

	}

	catch (Throwable inputException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while inputting into - '" + elemName + "' : " + inputException.getMessage());

	    return "Fail : Error while inputting into - '" + elemName + "' : " + inputException.getMessage();

	}

    }

    /*
     *
     * public static String clearAndInput(By locator,String elemName,String Value)
     * method specification :-
     *
     * 1) Clear and then Inputs/sends value 2) locator -> identify the web element
     * by id,x-path,name,etc. 3) elemName -> the name of the web element where we
     * intend to input/send values 4) Value -> the string value which we intend to
     * input/send 5) waitForElementToLoad(locator) -> waits for web element to load
     * 6) FunctionLibrary.clearField(locator, elemName); -> clears the input field
     * 7) driver.findElement(locator).sendKeys(Value) -> inputs/sends the value to
     * the intended web element
     *
     * @param : Locator for the input-box, name of the web element, value to be
     * inputted
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String clearAndInput(By locator, String elemName, String Value) {

	try {

	    // Wait for the input box to appear on the page
	    waitForElementToLoad(locator);

	    // Highlight the input box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Clear the input field before sending values
	    FunctionLibrary.clearField(locator, elemName);

	    // Send values to the input box
	    APPLICATION_LOGS.debug("Sending Values in : " + elemName);
	    driver.findElement(locator).sendKeys(Value);

	    // Log result
	    APPLICATION_LOGS.debug("Inputted '" + Value + "' text into : '" + elemName + "'");

	    return "Pass : Inputted '" + Value + "' text into : '" + elemName + "'";

	}

	catch (Throwable inputException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while inputting into - '" + elemName + "' : " + inputException.getMessage());

	    return "Fail : Error while inputting into - '" + elemName + "' : " + inputException.getMessage();

	}

    }

    /*
     *
     * public static String inputChord(By locator,String elemName,String Value)
     * method specification :-
     *
     * 1) Inputs/sends value in chord 2) locator -> identify the web element by
     * id,x-path,name,etc. 3) elemName -> the name of the web element where we
     * intend to input/send values 4) Value -> the string value which we intend to
     * input/send 5) waitForElementToLoad(locator) -> waits for web element to load
     * 6) driver.findElement(locator).sendKeys(Keys.chord(Value)) -> inputs/sends
     * the value in chord to the intended web element
     *
     * @param : Locator for the input-box, name of the web element, value to be
     * inputted
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String inputChord(By locator, String elemName, String Value) {

	APPLICATION_LOGS.debug("Sending Values in : " + elemName);

	try {

	    // Wait for input box to appear on the page
	    waitForElementToLoad(locator);

	    // Highlight input box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Send values in chord to the input box
	    driver.findElement(locator).sendKeys(Keys.chord(Value));

	    // Log result
	    APPLICATION_LOGS.debug("Inputted '" + Value + "' text into : '" + elemName + "'");
	    return "Pass : Inputted '" + Value + "' text into : '" + elemName + "'";

	}

	catch (Throwable inputException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while inputting into - '" + elemName + "' : " + inputException.getMessage());

	    return "Fail : Error while inputting into - '" + elemName + "' : " + inputException.getMessage();

	}

    }

    /**
     * public static String assertText(String elemName,String actValue, String
     * expValue) method specification :-
     *
     * 1) Verifies and returns TRUE if expected and actual text match 2) elemName ->
     * the name/type of text we intend to compare 3) actValue -> the actual string
     * value which is shown in the application 4) expValue -> the expected string
     * value which should be shown in the application 5)
     * Assert.assertEquals(expValue.trim(), actValue.trim())) -> trims and compares
     * the actual and expected string value
     *
     * @param :
     *            Name of the web element, Actual text and expected text
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String assertText(String elemName, String actValue, String expValue) {

	APPLICATION_LOGS.debug("Asserting text for : '" + elemName + "' where Expected text is '" + expValue
		+ "' and Actual text is '" + actValue + "'");

	try {

	    // Assert that expected value matches with actual value
	    Assert.assertEquals(expValue.trim(), actValue.trim());

	    // Log result
	    APPLICATION_LOGS.debug("Successfully asserted text for : '" + elemName + "' where Expected text is '"
		    + expValue + "' and Actual text is '" + actValue + "'");

	    return "Pass : Expected text matches with actual text";

	}

	catch (Throwable assertTextException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Asserting Text for - '" + elemName + "' : " + assertTextException.getMessage());

	    return "Fail : Error while Asserting Text for - '" + elemName + "' : " + assertTextException.getMessage();

	}

    }

    /*
     * public static String assertText(String elemName,int actValue, int expValue)
     * method specification :-
     *
     * 1) Verifies and returns TRUE if expected and actual text match 2) elemName ->
     * the name/type of text we intend to compare 3) actValue -> the actual string
     * value which is shown in the application 4) expValue -> the expected string
     * value which should be shown in the application 5)
     * Assert.assertEquals(expValue, actValue)) -> Compares the actual and expected
     * int value
     *
     * @param : Name of the web element, Actual text and expected text
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String assertText(String elemName, int actValue, int expValue) {

	APPLICATION_LOGS
		.debug("Asserting  Text  where : ExpectedText = " + expValue + "  and ActualText = " + actValue);

	try {

	    // Assert that expected value matches with actual value
	    Assert.assertEquals(expValue, actValue);

	    // Log result
	    APPLICATION_LOGS.debug("Successfully asserted text for : '" + elemName + "' where Expected text is '"
		    + expValue + "' and Actual text is '" + actValue + "'");

	    return "Pass : Expected text matches with actual text";

	}

	catch (Throwable assertTextException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Asserting Text for - '" + elemName + "' : " + assertTextException.getMessage());

	    return "Fail : Error while Asserting Text for - '" + elemName + "' : " + assertTextException.getMessage();

	}

    }

    /*
     * public static String assertCondition(String elemName,Boolean condition)
     * method specification :-
     *
     * 1) Verifies and returns TRUE if condition mentioned is true 2) elemName ->
     * Name of the web element for which we are asserting the condition 3) condition
     * -> Condition to be checked 5) Assert.assertTrue(condition) -> Asserts whether
     * the condition mentioned is TRUE or not
     *
     * @param : Name of the web element, condition to assert
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String assertCondition(String elemName, Boolean condition) {

	APPLICATION_LOGS.debug("Asserting Condition for : " + elemName);

	try {

	    // Assert that condition is true
	    Assert.assertTrue(condition);

	    // Log result
	    APPLICATION_LOGS.debug("Successfully asserted condition for : " + elemName);

	    return "Pass : Successfully asserted condition for : " + elemName;

	}

	catch (Throwable assertConditionException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while asserting Condition for - '" + elemName + "' : "
		    + assertConditionException.getMessage());

	    return "Fail : Error while asserting Condition for - '" + elemName + "' : "
		    + assertConditionException.getMessage();

	}

    }

    /*
     * public static String closePopupWindow() method specification :-
     *
     * 1) Closes the popup window 2) driver.close() -> closes the popup window which
     * has the current window handle 3) driver.switchTo().window(mainWindow) ->
     * switches back to main window by granting the current window handle to main
     * window
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String closePopupWindow() throws InterruptedException {

	APPLICATION_LOGS.debug("Closing pop-up window ...");

	try {

	    // Close current window pointed by webdriver
	    driver.close();

	    // Switch back to the main window
	    driver.switchTo().window(defaultWindow);

	    // Log result
	    APPLICATION_LOGS.debug("Closed pop-up window");

	    return "Pass : Closed pop-up window";

	}

	catch (Throwable closePopUpException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while closing pop-up window : " + closePopUpException.getMessage());

	    return "Fail : Error while closing pop-up window : " + closePopUpException.getMessage();

	}

    }

    /*
     * public static String switchToDefaultWindow() method specification :-
     *
     * 1) Switches back to the main window 2) driver.switchTo().window(mainWindow)
     * -> switches back to main window by granting the current window handle to main
     * window
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String switchToDefaultWindow() throws InterruptedException {

	APPLICATION_LOGS.debug("Switching to Default Window");

	try {

	    // Switch to main window
	    driver.switchTo().window(defaultWindow);

	    // Log result
	    APPLICATION_LOGS.debug("Switched to default window");

	    return "Pass : Switched to default window";

	}

	catch (Throwable switchToDefaultWindowException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while switching to default window : " + switchToDefaultWindowException.getMessage());

	    return "Fail : Error while switching to default window : " + switchToDefaultWindowException.getMessage();

	}

    }

    /*
     * public static String switchToPopupWindow() method specification :-
     *
     * 1) Switches to pop-up window 2) driver.getWindowHandle() -> Returns current
     * window handle 3) driver.getWindowHandles() -> Returns all the available
     * window handles 4) driver.switchTo().window(popUpWindowHandle) -> Switches to
     * pop-up window
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String switchToPopupWindow() throws InterruptedException {

	APPLICATION_LOGS.debug("Executing switchToPopupWindow");

	String popUpWindowHandle = null;

	try {

	    // Save current window handle for future reference
	    defaultWindow = driver.getWindowHandle();

	    // Get all the window handles one by one
	    for (String windowHandle : driver.getWindowHandles()) {

		// Save new window handle
		if (!windowHandle.equals(defaultWindow)) {

		    popUpWindowHandle = windowHandle;

		}

	    }

	    // Switches to pop-up window
	    driver.switchTo().window(popUpWindowHandle);

	    // Maximize browser window
	    driver.manage().window().maximize();

	    // Log result
	    APPLICATION_LOGS.debug("Switched to pop-up window");

	    return "Pass : Switched to pop-up window";

	}

	catch (Throwable switchToPopupWindowException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Switching to Pop Window : " + switchToPopupWindowException.getMessage());

	    return "Fail : Error while Switching to Pop Window : " + switchToPopupWindowException.getMessage();

	}

    }

    /*
     * public static String closeDriver() method specification :-
     *
     * 1) Closes the web driver 2) driver.close() -> Closes the webdriver
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String closeDriver() throws InterruptedException {

	APPLICATION_LOGS.debug("Closing the driver ...");

	try {

	    // Close the driver
	    // driver.close();
	    driver.quit();

	    // Make driver to point to null
	    wbdv = null;

	    // Close IEDriverServer processes if browser is IE
	    if (CONFIG.getProperty("test_browser").equals("InternetExplorer")) {

		APPLICATION_LOGS.debug("Killing IEDriverServer process");

		// Kill IEDriverServer from Remote machine
		String remote_ip = CONFIG.getProperty("remote_ip");
		String domain = CONFIG.getProperty("domain");
		String username = CONFIG.getProperty("username");
		String password = CONFIG.getProperty("password");
		String command = "pskill \\\\" + remote_ip + " -u " + domain + "\\" + username + "-p " + password
			+ " iedriverserver.exe";
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);
		int exitVal = proc.waitFor();

		// If failed to kill try once with this
		if (exitVal == -1) {

		    try {

			WindowsUtils.killByName("IEDriverServer.exe");
			APPLICATION_LOGS.debug("Killed IEDriverServer process");

		    }

		    catch (WindowsRegistryException taskKillException) {
			APPLICATION_LOGS.debug("IE Driver Server already killed. Skipping ...");
		    }

		}

		else {
		    APPLICATION_LOGS.debug("Killed IEDriverServer process");
		}

	    }

	    // Log result
	    APPLICATION_LOGS.debug("Closed the driver");

	    return "Pass : Closed the driver";

	}

	catch (Throwable closeDriverException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error came while closing driver : " + closeDriverException.getMessage());

	    return "Fail : Error came while closing driver : " + closeDriverException.getMessage();

	}

    }

    /*
     * public static String selectValueByVisibleText(By Locator, String Option,
     * String elemName) method specification :-
     *
     * 1) Select value from drop-down by visible text 2) Select -> This is a
     * in-built class in Selenium which is used to represent a drop-down 3)
     * select.selectByVisibleText(Value) -> Select by visible text
     *
     * @param : Locator for the drop-down field, Option to be selected, Name of the
     * web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String selectValueByVisibleText(By Locator, String Option, String elemName) {

	APPLICATION_LOGS.debug("Selecting '" + Option + "' from : " + elemName);

	try {

	    // Wait for drop-down element to load on the page
	    waitForElementToLoad(Locator);

	    // Highlight the drop-down
	    FunctionLibrary.highlightElement(driver, Locator);

	    // Locate drop-down field
	    Select select = new Select(driver.findElement(Locator));

	    // Select value from drop-down
	    select.selectByVisibleText(Option);

	    // Log result
	    APPLICATION_LOGS.debug("Selected '" + Option + "' from : " + elemName);

	    return "Pass : Selected '" + Option + "' from : " + elemName;

	}

	catch (Throwable selectValueException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while Selecting Value from - '" + elemName + "' : " + selectValueException.getMessage());

	    return "Fail : Error while Selecting Value from - '" + elemName + "' : "
		    + selectValueException.getMessage();

	}

    }

    /*
     * public static String selectValueByIndex(By Locator, int index, String
     * elemName) method specification :-
     *
     * 1) Select value from drop-down by index 2) Select -> This is a in-built class
     * in Selenium which is used to represent a drop-down 3)
     * select.selectByIndex(index) -> Select by index
     *
     * @param : Locator for the drop-down field, Index for the option to be
     * selected, Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String selectValueByIndex(By Locator, int index, String elemName) {

	APPLICATION_LOGS.debug("Selecting value from : " + elemName);

	try {

	    // Wait for drop-down element to load on the page
	    waitForElementToLoad(Locator);

	    // Highlight the drop-down
	    FunctionLibrary.highlightElement(driver, Locator);

	    // Locate drop-down field
	    Select select = new Select(driver.findElement(Locator));

	    // Select value from drop-down
	    select.selectByIndex(index);

	    // Log result
	    APPLICATION_LOGS.debug("Selected value from : " + elemName);

	    return "Pass : Selected value from : " + elemName;

	}

	catch (Throwable selectValueException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while Selecting Value from - '" + elemName + "' : " + selectValueException.getMessage());

	    return "Fail : Error while Selecting Value from - '" + elemName + "' : "
		    + selectValueException.getMessage();

	}

    }

    /*
     * public static String retrieveText(By locator,String elemName) method
     * specification :-
     *
     * 1) Return retrieved text from webpage 2)
     * driver.findElement(locator).getText() -> Retrieves text from the web element
     * targeted by specified locator
     *
     * @param : Locator for the web element, Name of the web element
     *
     * @return : Text retrieved from the webpage
     */

    public static String retrieveText(By locator, String elemName) {

	String retrievedText = null;

	APPLICATION_LOGS.debug("Retrieving Text from : " + elemName);

	try {

	    // Wait for web element to load on the page
	    waitForElementToLoad(locator);

	    // Highlight the web element
	    FunctionLibrary.highlightElement(driver, locator);

	    // Retrieve text from web element
	    retrievedText = driver.findElement(locator).getText().trim();

	    // Log result
	    APPLICATION_LOGS.debug("Retrieved text : " + retrievedText);

	}

	catch (Throwable retrieveTextException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage());

	}

	return retrievedText;

    }

    /*
     * public static String retrieveAttributeValue(By locator,String value,String
     * elemName) method specification :-
     *
     * 1) Return retrieved HTML attribute value from webpage 2)
     * driver.findElement(locator).getAttribute(value) -> Retrieves attribute
     * (present under a web element) value
     *
     * @param : Locator for the web element, Attribute name, Name of the web element
     *
     * @return : Attribute value retrieved
     */

    public static String retrieveAttributeValue(By locator, String value, String elemName) {

	String attributeValue = null;

	APPLICATION_LOGS.debug("Getting Attribute '" + value + "'  Value from - " + elemName);

	try {

	    // Wait for web element to load
	    waitForElementToLoad(locator);

	    // Highlight the web element
	    FunctionLibrary.highlightElement(driver, locator);

	    // Get attribute value for the web element
	    attributeValue = driver.findElement(locator).getAttribute(value);

	    // Log result
	    APPLICATION_LOGS.debug("Got Attribute '" + value + "'  Value from : " + elemName + " : " + attributeValue);

	}

	catch (Throwable retrieveAttributeValueException) {

	    // report error
	    APPLICATION_LOGS.debug("Error while Getting Attribute '" + value + "' value from '" + elemName + "' : "
		    + retrieveAttributeValueException.getMessage());

	    return "Fail : Error while Getting Attribute '" + value + "' value from '" + elemName + "' : "
		    + retrieveAttributeValueException.getMessage();

	}

	return attributeValue;

    }

    /*
     * public static List<WebElement> createWebList(By locator,String elemName)
     * method specification :-
     *
     * 1) Return a list of web elements 2) driver.findElements(locator) -> Returns
     * of list of web elements located by locator
     *
     * @param : Locator for the web element, Name of the web element
     *
     * @return : List of web elements
     */

    public static List<WebElement> createWebList(By locator, String elemName) {

	APPLICATION_LOGS.debug("Creating WebList for : " + elemName);

	List<WebElement> list = null;

	try {

	    // Find the list of web elements
	    list = driver.findElements(locator);

	    // Log result
	    APPLICATION_LOGS
		    .debug("Created WebList for : " + elemName + ". Weblist contains " + list.size() + " elements");

	}

	catch (Throwable createWebListException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while Creating WebList reference for '" + elemName + "' : "
		    + createWebListException.getMessage());

	}

	return list;

    }

    /*
     * public static List<WebElement> createWebList(By parentLoc,By childLoc,String
     * elemName) method specification :-
     *
     * 1) Return a list of web elements 2)
     * driver.findElement(parentLoc).findElements(childLoc) -> Returns of list of
     * web elements located by locator which present under parent web element
     *
     * @param : Locator for the parent web element, Locator for the child web
     * element, Name of the web element
     *
     * @return : List of web elements
     */

    public static List<WebElement> createWebList(By parentLoc, By childLoc, String elemName) {

	List<WebElement> list = null;

	APPLICATION_LOGS.debug("Creating WebList for : " + elemName);

	try {

	    // Wait for parent web element to load
	    waitForElementToLoad(parentLoc);

	    // Highlight parent web element
	    FunctionLibrary.highlightElement(driver, parentLoc);

	    // Find all the child web elements under parent web element
	    list = driver.findElement(parentLoc).findElements(childLoc);

	    // Log result
	    APPLICATION_LOGS.debug("Created WebList for : " + elemName);

	}

	catch (Throwable createWebListException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while Creating WebList reference for '" + elemName + "' : "
		    + createWebListException.getMessage());

	}

	return list;

    }

    /*
     * public static void waitForNewWindow(int prevWndCount) method specification :-
     *
     * 1) Waits for a new window to appear 2) new WebDriverWait(driver, 60) -> Waits
     * for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait until
     * expected condition (Window count increases) met 4)
     * d.getWindowHandles().size() -> Returns number of window handles present
     *
     * @param : Previous window count
     *
     * @return : void
     */

    public static void waitForNewWindow(int prevWndCount) {

	final int currWndCount = prevWndCount;

	try {

	    // Waits for 60 seconds
	    WebDriverWait wait = new WebDriverWait(driver, 60);

	    // Wait until expected condition (Window count increases) met
	    wait.until(new ExpectedCondition<Boolean>() {

		public Boolean apply(WebDriver d) {

		    // Return true if window count increases, else return false
		    return d.getWindowHandles().size() > currWndCount;

		}

	    });

	}

	catch (Throwable waitForNewWindowException) {
	    APPLICATION_LOGS.debug(
		    "Exception while waiting for new window to appear : " + waitForNewWindowException.getMessage());
	}

    }

    /*
     * public static void waitForPageToLoad() method specification :-
     *
     * 1) Waits for a new page to load completely 2) new WebDriverWait(driver, 60)
     * -> Waits for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait
     * until expected condition (All documents present on the page get ready) met
     *
     * @param : no parameters passed
     *
     * @return : void
     */

    public static void waitForPageToLoad() throws InterruptedException {

	try {

	    // Waits for 60 seconds
	    WebDriverWait wait = new WebDriverWait(driver, 60);
	    // Wait until expected condition (All documents present on the page
	    // get ready) met
	    wait.until(new ExpectedCondition<Boolean>() {

		public Boolean apply(WebDriver d) {

		    if (!(d instanceof JavascriptExecutor)) {
			return true;
		    }

		    Object result = ((JavascriptExecutor) d)
			    .executeScript("return document['readyState'] ? 'complete' == document.readyState : true");

		    if (result != null && result instanceof Boolean && (Boolean) result) {
			return true;
		    }

		    return false;

		}

	    });

	}

	catch (Throwable waitForPageToLoadException) {
	    APPLICATION_LOGS
		    .debug("Error came while waiting for page to load : " + waitForPageToLoadException.getMessage());
	}

    }

    /*
     * public static void waitForElementToLoad(By locator) method specification :-
     *
     * 1) Waits for the web element to appear on the page 2) new
     * WebDriverWait(driver, 60) -> Waits for 60 seconds 3)
     * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All
     * documents present on the page get ready) met
     *
     * @param : no parameters passed
     *
     * @return : void
     */

    public static void waitForElementToLoad(final By locator) {

	APPLICATION_LOGS.debug("Waiting for web element to load on the page");

	try {

	    // Waits for 60 seconds
	    Wait<WebDriver> wait = new WebDriverWait(driver, 60);

	    // Wait until the element is located on the page
	    @SuppressWarnings("unused")
	    WebElement element = wait.until(visibilityOfElementLocated(locator));

	    // Log result
	    APPLICATION_LOGS.debug("Waiting ends ... Web element loaded on the page");

	}

	catch (Throwable waitForElementException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error came while waiting for element to appear : " + waitForElementException.getMessage());

	}

    }

    /*
     * public static ExpectedCondition<WebElement> visibilityOfElementLocated(final
     * By locator) method specification :-
     *
     * 1) Waits for the web element to appear on the page 2) WebElement
     * toReturn.isDisplayed() -> Returns true if displayed on the page, else returns
     * false
     *
     * @param : Locator to locate the web element
     *
     * @return : ExpectedCondition about the web element
     */

    public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {

	return new ExpectedCondition<WebElement>() {

	    public WebElement apply(WebDriver driver) {

		// Highlight the web element
		FunctionLibrary.highlightElement(driver, locator);

		// Store the web element
		WebElement toReturn = driver.findElement(locator);

		// Check whether the web element is displayed on the page
		if (toReturn.isDisplayed()) {
		    return toReturn;
		}

		return null;

	    }

	};

    }

    /*
     * public static void waitForElementToDisappear(By locator) method specification
     * :-
     *
     * 1) Waits for the web element to appear on the page 2) new
     * WebDriverWait(driver, 60) -> Waits for 60 seconds 3)
     * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All
     * documents present on the page get ready) met
     *
     * @param : locator for the web element
     *
     * @return : void
     */

    public static void waitForElementToDisappear(final By locator, String elemName) {

	APPLICATION_LOGS.debug("Waiting for " + elemName + " to disappear ...");

	try {

	    // Waits for 60 seconds
	    Wait<WebDriver> wait = new WebDriverWait(driver, 60);

	    // Wait until the element get disappeared
	    @SuppressWarnings("unused")
	    /*
	     * WebElement element = wait .until(ElementLocatedToGetDisappear(locator));
	     */
	    WebElement element = wait.until(ElementLocatedToGetDisappear(locator));

	    // Log result
	    APPLICATION_LOGS.debug("Waiting ends ... " + elemName + " disappeared");

	}

	catch (Throwable waitForElementException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error came while waiting for element to disappear : " + waitForElementException.getMessage());

	}

    }

    /*
     * public static ExpectedCondition<WebElement>
     * ElementLocatedToGetDisappear(final By locator) method specification :-
     *
     * 1) Waits for the web element to disappear on the page 2) WebElement
     * toReturn.isDisplayed() -> Returns true if displayed on the page, else returns
     * false
     *
     * @param : Locator to locate the web element
     *
     * @return : ExpectedCondition about the web element
     */

    public static ExpectedCondition<WebElement> ElementLocatedToGetDisappear(final By locator) {

	return new ExpectedCondition<WebElement>() {

	    public WebElement apply(WebDriver driver) {

		// Store the web element
		WebElement toReturn = driver.findElement(locator);

		// Check whether the web element is disappeared
		if (!toReturn.isDisplayed()) {
		    return toReturn;
		}

		return null;

	    }

	};

    }

    /*
     * public static boolean verifyNumber(String elemName,int actValue, int
     * expValue) method specification :-
     *
     * 1) Verify number on the page 2)
     *
     * @param : Name of the web element, Actual value in integer, Expected value in
     * integer
     *
     * @return : Boolean - true if verified successfully else false
     */

    public static boolean verifyNumber(String elemName, int actValue, int expValue) {

	APPLICATION_LOGS.debug("Verifying Number for : " + elemName);

	try {

	    // Assert whether actual value matches with expected
	    Assert.assertEquals(expValue, actValue);

	    // Log result
	    APPLICATION_LOGS.debug(
		    "Actual value '" + actValue + "' matches with Expected value '" + expValue + "' for : " + elemName);

	    return true;

	}

	catch (Throwable verifyNumberException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while Verifying Number for '" + elemName + "' : " + verifyNumberException.getMessage());

	    return false;

	}

    }

    /*
     * public static String acceptAlert(String elemName) method specification :-
     *
     * 1) Accepts an alert 2) driver.switchTo().alert() -> Switch to the desired
     * alert 3) alert.accept() -> Accepts the alert
     *
     * @param : Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String acceptAlert(String elemName) {

	APPLICATION_LOGS.debug("Accepting alert : " + elemName);

	try {

	    // Create a new alert object
	    Alert alert = driver.switchTo().alert();

	    // Accept the alert
	    alert.accept();

	    // Log result
	    APPLICATION_LOGS.debug("Accepted alert : " + elemName);

	    return "Pass : Accepted the alert '" + elemName + "'";

	}

	catch (Throwable acceptAlertException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error came while accepting alert : " + acceptAlertException.getMessage());

	    return "Fail : Error came while accepting alert : " + acceptAlertException.getMessage();

	}

    }

    /*
     * public static void clickAndWait(By locator,String elemName) method
     * specification :-
     *
     * 1) Click and wait for next page to load 2)
     * driver.findElement(locator).click() -> Clicks on the web element targeted by
     * locator
     *
     * @param : Locator to locate the web element, Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String clickAndWait(By locator, String elemName) {

	try {

	    // Click on the web element targeted by locator
	    methodReturnResult = FunctionLibrary.clickLink(locator, elemName);
	    if (methodReturnResult.contains(failTest)) {
		return methodReturnResult;
	    }

	    // Wait for new page to load
	    FunctionLibrary.waitForPageToLoad();

	    // Log result
	    APPLICATION_LOGS.debug(
		    "Clicked on the element : " + elemName + " and new page loaded with title : " + driver.getTitle());

	    return "Pass : Clicked on the element : " + elemName + " and new page loaded with title : "
		    + driver.getTitle();

	}

	catch (Throwable clickAndWaitException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clicking on " + elemName + " and waiting for new page to load : "
		    + clickAndWaitException.getMessage());

	    return "Error while clicking on link " + elemName + " and waiting for new page to load : "
		    + clickAndWaitException.getMessage();

	}

    }

    /*
     * public static String assertTitle(String expectedTitle) method specification
     * :-
     *
     * 1) Asserts page title 2) driver.getTitle() -> Retrieves page title 3)
     * Assert.assertEquals() -> Asserts for equality
     *
     * @param : Expected title to assert
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String assertTitle(String expectedTitle) {

	String actualTitle = null;

	APPLICATION_LOGS.debug("Asserting  title  where : Expected title = " + expectedTitle);

	try {

	    // Fetch actual title of the webpage
	    actualTitle = driver.getTitle();

	    // Asserts whether actual title matches with expected one
	    Assert.assertEquals(expectedTitle.trim(), actualTitle.trim());

	    // Log result
	    APPLICATION_LOGS
		    .debug("Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle);

	    return "Pass : Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle;

	}

	catch (Throwable assertTitleException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while asserting title : " + assertTitleException.getMessage());

	    return "Fail : Error while asserting title : " + assertTitleException.getMessage();

	}

    }

    /*
     * public static String assertAlertAndAccept(String expectedAlertText) method
     * specification :-
     *
     * driver.switchTo().alert() -> Switch to the alert appeared on the page 3)
     * Assert.assertEquals() -> Asserts for equality 4) alert.accept() -> Accepts
     * the alert
     *
     * @param : Expected alert text to assert
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String assertAlertAndAccept(String expectedAlertText) {

	APPLICATION_LOGS.debug("Asserting alert text : " + expectedAlertText);

	String actualAlertText = null;
	Alert alert = null;

	try {

	    Thread.sleep(3000L);

	    // Switch control to alert
	    alert = driver.switchTo().alert();

	    // Get the actual alert message
	    actualAlertText = alert.getText();

	    // Assert alert message
	    Assert.assertEquals(expectedAlertText.trim(), actualAlertText.trim());
	    Thread.sleep(5000L);

	    // Accept alert message
	    alert.accept();
	    Thread.sleep(5000L);

	    // log result
	    APPLICATION_LOGS.debug("Success : got the alert message saying : " + actualAlertText);

	    return "Pass : got the alert message saying : '" + actualAlertText;

	}

	catch (Throwable alertExcpetion) {

	    APPLICATION_LOGS.debug("Error came while asserting alert and accepting : " + alertExcpetion.getMessage());
	    return "Fail : Error came while asserting alert and accepting : " + alertExcpetion.getMessage();

	}

    }

    // highlight the element on which action will be performed
    public static void highlightElement(WebDriver driver, By Locator) {

	/*
	 * if (highlightElement) { try {
	 * 
	 * for (int i = 0; i < 3; i++) { JavascriptExecutor js = (JavascriptExecutor)
	 * driver; js.executeScript(
	 * "arguments[0].setAttribute('style', arguments[1]);",
	 * driver.findElement(Locator), "color: red; border: 2px solid red;");
	 * 
	 * }
	 * 
	 * }
	 * 
	 * catch (Throwable t) { APPLICATION_LOGS.debug("Error came : " +
	 * t.getMessage()); } }
	 */

    }

    /*
     * public static String checkCheckBox(By locator, String elemName) method
     * specification :
     *
     * 1) Checks a check-box if it is not checked already 2) if
     * (!driver.findElement(locator).isSelected()) {
     * driver.findElement(locator).click() : Checks the checkbox if it is not
     * checked already 3) String elemName : Passed as a parameter to name the
     * element
     */
    public static String checkCheckBox(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking : " + elemName);

	try {

	    // Wait for element to load
	    waitForElementToLoad(locator);

	    // Highlight the element
	    FunctionLibrary.highlightElement(driver, locator);

	    // Select the element if not selected already
	    if (!driver.findElement(locator).isSelected()) {

		driver.findElement(locator).click();

		APPLICATION_LOGS.debug("Checked " + elemName);
		return "Pass : Checked " + elemName;

	    }

	    else {

		APPLICATION_LOGS.debug(elemName + " is already checked");

		return "Pass : " + elemName + " is already checked";

	    }

	}

	catch (Throwable checkCheckBoxException) {

	    APPLICATION_LOGS.debug("Error while checking -" + elemName + checkCheckBoxException.getMessage());
	    return "Fail : Error while checking -" + elemName + checkCheckBoxException.getMessage();

	}

    }

    /*
     *
     * public static Boolean assertPartialText(String elemName, String expValue,
     * String actValue) method specification :
     *
     * 1) This method is for verifying presence of a sub-string in between a larger
     * string 2) String elemName : Passed as a parameter for naming the element 3)
     * String expValue : Passed as a parameter for storing the expected value 4)
     * String actValue : Passed as a parameter for storing the actual value 5)
     * Boolean check = actValue.trim().contains(expValue.trim()) : Checks if actual
     * text contains the expected text
     */

    public static Boolean assertPartialText(String elemName, String expValue, String actValue) {

	APPLICATION_LOGS.debug("Asserting Partial Text for: " + elemName);

	try {

	    Boolean check = actValue.trim().contains(expValue.trim());

	    if (check) {

		APPLICATION_LOGS.debug("Expected text - " + expValue + " is found to be present within Actual text - "
			+ actValue + " for : " + elemName);
		return true;

	    }

	    else {

		APPLICATION_LOGS.debug("Expected text : " + expValue + " is not present within Actual text : "
			+ actValue + " for - " + elemName);
		return false;

	    }

	}

	catch (Throwable assertPartialTextException) {

	    APPLICATION_LOGS.debug(
		    "Error while Verifying Partial Text for : " + elemName + assertPartialTextException.getMessage());
	    return false;

	}

    }

    /*
     * public static void inputInteger(By locator, String elemName, int Value)
     * method specification
     *
     * 1) This method converts integer to string and inputs to text box 2) String
     * elemName : A parameter passed to take the element name 3) int Value : Another
     * parameter passed to take an integer value and input it 4)
     * driver.findElement(locator).sendKeys(Value1) : Finds the element and enters
     * the value passed via 'Value' parameter
     */
    public static void inputInteger(By locator, String elemName, int Value) {

	APPLICATION_LOGS.debug("Sending Values in : " + elemName);

	try {

	    waitForElementToLoad(locator);
	    String Value1 = Integer.toString(Value);
	    driver.findElement(locator).sendKeys(Value1);

	}

	catch (Throwable t) {

	    APPLICATION_LOGS.debug("Error while Sending Values in:  -" + elemName + t.getMessage());
	}

    }

    /*
     * public static String getDateAndTimeOfSpecificTimeZone( String
     * dateAndTimeFormat, String timeZone) method specification :
     *
     * 1) This function gets date and time of a specific time zone 2) String
     * dateAndTimeFormat : A parameter passed in the function which takes the
     * Date/Time format we want 3) String timeZone : Another parameter passed
     * mentioning the timezone for which we want the date/time 4) Date date = new
     * Date() : Locale date and time 5)
     * formatter.setTimeZone(TimeZone.getTimeZone(timeZone)) : Get US/Eastern time
     * 6) return dateAndTime : Prints the date in the US timezone
     */

    public static String getDateAndTimeOfSpecificTimeZone(String dateAndTimeFormat, String timeZone) {

	APPLICATION_LOGS.debug("Getting date and time of specified time zone ...");

	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat(dateAndTimeFormat);
	formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
	String dateAndTime = formatter.format(date);

	APPLICATION_LOGS.debug(
		"Got date and time specific to timezone. Timezone = " + timeZone + " and DateTime = " + dateAndTime);

	return dateAndTime;

    }

    /*
     *
     * public static void maximizeWindow() method specification : -
     *
     * 1) Maximize the currently opened browser window 2)
     * driver.manage().window().maximize() : Maximize browser window
     */
    public static void maximizeWindow() {

	APPLICATION_LOGS.debug("Maximizing the window ...");

	try {

	    driver.manage().window().maximize();
	    APPLICATION_LOGS.debug("Browser window successfully maximized");

	}

	catch (Throwable windowMaximizeException) {

	    APPLICATION_LOGS.debug("Exception came while maximizing window : " + windowMaximizeException.getMessage());

	}

    }

    /*
     * public static void refreshPage() method specification : -
     *
     * 1) Refresh the page 2) driver.navigate().refresh : This is used to refresh
     * the current page
     */
    public static void refreshPage() {

	APPLICATION_LOGS.debug("Refreshing the page ...");

	try {

	    driver.navigate().to(driver.getCurrentUrl());
	    APPLICATION_LOGS.debug("Page successfully refreshed");

	}

	catch (Throwable pageRefreshException) {

	    APPLICATION_LOGS.debug("Exception came while refreshing page : " + pageRefreshException.getMessage());

	}

    }

    // Function for Selecting Value from Dropdown
    public static void selectValue(By Locator, String Value, String elemName) {

	APPLICATION_LOGS.debug("Selecting Value from : " + elemName);

	try {

	    waitForElementToLoad(Locator);
	    FunctionLibrary.highlightElement(driver, Locator);
	    Select select = new Select(driver.findElement(Locator));
	    select.selectByVisibleText(Value);
	}

	catch (Exception e) {
	    APPLICATION_LOGS.debug("Error while Selecting Value from :   -" + elemName + e.getMessage());
	}

    }

    // Generate randomn characters
    public static String randomnGenerator() {

	int PASSWORD_LENGTH = 8;
	String randomnCharacters;
	StringBuffer sb = new StringBuffer();

	for (int x = 0; x < PASSWORD_LENGTH; x++) {

	    sb.append((char) ((int) (Math.random() * 26) + 97));

	}

	randomnCharacters = sb.toString();
	return randomnCharacters;

    }

    /*
     *
     * Generate randomn numbers within the range provided inside the method argument
     *
     * @param : Min Value in the range, Max Value in the range
     *
     * @return : Random number generated
     */

    public static String randomnNumberGenerator(int minValue, int maxValue) {

	int randomnNumber;
	String numbers;
	randomnNumber = (int) (minValue + ((new Random()).nextDouble() * (maxValue - minValue)));
	numbers = Integer.toString(randomnNumber);
	return numbers;

    }

    /*
     * public static void isChecked(By locator, String elemName) method
     * specification :-
     *
     * 1) Verifies whether a Checkbox is checked or not 2) locator -> to locate the
     * element by id,x-path,name,etc. 3) elemName -> the name/type of the check-box
     * which we intend to check 4) driver.findElement(locator).isSelected() -> is to
     * verify whether the intended checkbox is checked or not
     *
     * @param : Locator for the Check-box, name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static Boolean isChecked(By locator, String elemName) {

	APPLICATION_LOGS.debug("Verifying is the checkbox checked : " + elemName);

	Boolean isChecked;

	try {

	    // Highlight check-box
	    FunctionLibrary.highlightElement(driver, locator);

	    // Wait for check-box to appear on the page
	    waitForElementToLoad(locator);

	    // Check whether web element is displayed or not
	    isChecked = driver.findElement(locator).isSelected();

	    if (isChecked) {
		// Log the result
		APPLICATION_LOGS.debug("'" + elemName + "' is checked");
	    } else {
		// Log the result
		APPLICATION_LOGS.debug("'" + elemName + "' is not checked");
	    }
	    return isChecked;

	}

	catch (Throwable ischeckCheckBoxException) {

	    // Log the exception
	    APPLICATION_LOGS.debug("Error while verifying checkbox is checked '" + elemName + "' : "
		    + ischeckCheckBoxException.getMessage());

	    return false;
	}
    }

    // Scroll webpage
    public static String scrollWebPage(int pixelToScrollHorizontally, int pixelToScrollVertically) {

	APPLICATION_LOGS.debug("Scrolling through web page ... ");

	// Scroll web page
	try {
	    (driver).executeScript("scroll(" + pixelToScrollHorizontally + "," + pixelToScrollVertically + ")");
	}

	catch (Throwable webPageScrollException) {

	    // Log the exception
	    APPLICATION_LOGS
		    .debug("Error while scrolling through the web page : " + webPageScrollException.getMessage());

	    return "Fail : Error while scrolling through the web page : " + webPageScrollException.getMessage();

	}

	APPLICATION_LOGS.debug("Scrolled through web page");

	return "Pass : Scrolled through the web page";

    }

    // Move cursor
    public static void moveCursor() throws AWTException, InterruptedException {

	APPLICATION_LOGS.debug("Moving cursor ...");

	// Declare start and end point of the cursor
	int endX;
	int endY;
	int startX;
	int startY;

	// Use Robot utility to move cursor
	Robot robot = new Robot();
	Point startLocation = MouseInfo.getPointerInfo().getLocation();
	startX = startLocation.x;
	startY = startLocation.y;

	endX = startX + 500;
	endY = startY + 500;

	robot.mouseMove(endX, endY);

	APPLICATION_LOGS.debug("Cursor has been moved");

    }

    // Horizontal DIV Scroller
    public static String divScrollerRight(String ID, String elemName) {

	APPLICATION_LOGS.debug("Horizontal right scroll inside DIV : " + elemName);

	String result = null;

	try {

	    // Scroll DIV towards right
	    driver.executeScript("document.getElementById('" + ID + "').scrollLeft = 1000");
	    result = "Pass: " + "Scrolled successfully towards right";

	}

	catch (Throwable isrightScrollxException) {

	    // Log the exception
	    APPLICATION_LOGS.debug("Error while scrolling horizontally towards right: '" + elemName + "' : "
		    + isrightScrollxException.getMessage());

	    result = "Error while scrolling horizontally towards right: '" + elemName + "' : "
		    + isrightScrollxException.getMessage();

	}
	return result;

    }

    /*
     * public static String convertStringToDate(String dateString, String
     * dateFormat) method specification :-
     *
     * 1) Returns date converted from a String format 2) SimpleDateFormat(
     * "MMMM dd, yyyy").parse(retrievedString) -> Converts String to Date format
     *
     * @param : date in string format, date format
     *
     * @return : Date which is being converted from String format
     */

    public static Date convertStringToDate(String dateString, String dateFormat) {

	Date date = null;

	APPLICATION_LOGS.debug("Converting string to date format ...");

	try {

	    // Convert to date
	    date = new SimpleDateFormat(dateFormat).parse(dateString);

	    // Log result
	    APPLICATION_LOGS.debug("Date retrieved : " + date);

	}

	catch (Throwable convertStringToDateException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while converting String to Date format : " + convertStringToDateException.getMessage());

	}

	return date;

    }

    /*
     * public static Boolean compareDates(Date date1, Date date2, String elemName)
     * method specification :-
     *
     * 1) Compares 2 dates 2) date1.equals(date2) -> Compares date1 and date2
     *
     * @param : First date to be compared, Second date to be compared, Name of the
     * web element
     *
     * @return : True if dates are matched
     */

    public static Boolean compareDates(Date date1, Date date2, String elemName) {

	Boolean result = null;
	APPLICATION_LOGS.debug("Comparing dates : " + elemName);

	try {

	    // Compare dates
	    if (date1.equals(date2)) {

		// Log result
		APPLICATION_LOGS.debug("Date matched");
		result = true;

	    }

	    else {

		// Log result
		APPLICATION_LOGS.debug("Date didn't match");
		result = false;

	    }

	}

	catch (Throwable compareDates) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while comparing dates '" + elemName + "' : " + compareDates.getMessage());

	}

	if (result) {
	    return true;
	} else {
	    return false;
	}

    }

    /*
     * public static String getFormattedDate(DateFormat dateFormat) method
     * specification :
     *
     * 1) This function gets date in the specified format 2) Date date = new Date()
     * : Locale date and time 3) return currentSystemDate : Returns current syatem
     * date and time
     */

    public static String getFormattedDate(DateFormat dateFormat) {

	APPLICATION_LOGS.debug("Getting date ...");

	String currentSystemDate = null;
	Date date = new Date();
	currentSystemDate = dateFormat.format(date);

	APPLICATION_LOGS.debug("Got formatted date : " + currentSystemDate);

	return currentSystemDate;

    }

    /*
     * public static String getFormattedDate(DateFormat dateFormat, int dayCount)
     * method specification :
     *
     * 1) This function gets date (Current/Past/Future) in the specified format 2)
     * Calendar date = new GregorianCalendar() : Locale date and time 3)
     * date.add(Calendar.DATE, dayCount) : Get Past/Current/Future date 4) return
     * formattedDate : Formatted past/current/future date
     *
     * @param dateFormat [Date formatter]
     *
     * @param dayCount [Count of days (Current/Past/Future) e.g. 0 - For current
     * system datetime, (+)daycount - For future datetime, (-)daycount - For past
     * datetime]
     *
     * @return Formatted past/current/future date
     */

    public static String getFormattedDate(DateFormat dateFormat, int dayCount) {

	if (dayCount < 0) {
	    APPLICATION_LOGS.debug("Getting date of " + (-dayCount) + " days before ...");
	} else if (dayCount == 0) {
	    APPLICATION_LOGS.debug("Getting current system date ...");
	} else {
	    APPLICATION_LOGS.debug("Getting date of " + dayCount + " days after ...");
	}

	// Get current system date and time
	Calendar date = new GregorianCalendar();

	// Get Past/Current/Future date
	date.add(Calendar.DATE, dayCount);

	// Format date
	String formattedDate = dateFormat.format(date.getTime());

	APPLICATION_LOGS.debug("Got formatted date : " + formattedDate);

	return formattedDate;

    }

    /*
     * public static String retrieveCSSValue(By locator,String value,String
     * elemName) method specification :-
     *
     * 1) Return retrieved CSS value for a web element 2)
     * driver.findElement(locator).getCssValue(value) -> Retrieves CSS (applied for
     * a web element) value
     *
     * @param : Locator for the web element, CSS name, Name of the web element
     *
     * @return : CSS value retrieved
     */

    public static String retrieveCSSValue(By locator, String value, String elemName) {

	String cssValue = null;

	APPLICATION_LOGS.debug("Getting CSS '" + value + "'  Value from - " + elemName);

	try {

	    // Wait for web element to load
	    waitForElementToLoad(locator);

	    // Highlight the web element
	    FunctionLibrary.highlightElement(driver, locator);

	    // Get CSS value for the web element
	    cssValue = driver.findElement(locator).getCssValue(value);

	    // Log result
	    APPLICATION_LOGS.debug("Got Attribute '" + value + "'  Value from : " + elemName + " : " + cssValue);

	}

	catch (Throwable retrieveCSSValueException) {

	    // report error

	    APPLICATION_LOGS.debug("Error while Getting CSS '" + value + "' value from '" + elemName + "' : "
		    + retrieveCSSValueException.getMessage());

	    return "Fail : Error while Getting CSS '" + value + "' value from '" + elemName + "' : "
		    + retrieveCSSValueException.getMessage();

	}

	return cssValue;

    }

    /*
     * public static int compareDates(String firstDate, String secondDate) method
     * specification :-
     *
     * 1) new SimpleDateFormat("MM-dd-yyyy") : Declare date formatter 2)
     * sdf.parse(firstDate) : Parse string to date
     *
     * @param : First date and second date to compare
     *
     * @return : The value 0 if firstDate is equal to secondDate; a value less than
     * 0 if firstDate is before secondDate; a value greater than 0 if firstDate is
     * after secondDate.
     */

    public static int compareDates(String firstDate, String secondDate) {

	APPLICATION_LOGS.debug("Comparing first date : " + firstDate + " and second date : " + secondDate + " ...");

	int valueReturnedAfterDateComparison = 0;

	try {

	    // Declare date formatter
	    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

	    // Parse both first and second date
	    Date date1 = sdf.parse(firstDate);
	    Date date2 = sdf.parse(secondDate);

	    // Compare first and second date
	    valueReturnedAfterDateComparison = date1.compareTo(date2);

	}

	catch (Throwable errorInDateComparison) {

	    APPLICATION_LOGS.debug("Error came while comparing first date : " + firstDate + " and second date : "
		    + secondDate + " - " + errorInDateComparison.getMessage());

	}

	return valueReturnedAfterDateComparison;

    }

    /*
     * public static Boolean compareLists(List<String> listOfUnitsToBePopulated,
     * List<String> listOfUnitsPopulated) method specification :-
     *
     * 1) Collections.sort() : Sort the list 2) list1.equals(list2) : Checks the
     * equality
     *
     * @param : First list and second list to compare
     *
     * @return : true if lists are equal, false if lists are NOT equal.
     */

    public static Boolean compareLists(List<String> firstList, List<String> secondList) {

	APPLICATION_LOGS.debug("Comparing two lists ...");

	// Declare variables need
	Boolean result = true;

	// Sort both the lists
	Collections.sort(firstList);
	Collections.sort(secondList);

	// Verify whether they are equal or not
	result = firstList.equals(secondList);

	if (result) {
	    APPLICATION_LOGS.debug(
		    "First list is : " + firstList + " , Second list is : " + secondList + " and they are equal");
	} else {
	    APPLICATION_LOGS.debug(
		    "First list is : " + firstList + " , Second list is : " + secondList + " and they are NOT equal");
	}

	return result;

    }

    /*
     * public static void assignIdAttributeToWebElement(By locator, String elemName,
     * String Id) method specification :-
     *
     * 1) JavascriptExecutor js = (JavascriptExecutor) driver : Initialize
     * Javascript executor 2) driver.findElement(locator) : Find the webelement 3)
     * js.executeScript("arguments[0].setAttribute('id', arguments[1]);" ,element ,
     * Id) : Assign an Id to the element
     *
     * @param : Element locator, Element name and ID want to replace with
     *
     * @return : void
     */

    public static void assignIdAttributeToWebElement(By locator, String elemName, String Id) {

	APPLICATION_LOGS.debug("Assigning ID attribute to " + elemName + " ...");

	try {

	    // Initialize Javascript executor
	    JavascriptExecutor js = driver;

	    // Store the webelement
	    WebElement element = driver.findElement(locator);

	    // Substituting/Adding an id for future reference
	    js.executeScript("arguments[0].setAttribute('id', arguments[1]);", element, Id);

	}

	catch (Throwable assignIdException) {

	    APPLICATION_LOGS.debug("Error came while assigning ID to the element : " + assignIdException.getMessage());

	}

	APPLICATION_LOGS.debug("Assigned ID attribute to " + elemName);

    }

    /**
     * Scrolls within an web element.
     * <ul>
     * <li><strong>pixelToScrollVertically:</strong> +ve value (scroll down) -ve
     * value (scroll top)
     * <li><strong>pixelToScrollHorizontally:</strong> +ve value (scroll left) -ve
     * value (scroll right))
     * </ul>
     *
     * @param locator
     *                                      Element locator
     * @param elemName
     *                                      Element name
     * @param pixelToScrollVertically
     *                                      Pixel to scroll vertically
     * @param pixelToScrollHorizontally
     *                                      Pixel to scroll horizontally
     *
     * @return void
     */

    public static void scrollWithinParticularElement(By locator, String elemName, int pixelToScrollVertically,
	    int pixelToScrollHorizontally) {

	APPLICATION_LOGS.debug("Scrolling within " + elemName + " ...");

	try {

	    // Initialize Javascript executor
	    JavascriptExecutor js = driver;

	    // Scroll inside web element vertically
	    js.executeScript("arguments[0].scrollTop = arguments[1];", driver.findElement(locator),
		    pixelToScrollVertically);

	    // Scroll inside web element horizontally
	    js.executeScript("arguments[0].scrollLeft = arguments[1];", driver.findElement(locator),
		    pixelToScrollHorizontally);

	}

	catch (Throwable scrollException) {

	    APPLICATION_LOGS.debug("Error came while scrolling within the element : " + scrollException.getMessage());

	}

	APPLICATION_LOGS.debug("Scrolled within " + elemName);

    }

    /*
     * public static String getBrowserInfo() method specification :-
     *
     * 1) JavascriptExecutor js = (JavascriptExecutor) driver : Initialize
     * Javascript executor 2) js.executeScript("return navigator.userAgent;") : Get
     * browser info
     *
     * @param : none
     *
     * @return : Browser Info
     */

    public static String getBrowserInfo() {

	APPLICATION_LOGS.debug("Getting browser info ...");

	// Declare variables need
	String browserInfo = null;
	JavascriptExecutor js = null;

	try {

	    // Initialize JavascriptExecutor
	    js = driver;

	    // Get browser info
	    browserInfo = (String) js.executeScript("return navigator.userAgent;");

	    APPLICATION_LOGS.debug("Got browser info : " + browserInfo);

	}

	catch (Throwable browserInfoError) {

	    APPLICATION_LOGS.debug("Error came while getting browser info : " + browserInfoError.getMessage());

	}

	// Return browser info
	return browserInfo;

    }

    /*
     * public static String getBrowserName() method specification :-
     *
     * 1) browserInfo = getBrowserInfo() : Get browser info 2)
     * browserInfo.contains("Firefox") or browserInfo.contains("MSIE") : Get browser
     * name
     *
     * @param : none
     *
     * @return : Browser Name
     */

    public static String getBrowserName() {

	APPLICATION_LOGS.debug("Getting browser name ...");

	// Declare variables need
	String browserInfo = null;
	String browserName = null;

	// Get browser info
	browserInfo = getBrowserInfo();

	// Get browser name
	if (browserInfo.contains("Firefox")) {
	    browserName = "Firefox";
	} else if (browserInfo.contains("MSIE")) {
	    browserName = "Microsoft Internet Explorer";
	}

	APPLICATION_LOGS.debug("Got browser name : " + browserName);

	// Return browser name
	return browserName;

    }

    /*
     * public static String getBrowserVersion() method specification :-
     *
     * 1) browserInfo = getBrowserInfo() : Get browser info 2) browserInfo.split :
     * Split browser info to get browser version
     *
     * @param : none
     *
     * @return : Browser Version
     */

    public static String getBrowserVersion() {

	APPLICATION_LOGS.debug("Getting browser version ...");

	// Declare variables need
	String browserInfo = null;
	String browserVersion = null;

	// Get browser info
	browserInfo = getBrowserInfo();

	// Get browser version
	if (browserInfo.contains("Firefox")) {
	    browserVersion = browserInfo.split("Firefox/")[1];
	} else if (browserInfo.contains("MSIE")) {
	    browserVersion = browserInfo.split("MSIE ")[1].split(";")[0];
	}

	APPLICATION_LOGS.debug("Got browser version : " + browserVersion);

	// Return browser version
	return browserVersion;

    }

    /*
     * public static String getPageTitle() method specification :-
     *
     * 1) driver.getTitle() : Get current page title
     *
     * @param : none
     *
     * @return : Current webpage title
     */

    public static String getPageTitle() {

	APPLICATION_LOGS.debug("Getting current webpage title ...");
	String pageTitle = null;

	try {

	    // Get page title
	    pageTitle = driver.getTitle();

	} catch (Throwable getPageTitleError) {

	    APPLICATION_LOGS.debug("Error came while fetching page title : " + getPageTitleError.getMessage());

	}

	APPLICATION_LOGS.debug("Got current webpage title : " + pageTitle);
	return pageTitle;

    }

    /*
     * public static String getCurrentUrl() method specification :-
     *
     * driver.getCurrentUrl() : Get current url
     *
     * @param : none
     *
     * @return : Current webpage title
     */

    public static String getCurrentUrl() {

	APPLICATION_LOGS.debug("Getting current Url ...");
	String currentUrl = null;

	try {

	    // Get current url
	    currentUrl = driver.getCurrentUrl();

	} catch (Throwable getCurrentUrlError) {

	    APPLICATION_LOGS.debug("Error came while getting current Url : " + getCurrentUrlError.getMessage());

	}

	APPLICATION_LOGS.debug("Got current URL : " + currentUrl);
	return currentUrl;

    }

    /*
     * public static String loadUrl(String url) method specification :-
     *
     * driver.navigate().to(url); : Navigate to Url
     *
     * @param : none
     *
     * @return : (Pass) - If Url loaded successfully (Fail) - If Url NOT loaded
     * successfully
     */

    public static String loadUrl(String url) {

	APPLICATION_LOGS.debug("Loading Url : " + url + " ...");

	try {

	    // Load url
	    driver.navigate().to(url);

	} catch (Throwable loadingUrlError) {

	    APPLICATION_LOGS.debug("Error came while loading Url : " + loadingUrlError.getMessage());

	    return "Fail : Error came while loading Url : " + loadingUrlError.getMessage();

	}

	APPLICATION_LOGS.debug("Url successfully loaded");
	return "Pass : Url successfully loaded";

    }

    /*
     * public static Boolean isElementPresent(By locator, String elemName) method
     * specification :-
     *
     * driver.findElement(locator) : Checking whether element present or not
     *
     * @param : web element locator, web element name
     *
     * @return : (true) - If element is present (false) - If element not present
     */
    public static Boolean isElementPresent(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking whether " + elemName + " is present on the page or not ...");

	try {

	    // Check whether web element is displayed or not
	    driver.findElement(locator);

	    APPLICATION_LOGS.debug(elemName + " is present on the page");
	    return true;

	}

	catch (NoSuchElementException elementPresentError) {

	    APPLICATION_LOGS.debug(elemName + " not present on the page");
	    return false;

	}

    }

    /*
     * public static Boolean isElementDisplayed(By locator, String elemName) method
     * specification :-
     *
     * driver.findElement(locator).isDisplayed() : Verifying whether element
     * displayed or not
     *
     * @param : web element locator, web element name
     *
     * @return : (true) - If element is displayed (false) - If element not displayed
     */
    public static Boolean isElementDisplayed(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking whether " + elemName + " is displayed on the page or not ...");

	Boolean isDisplayed;

	try {

	    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

	    // Check whether web element is displayed or not
	    isDisplayed = driver.findElement(locator).isDisplayed();

	    if (isDisplayed) {
		APPLICATION_LOGS.debug(elemName + " is displayed on the page");
	    } else {
		APPLICATION_LOGS.debug(elemName + " not displayed on the page");
	    }

	    return isDisplayed;

	}

	catch (Throwable elementPresentError) {

	    APPLICATION_LOGS.debug(elemName + " not present on the page");
	    return false;

	}

    }

    /*
     * public static Boolean isElementEnabled(By locator, String elemName) method
     * specification :-
     *
     * driver.findElement(locator).isEnabled() : Verifying whether element enabled
     * or not
     *
     * @param : web element locator, web element name
     *
     * @return : (true) - If element is enabled (false) - If element not enabled
     */
    public static Boolean isElementEnabled(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking whether " + elemName + " is enabled on the page or not ...");

	Boolean isEnabled;

	try {

	    // Check whether web element is enabled or not
	    isEnabled = driver.findElement(locator).isEnabled();

	    if (isEnabled) {
		APPLICATION_LOGS.debug(elemName + " is enabled on the page");
	    } else {
		APPLICATION_LOGS.debug(elemName + " not enabled on the page");
	    }

	    return isEnabled;

	}

	catch (Throwable elementPresentError) {

	    APPLICATION_LOGS.debug(elemName + " not present on the page");
	    return false;

	}

    }

    /*
     * public static Boolean isElementSelected(By locator, String elemName) method
     * specification :-
     *
     * driver.findElement(locator).isSelected() : Verifying whether element selected
     * or not
     *
     * @param : web element locator, web element name
     *
     * @return : (true) - If element is selected (false) - If element not selected
     */
    public static Boolean isElementSelected(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking whether " + elemName + " is selected on the page or not ...");

	Boolean isSelected;

	try {

	    // Check whether web element is selected or not
	    isSelected = driver.findElement(locator).isSelected();

	    if (isSelected) {
		APPLICATION_LOGS.debug(elemName + " is selected on the page");
	    } else {
		APPLICATION_LOGS.debug(elemName + " not selected on the page");
	    }

	    return isSelected;

	}

	catch (NoSuchElementException elementPresentError) {

	    APPLICATION_LOGS.debug(elemName + " not present on the page");
	    return false;

	}

    }

    /*
     * public static Boolean compareListsWithPreservedOrder( List<String> firstList,
     * List<String> secondList) method specification :-
     *
     * 1) if (!firstList.get(i).equals(secondList.get(i))) : Checks for equality
     * with preserved order
     *
     * @param : First list and second list to compare
     *
     * @return : true if lists are equal, false if lists are NOT equal.
     */

    public static Boolean compareListsWithPreservedOrder(List<String> firstList, List<String> secondList) {

	APPLICATION_LOGS.debug("Comparing two lists whether in the same order ...");

	// Declare variables need
	Boolean result = true;

	// Compare two lists
	for (int i = 0; i < firstList.size(); i++) {

	    if (!firstList.get(i).equals(secondList.get(i))) {
		result = false;
	    }

	}

	// Verify whether they are equal or not

	if (result) {
	    APPLICATION_LOGS.debug(
		    "First list is : " + firstList + " , Second list is : " + secondList + " and they are equal");
	} else {
	    APPLICATION_LOGS.debug(
		    "First list is : " + firstList + " , Second list is : " + secondList + " and they are NOT equal");
	}

	System.out.println(Arrays.toString(firstList.toArray()));
	System.out.println(Arrays.toString(secondList.toArray()));

	return result;

    }

    /*
     * public static ArrayList<Date> convertStringListToDateList( ArrayList<String>
     * stringList, String dateFormat) method specification :-
     *
     * 1) date =FunctionLibrary. convertStringToDate(stringList.get(i),dateFormat) :
     * This converts string to date 2) dateList.add(date) : Add date to the list
     *
     * @param : List of date in string data type, date format
     *
     * @return : List of date in date data type
     */
    public static ArrayList<Date> convertStringListToDateList(ArrayList<String> stringList, String dateFormat) {

	// Declare variables
	Date date;
	ArrayList<Date> dateList = new ArrayList<Date>();

	// Convert string list to date list
	for (int i = 0; i < stringList.size(); i++) {

	    date = FunctionLibrary.convertStringToDate(stringList.get(i), dateFormat);

	    // Add converted date into date list
	    dateList.add(date);

	}

	// Return date type list
	return dateList;

    }

    /*
     * public static ArrayList<String> convertDateListToStringList( ArrayList<Date>
     * dateList, String format) method specification :-
     *
     * 1) SimpleDateFormat dateFormat = new SimpleDateFormat(format) : Instantiate
     * date formatter 2) date = dateFormat.format(dateList.get(i)) : Format a date
     * to string in specified format
     *
     * @param : List of date in date data type, date format
     *
     * @return : List of date in string data type
     */
    public static ArrayList<String> convertDateListToStringList(ArrayList<Date> dateList, String format) {

	// Declare variables
	String date;
	ArrayList<String> stringList = new ArrayList<String>();
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);

	// Convert date list to string list
	for (int i = 0; i < dateList.size(); i++) {

	    date = dateFormat.format(dateList.get(i));

	    // Add converted date into string list
	    stringList.add(date);

	}

	// Return date type list
	return stringList;

    }

    /**
     * Brief Description Of getNextOrPreviousDate(String startDate, int days, String
     * dateFormat) method Add no. of days to the current Date Deduct no. of days
     * from the curent date
     */

    public static String getNextOrPreviousDate(String startDate, int days, String dateFormat) throws ParseException {

	// Start date
	try {

	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    Calendar c = Calendar.getInstance();
	    c.setTime(sdf.parse(startDate));
	    c.add(Calendar.DATE, days); // number of days to add
	    dateFormat = sdf.format(c.getTime()); // dt is now the new date

	}

	catch (Throwable dateException) {

	    APPLICATION_LOGS.debug("Error came while getting next or previous date : " + dateException.getMessage());

	}

	APPLICATION_LOGS.debug("New date After addition or substarction of Days : " + dateFormat);

	return dateFormat;

    }

    /**
     * Move to web element. This function is useful to move view-port within web
     * element. Works for page scroll only.
     *
     * <p>
     * This method <code>returns</code>:
     * <ul>
     * <li>Pass if moved to web element successfully</li>
     * <li>Fail if any exception occurs in between</li>
     * </ul>
     *
     * @param locator
     *                     Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String moveToWebElement(By locator, String elemName) {

	APPLICATION_LOGS.debug("Moving to : " + elemName + " ...");

	// Initiate Actions class
	Actions action = new Actions(driver);

	try {

	    // Wait for check-box to appear on the page
	    waitForElementToLoad(locator);

	    // Move to element
	    action.moveToElement(driver.findElement(locator)).build().perform();

	    // Log the result
	    APPLICATION_LOGS.debug("Moved to : " + elemName);

	    return "Pass : Moved to : " + elemName;

	}

	catch (Throwable moveToElementException) {

	    APPLICATION_LOGS.debug("Error came while moving to : " + moveToElementException.getMessage());

	    return "Fail : Error came while moving to " + moveToElementException.getMessage();

	}

    }

    /* Switch to iframe */
    public static String switchToiFrame(By locator, String elemname) {
	try {
	    // Wait for check-box to appear on the page
	    waitForElementToLoad(locator);
	    // Switch to iframe
	    APPLICATION_LOGS.debug("Switching to " + elemname + ": iframe ....");
	    driver.switchTo().frame(driver.findElement(locator));

	    APPLICATION_LOGS.debug("Switched to " + elemname + ": iframe ....");
	    return "Pass : switch to " + elemname + ": iframe ";
	} catch (Throwable SwitchToFrameException) {
	    APPLICATION_LOGS.debug("Error came while switching to " + elemname + ": iframe");
	    return failTest + ": Error came while switching to " + elemname + ": iframe";
	}
    }

    public static String switchToAlert() throws InterruptedException {

	APPLICATION_LOGS.debug("Switching to Alert");

	try {

	    // Switch to alert
	    driver.switchTo().alert();

	    // Log result
	    APPLICATION_LOGS.debug("Switched to Alert");

	    return "Pass : Switched to alert";

	}

	catch (Throwable switchToAlertException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while switching to alert : " + switchToAlertException.getMessage());

	    return "Fail : Error while switching to alert : " + switchToAlertException.getMessage();

	}

    }

    /*
     * public static String dismissAlert(String elemName) method specification :-
     *
     * 1) Dismiss an alert 2) alert.dismiss() -> dismisses the alert
     *
     * @param : Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String dismissAlert(String elemName) {

	APPLICATION_LOGS.debug("Dismissing alert : " + elemName);

	try {

	    // Create a new alert object
	    Alert alert = driver.switchTo().alert();

	    // Dismiss the alert
	    alert.dismiss();

	    // Log result
	    APPLICATION_LOGS.debug("Dismissed alert : " + elemName);

	    return "Pass : Dismissed the alert '" + elemName + "'";

	}

	catch (Throwable dismissAlertException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error came while dismissing alert : " + dismissAlertException.getMessage());

	    return "Fail : Error came while dismissing alert : " + dismissAlertException.getMessage();

	}

    }

    /*
     * public static String getAlertText() method specification :-
     *
     * 1) driver.getAlertText() : Get the text of the alert
     *
     * @param : none
     *
     * @return : Current webpage title
     */

    public static String getAlertText() {

	APPLICATION_LOGS.debug("Getting text of the alert ...");
	String alertText = null;

	try {

	    // Create a new alert object
	    Alert alert = driver.switchTo().alert();

	    // Get alert text
	    alertText = alert.getText();

	} catch (Throwable getAlertTextError) {

	    APPLICATION_LOGS.debug("Error came while fetching alert text : " + getAlertTextError.getMessage());

	}

	APPLICATION_LOGS.debug("Got text of alert : " + alertText);
	return alertText;

    }

    // Function to MouseHover webelement

    public static String mouseOverWebElement(By locator, String elemName) {
	try {

	    Actions builder = new Actions(driver);
	    builder.moveToElement(driver.findElement(locator)).build().perform();
	    System.out.println(elemName + " Mouse hovered");
	    APPLICATION_LOGS.debug(elemName + " Mouse hovered");
	    return "Pass";
	} catch (Exception e) {
	    return "Fail" + e.getMessage();
	}
    }

    public static int extractNumberFromString(String stringWithNumbers) {

	int integerInString = 0;

	APPLICATION_LOGS.debug("Extracting Number from the String ...");

	try {

	    // Convert to dateString str_Extract = "ABC1234jk98";
	    for (int i = 0; i < stringWithNumbers.length(); i++) {
		if (stringWithNumbers.charAt(i) > 47 && stringWithNumbers.charAt(i) < 58) {
		    integerInString = stringWithNumbers.charAt(i);
		}
	    }

	    // Log result
	    APPLICATION_LOGS.debug("Integer retreived : " + integerInString);

	}

	catch (Throwable extractNumberFromStringException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while converting String to int format : " + extractNumberFromStringException.getMessage());

	}

	return integerInString;

    }

    public static String[] extractAllTextFromWebTable(By locator) {

	String[] rowTxt = null;

	APPLICATION_LOGS.debug("Extracting text from web table ...");

	try {

	    // get the rows of the table
	    List<WebElement> tableRows = driver.findElements(locator);

	    // get the Text from the table
	    for (WebElement row : tableRows) {
		List<WebElement> tableCols = row.findElements(By.tagName("td"));
		rowTxt = new String[tableCols.size()];
		for (int i = 0; i < rowTxt.length; i++) {
		    rowTxt[i] = tableCols.get(i).getText();
		}

	    }
	    // Log result
	    APPLICATION_LOGS.debug("Text from the web table retrieved ");

	}

	catch (Throwable extractAllTextFromWebTableException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while retrieving the text from web table : "
		    + extractAllTextFromWebTableException.getMessage());

	}

	return rowTxt;

    }

    /*
     * public static String convertStringToDate(String dateString, String
     * dateFormat) method specification :-
     *
     * 1) Returns date converted from a String format 2) SimpleDateFormat(
     * "MMMM dd, yyyy").parse(retrievedString) -> Converts String to Date format
     *
     * @param : date in string format, date format
     *
     * @return : Date which is being converted from String format
     */

    public static long getMinuteDifference(String date1, String date2, String dateFormat) {

	APPLICATION_LOGS.debug("Get the minute difference from two date strings ...");

	long minutes = 0;

	try {

	    // Convert to date
	    DateFormat sdf = new SimpleDateFormat(dateFormat);
	    Date d1 = sdf.parse(date1);
	    Date d2 = sdf.parse(date2);

	    long diff = d1.getTime() - d2.getTime();
	    minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
	    // Log result
	    APPLICATION_LOGS.debug("Minute difference : " + minutes);

	}

	catch (Throwable getMinuteDifferenceException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while getting the difference from two date strings : "
		    + getMinuteDifferenceException.getMessage());

	}

	return minutes;

    }

    public static Date subtractDate(int dateDiff) {

	APPLICATION_LOGS.debug("Get the date difference  ...");
	Date newDate = new Date();

	try {

	    // Subtract days from current date

	    newDate.setDate(newDate.getDate() - dateDiff);

	    // Log result
	    APPLICATION_LOGS.debug("Date difference : " + newDate);

	}

	catch (Throwable getDateDifferenceException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while getting the difference of date : " + getDateDifferenceException.getMessage());

	}

	return newDate;

    }

    /*
     * public static void waitForElementToDisappear(By locator) method specification
     * :-
     *
     * 1) Waits for the web element to appear on the page 2) new
     * WebDriverWait(driver, 60) -> Waits for 60 seconds 3)
     * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All
     * documents present on the page get ready) met
     *
     * @param : locator for the web element
     *
     * @return : void
     */

    public static void driver2WaitForElementToDisappear(final By locator, String elemName) {

	APPLICATION_LOGS.debug("Waiting for " + elemName + " to disappear ...");

	try {

	    // Waits for 60 seconds
	    Wait<WebDriver> wait = new WebDriverWait(driver2, 60);

	    // Wait until the element get disappeared
	    @SuppressWarnings("unused")
	    /*
	     * WebElement element = wait .until(ElementLocatedToGetDisappear(locator));
	     */
	    WebElement element = wait.until(ElementLocatedToGetDisappear(locator));

	    // Log result
	    APPLICATION_LOGS.debug("Waiting ends ... " + elemName + " disappeared");

	}

	catch (Throwable waitForElementException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error came while waiting for element to disappear : " + waitForElementException.getMessage());

	}

    }

    /*
     * public static String assertTitle(String expectedTitle) method specification
     * :-
     *
     * 1) Asserts page title 2) driver.getTitle() -> Retrieves page title 3)
     * Assert.assertEquals() -> Asserts for equality
     *
     * @param : Expected title to assert
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2AssertTitle(String expectedTitle) {

	String actualTitle = null;

	APPLICATION_LOGS.debug("Asserting  title  where : Expected title = " + expectedTitle);

	try {

	    // Fetch actual title of the webpage
	    actualTitle = driver2.getTitle();

	    // Asserts whether actual title matches with expected one
	    Assert.assertEquals(expectedTitle.trim(), actualTitle.trim());

	    // Log result
	    APPLICATION_LOGS
		    .debug("Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle);

	    return "Pass : Actual title = " + actualTitle + " and matches with Expected title = " + expectedTitle;

	}

	catch (Throwable assertTitleException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while asserting title : " + assertTitleException.getMessage());

	    return "Fail : Error while asserting title : " + assertTitleException.getMessage();

	}

    }

    /*
     * public static String getPageTitle() method specification :-
     *
     * 1) driver.getTitle() : Get current page title
     *
     * @param : none
     *
     * @return : Current webpage title
     */

    public static String driver2GetPageTitle() {

	APPLICATION_LOGS.debug("Getting current webpage title ...");
	String pageTitle = null;

	try {

	    // Get page title
	    pageTitle = driver2.getTitle();

	} catch (Throwable getPageTitleError) {

	    APPLICATION_LOGS.debug("Error came while fetching page title : " + getPageTitleError.getMessage());

	}

	APPLICATION_LOGS.debug("Got current webpage title : " + pageTitle);
	return pageTitle;

    }

    /*
     * public static void waitForElementToLoad(By locator) method specification :-
     *
     * 1) Waits for the web element to appear on the page 2) new
     * WebDriverWait(driver, 60) -> Waits for 60 seconds 3)
     * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All
     * documents present on the page get ready) met
     *
     * @param : no parameters passed
     *
     * @return : void
     */

    public static void driver2WaitForElementToLoad(final By locator) {

	APPLICATION_LOGS.debug("Waiting for web element to load on the page");

	try {

	    // Waits for 60 seconds
	    Wait<WebDriver> wait = new WebDriverWait(driver2, 60);

	    // Wait until the element is located on the page
	    @SuppressWarnings("unused")
	    WebElement element = wait.until(visibilityOfElementLocated(locator));

	    // Log result
	    APPLICATION_LOGS.debug("Waiting ends ... Web element loaded on the page");

	}

	catch (Throwable waitForElementException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error came while waiting for element to appear : " + waitForElementException.getMessage());

	}

    }

    /*
     * public static void waitForPageToLoad() method specification :-
     *
     * 1) Waits for a new page to load completely 2) new WebDriverWait(driver, 60)
     * -> Waits for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait
     * until expected condition (All documents present on the page get ready) met
     *
     * @param : no parameters passed
     *
     * @return : void
     */

    public static void driver2WaitForPageToLoad() throws InterruptedException {

	try {

	    // Waits for 60 seconds
	    WebDriverWait wait = new WebDriverWait(driver2, 60);
	    // Wait until expected condition (All documents present on the page
	    // get ready) met
	    wait.until(new ExpectedCondition<Boolean>() {

		public Boolean apply(WebDriver d) {

		    if (!(d instanceof JavascriptExecutor)) {
			return true;
		    }

		    Object result = ((JavascriptExecutor) d)
			    .executeScript("return document['readyState'] ? 'complete' == document.readyState : true");

		    if (result != null && result instanceof Boolean && (Boolean) result) {
			return true;
		    }

		    return false;

		}

	    });

	}

	catch (Throwable waitForPageToLoadException) {
	    APPLICATION_LOGS
		    .debug("Error came while waiting for page to load : " + waitForPageToLoadException.getMessage());
	}

    }

    /*
     * public static Boolean isElementDisplayed(By locator, String elemName) method
     * specification :-
     *
     * driver.findElement(locator).isDisplayed() : Verifying whether element
     * displayed or not
     *
     * @param : web element locator, web element name
     *
     * @return : (true) - If element is displayed (false) - If element not displayed
     */
    public static Boolean driver2IsElementDisplayed(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking whether " + elemName + " is displayed on the page or not ...");

	Boolean isDisplayed;

	try {

	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

	    // Check whether web element is displayed or not
	    isDisplayed = driver2.findElement(locator).isDisplayed();

	    if (isDisplayed) {
		APPLICATION_LOGS.debug(elemName + " is displayed on the page");
	    } else {
		APPLICATION_LOGS.debug(elemName + " not displayed on the page");
	    }

	    return isDisplayed;

	}

	catch (NoSuchElementException elementPresentError) {

	    APPLICATION_LOGS.debug(elemName + " not present on the page");
	    return false;

	}

    }

    public static String driver2ClearAndInput(By locator, String elemName, String Value) {

	try {

	    // Wait for the input box to appear on the page
	    driver2WaitForElementToLoad(locator);

	    // Highlight the input box
	    FunctionLibrary.highlightElement(driver2, locator);

	    // Clear the input field before sending values
	    FunctionLibrary.driver2ClearField(locator, elemName);

	    // Send values to the input box
	    APPLICATION_LOGS.debug("Sending Values in : " + elemName);
	    driver2.findElement(locator).sendKeys(Value);

	    // Log result
	    APPLICATION_LOGS.debug("Inputted '" + Value + "' text into : '" + elemName + "'");

	    return "Pass : Inputted '" + Value + "' text into : '" + elemName + "'";

	}

	catch (Throwable inputException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while inputting into - '" + elemName + "' : " + inputException.getMessage());

	    return "Fail : Error while inputting into - '" + elemName + "' : " + inputException.getMessage();

	}

    }

    public static String driver2ClearField(By locator, String elemName) {

	APPLICATION_LOGS.debug("Clearing field : " + elemName);

	try {

	    // Wait for the input-box to load on the page
	    driver2WaitForElementToLoad(locator);

	    // Highlight the input-box
	    FunctionLibrary.highlightElement(driver2, locator);

	    // Clear the input-box
	    driver2.findElement(locator).clear();

	    // Check whether input-box has been cleared or not
	    if (!driver2.findElement(locator).getAttribute("value").isEmpty()) {
		driver2.findElement(locator).clear();
	    }

	    // Log result
	    APPLICATION_LOGS.debug("Cleared : " + elemName);

	    return "Pass : Cleared : " + elemName;

	}

	catch (Throwable clearFieldException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clearing - " + elemName + " : " + clearFieldException.getMessage());

	    return "Fail : Error while clearing - " + elemName + " : " + clearFieldException.getMessage();

	}

    }

    public static String driver2ClickAndWait(By locator, String elemName) {

	try {

	    // Click on the web element targeted by locator
	    methodReturnResult = FunctionLibrary.driver2ClickLink(locator, elemName);
	    if (methodReturnResult.contains(failTest)) {
		return methodReturnResult;
	    }

	    // Wait for new page to load
	    FunctionLibrary.driver2WaitForPageToLoad();

	    // Log result
	    APPLICATION_LOGS.debug(
		    "Clicked on the element : " + elemName + " and new page loaded with title : " + driver2.getTitle());

	    return "Pass : Clicked on the element : " + elemName + " and new page loaded with title : "
		    + driver2.getTitle();

	}

	catch (Throwable clickAndWaitException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clicking on " + elemName + " and waiting for new page to load : "
		    + clickAndWaitException.getMessage());

	    return "Error while clicking on link " + elemName + " and waiting for new page to load : "
		    + clickAndWaitException.getMessage();

	}

    }

    /**
     * Waits for element to appear on the page. Once appeared, highlight the element
     * and clicks on it. Returns Pass if able to click on the element. Returns Fail
     * if any exception occurs in between.
     *
     * @param locator
     *                     Element locator
     * @param elemName
     *                     Element name
     *
     * @return Pass/Fail
     */

    public static String driver2ClickLink(By locator, String elemName) {

	APPLICATION_LOGS.debug("Clicking on : " + elemName);

	try {

	    // Wait for link to appear on the page
	    driver2WaitForElementToLoad(locator);

	    // Highlight link
	    FunctionLibrary.highlightElement(driver2, locator);

	    // Click on the link
	    driver2.findElement(locator).click();

	    // Log result
	    APPLICATION_LOGS.debug("Clicked on : " + elemName);

	    return "Pass : Clicked on : " + elemName;

	}

	catch (Throwable clickLinkException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while clicking on - " + elemName + " : " + clickLinkException.getMessage());

	    return "Fail : Error while clicking on - " + elemName + " : " + clickLinkException.getMessage();

	}

    }

    /*
     * public static String retrieveText(By locator,String elemName) method
     * specification :-
     *
     * 1) Return retrieved text from webpage 2)
     * driver.findElement(locator).getText() -> Retrieves text from the web element
     * targeted by specified locator
     *
     * @param : Locator for the web element, Name of the web element
     *
     * @return : Text retrieved from the webpage
     */

    public static String driver2RetrieveText(By locator, String elemName) {

	String retrievedText = null;

	APPLICATION_LOGS.debug("Retrieving Text from : " + elemName);

	try {

	    // Wait for web element to load on the page
	    driver2WaitForElementToLoad(locator);

	    // Highlight the web element
	    FunctionLibrary.highlightElement(driver2, locator);

	    // Retrieve text from web element
	    retrievedText = driver2.findElement(locator).getText().trim();

	    // Log result
	    APPLICATION_LOGS.debug("Retrieved text : " + retrievedText);

	}

	catch (Throwable retrieveTextException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage());

	}

	return retrievedText;

    }

    /*
     * public static String selectValueByIndex(By Locator, int index, String
     * elemName) method specification :-
     *
     * 1) Select value from drop-down by index 2) Select -> This is a in-built class
     * in Selenium which is used to represent a drop-down 3)
     * select.selectByIndex(index) -> Select by index
     *
     * @param : Locator for the drop-down field, Index for the option to be
     * selected, Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2SelectValueByIndex(By Locator, int index, String elemName) {

	APPLICATION_LOGS.debug("Selecting value from : " + elemName);

	try {

	    // Wait for drop-down element to load on the page
	    driver2WaitForElementToLoad(Locator);

	    // Highlight the drop-down
	    FunctionLibrary.highlightElement(driver2, Locator);

	    // Locate drop-down field
	    Select select = new Select(driver2.findElement(Locator));

	    // Select value from drop-down
	    select.selectByIndex(index);

	    // Log result
	    APPLICATION_LOGS.debug("Selected value from : " + elemName);

	    return "Pass : Selected value from : " + elemName;

	}

	catch (Throwable selectValueException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while Selecting Value from - '" + elemName + "' : " + selectValueException.getMessage());

	    return "Fail : Error while Selecting Value from - '" + elemName + "' : "
		    + selectValueException.getMessage();

	}

    }

    /*
     * public static String selectValueByVisibleText(By Locator, String Option,
     * String elemName) method specification :-
     *
     * 1) Select value from drop-down by visible text 2) Select -> This is a
     * in-built class in Selenium which is used to represent a drop-down 3)
     * select.selectByVisibleText(Value) -> Select by visible text
     *
     * @param : Locator for the drop-down field, Option to be selected, Name of the
     * web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2SelectValueByVisibleText(By Locator, String Option, String elemName) {

	APPLICATION_LOGS.debug("Selecting '" + Option + "' from : " + elemName);

	try {

	    // Wait for drop-down element to load on the page
	    driver2WaitForElementToLoad(Locator);

	    // Highlight the drop-down
	    FunctionLibrary.highlightElement(driver2, Locator);

	    // Locate drop-down field
	    Select select = new Select(driver2.findElement(Locator));

	    // Select value from drop-down
	    select.selectByVisibleText(Option);

	    // Log result
	    APPLICATION_LOGS.debug("Selected '" + Option + "' from : " + elemName);

	    return "Pass : Selected '" + Option + "' from : " + elemName;

	}

	catch (Throwable selectValueException) {

	    // Log error
	    APPLICATION_LOGS.debug(
		    "Error while Selecting Value from - '" + elemName + "' : " + selectValueException.getMessage());

	    return "Fail : Error while Selecting Value from - '" + elemName + "' : "
		    + selectValueException.getMessage();

	}

    }

    /*
     * public static String checkCheckBox(By locator, String elemName) method
     * specification :
     *
     * 1) Checks a check-box if it is not checked already 2) if
     * (!driver.findElement(locator).isSelected()) {
     * driver.findElement(locator).click() : Checks the checkbox if it is not
     * checked already 3) String elemName : Passed as a parameter to name the
     * element
     */
    public static String driver2CheckCheckBox(By locator, String elemName) {

	APPLICATION_LOGS.debug("Checking : " + elemName);

	try {

	    // Wait for element to load
	    driver2WaitForElementToLoad(locator);

	    // Highlight the element
	    FunctionLibrary.highlightElement(driver2, locator);

	    // Select the element if not selected already
	    if (!driver2.findElement(locator).isSelected()) {

		driver2.findElement(locator).click();

		APPLICATION_LOGS.debug("Checked " + elemName);
		return "Pass : Checked " + elemName;

	    }

	    else {

		APPLICATION_LOGS.debug(elemName + " is already checked");

		return "Pass : " + elemName + " is already checked";

	    }

	}

	catch (Throwable checkCheckBoxException) {

	    APPLICATION_LOGS.debug("Error while checking -" + elemName + checkCheckBoxException.getMessage());
	    return "Fail : Error while checking -" + elemName + checkCheckBoxException.getMessage();

	}

    }

    /*
     * public static void waitForNewWindow(int prevWndCount) method specification :-
     *
     * 1) Waits for a new window to appear 2) new WebDriverWait(driver, 60) -> Waits
     * for 60 seconds 3) wait.until((ExpectedCondition<Boolean>) -> Wait until
     * expected condition (Window count increases) met 4)
     * d.getWindowHandles().size() -> Returns number of window handles present
     *
     * @param : Previous window count
     *
     * @return : void
     */

    public static void driver2WaitForNewWindow(int prevWndCount) {

	final int currWndCount = prevWndCount;

	try {

	    // Waits for 60 seconds
	    WebDriverWait wait = new WebDriverWait(driver2, 60);

	    // Wait until expected condition (Window count increases) met
	    wait.until(new ExpectedCondition<Boolean>() {

		public Boolean apply(WebDriver d) {

		    // Return true if window count increases, else return false
		    return d.getWindowHandles().size() > currWndCount;

		}

	    });

	}

	catch (Throwable waitForNewWindowException) {
	    APPLICATION_LOGS.debug(
		    "Exception while waiting for new window to appear : " + waitForNewWindowException.getMessage());
	}

    }

    /*
     * public static String switchToPopupWindow() method specification :-
     *
     * 1) Switches to pop-up window 2) driver.getWindowHandle() -> Returns current
     * window handle 3) driver.getWindowHandles() -> Returns all the available
     * window handles 4) driver.switchTo().window(popUpWindowHandle) -> Switches to
     * pop-up window
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2SwitchToPopupWindow() throws InterruptedException {

	APPLICATION_LOGS.debug("Executing switchToPopupWindow");

	String popUpWindowHandle = null;

	try {

	    // Save current window handle for future reference
	    defaultWindow = driver2.getWindowHandle();

	    // Get all the window handles one by one
	    for (String windowHandle : driver2.getWindowHandles()) {

		// Save new window handle
		if (!windowHandle.equals(defaultWindow)) {

		    popUpWindowHandle = windowHandle;

		}

	    }

	    // Switches to pop-up window
	    driver2.switchTo().window(popUpWindowHandle);

	    // Maximize browser window
	    driver2.manage().window().maximize();

	    // Log result
	    APPLICATION_LOGS.debug("Switched to pop-up window");

	    return "Pass : Switched to pop-up window";

	}

	catch (Throwable switchToPopupWindowException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while Switching to Pop Window : " + switchToPopupWindowException.getMessage());

	    return "Fail : Error while Switching to Pop Window : " + switchToPopupWindowException.getMessage();

	}

    }

    public static String generateSpecialCharacter(int length) {

	final String alphabet = "!@#$%^&*<>?";
	final int N = alphabet.length();
	Random rd = new Random();
	int randomSplCharacterLength = length;
	StringBuilder sb = new StringBuilder(randomSplCharacterLength);
	for (int i = 0; i < randomSplCharacterLength; i++) {
	    sb.append(alphabet.charAt(rd.nextInt(N)));
	}

	String randomnSplCharacter = sb.toString();
	return randomnSplCharacter;

    }

    public static String generateRandomNumber(int length) {

	final String alphabet = "123456789";
	final int N = alphabet.length();
	Random rd = new Random();
	int randomNumberLength = length;
	StringBuilder sb = new StringBuilder(randomNumberLength);
	for (int i = 0; i < randomNumberLength; i++) {
	    sb.append(alphabet.charAt(rd.nextInt(N)));
	}

	String randomnNumber = sb.toString();
	return randomnNumber;

    }

    public static String generateRandomString(int length) {

	String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	final int N = CHAR_LIST.length();
	Random rd = new Random();
	int randomStringLength = length;
	StringBuilder sb = new StringBuilder(randomStringLength);
	for (int i = 0; i < randomStringLength; i++) {
	    sb.append(CHAR_LIST.charAt(rd.nextInt(N)));
	}

	String randomnString = sb.toString();
	return randomnString;

    }

    public static Date addDate(int daysToAdd) {

	APPLICATION_LOGS.debug("Get the date after adding  ...");
	Date newDate = new Date();

	try {

	    // Add days to current date

	    newDate.setDate(newDate.getDate() + daysToAdd);

	    // Log result
	    APPLICATION_LOGS.debug("Date difference : " + newDate);

	}

	catch (Throwable addDateException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while getting the difference of date : " + addDateException.getMessage());

	}

	return newDate;

    }

    public static Date subtractDaysFromDate(Date Dischargedate, int dateDiff) {

	APPLICATION_LOGS.debug("Subtarcting " + dateDiff + " from " + Dischargedate + "...");
	Date newDate = new Date();

	try {

	    // Subtract days from current date
	    System.out.println();
	    newDate = new DateTime(Dischargedate).minusDays(dateDiff).toDate();

	    // Log result
	    APPLICATION_LOGS.debug("Date after subtracting " + dateDiff + " from " + Dischargedate + " : " + newDate);

	}

	catch (Throwable getDateDifferenceException) {

	    // Log error
	    APPLICATION_LOGS
		    .debug("Error while getting the difference of date : " + getDateDifferenceException.getMessage());

	}

	return newDate;

    }

    /***
     * Check if Alert is present
     */

    public static boolean isAlertPresent() {
	try {
	    driver.switchTo().alert();
	    return true;
	} catch (NoAlertPresentException Ex) {
	    return false;
	}
    }

    /*
     * public static String acceptAlert(String elemName) method specification :-
     *
     * 1) Accepts an alert 2) driver.switchTo().alert() -> Switch to the desired
     * alert 3) alert.accept() -> Accepts the alert
     *
     * @param : Name of the web element
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2AcceptAlert(String elemName) {

	APPLICATION_LOGS.debug("Accepting alert : " + elemName);

	try {

	    // Create a new alert object
	    Alert alert = driver2.switchTo().alert();

	    // Accept the alert
	    alert.accept();

	    // Log result
	    APPLICATION_LOGS.debug("Accepted alert : " + elemName);

	    return "Pass : Accepted the alert '" + elemName + "'";

	}

	catch (Throwable acceptAlertException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error came while accepting alert : " + acceptAlertException.getMessage());

	    return "Fail : Error came while accepting alert : " + acceptAlertException.getMessage();

	}

    }

    /**
     * Scrolls within an web element.
     * <ul>
     * <li><strong>pixelToScrollVertically:</strong> +ve value (scroll down) -ve
     * value (scroll top)
     * <li><strong>pixelToScrollHorizontally:</strong> +ve value (scroll left) -ve
     * value (scroll right))
     * </ul>
     *
     * @param locator
     *                                      Element locator
     * @param elemName
     *                                      Element name
     * @param pixelToScrollVertically
     *                                      Pixel to scroll vertically
     * @param pixelToScrollHorizontally
     *                                      Pixel to scroll horizontally
     *
     * @return void
     */

    public static void driver2ScrollWithinParticularElement(By locator, String elemName, int pixelToScrollVertically,
	    int pixelToScrollHorizontally) {

	APPLICATION_LOGS.debug("Scrolling within " + elemName + " ...");

	try {

	    // Initialize Javascript executor
	    JavascriptExecutor js = driver2;

	    // Scroll inside web element vertically
	    js.executeScript("arguments[0].scrollTop = arguments[1];", driver2.findElement(locator),
		    pixelToScrollVertically);

	    // Scroll inside web element horizontally
	    js.executeScript("arguments[0].scrollLeft = arguments[1];", driver2.findElement(locator),
		    pixelToScrollHorizontally);

	}

	catch (Throwable scrollException) {

	    APPLICATION_LOGS.debug("Error came while scrolling within the element : " + scrollException.getMessage());

	}

	APPLICATION_LOGS.debug("Scrolled within " + elemName);

    }

    /*
     * public static void fluentWait(final String locator) method specification :-
     *
     * 
     *
     * @param : Locator for the web element
     *
     * @return : none
     */
    public static void fluentWait(final String locator) {

	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
		.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
	wait.until(new Function<WebDriver, WebElement>() {
	    public WebElement apply(WebDriver driver) {
		return driver.findElement(By.xpath(locator));
	    }
	});

    }

    /*
     * public static String closeDriver() method specification :-
     *
     * 1) Closes the web driver 2) driver.close() -> Closes the webdriver
     *
     * @param : no parameters
     *
     * @return : Result of execution - Pass or fail (with cause)
     */

    public static String driver2CloseDriver() throws InterruptedException {

	APPLICATION_LOGS.debug("Closing the driver ...");

	try {

	    // Close the driver
	    // driver.close();
	    driver2.quit();

	    // Make driver to point to null
	    wbdv2 = null;

	    // Close IEDriverServer processes if browser is IE
	    if (CONFIG.getProperty("test_browser").equals("InternetExplorer")) {

		APPLICATION_LOGS.debug("Killing IEDriverServer process");

		// Kill IEDriverServer from Remote machine
		String remote_ip = CONFIG.getProperty("remote_ip");
		String domain = CONFIG.getProperty("domain");
		String username = CONFIG.getProperty("username");
		String password = CONFIG.getProperty("password");
		String command = "pskill \\\\" + remote_ip + " -u " + domain + "\\" + username + "-p " + password
			+ " iedriverserver.exe";
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);
		int exitVal = proc.waitFor();

		// If failed to kill try once with this
		if (exitVal == -1) {

		    try {

			WindowsUtils.killByName("IEDriverServer.exe");
			APPLICATION_LOGS.debug("Killed IEDriverServer process");

		    }

		    catch (WindowsRegistryException taskKillException) {
			APPLICATION_LOGS.debug("IE Driver Server already killed. Skipping ...");
		    }

		}

		else {
		    APPLICATION_LOGS.debug("Killed IEDriverServer process");
		}

	    }

	    // Log result
	    APPLICATION_LOGS.debug("Closed the driver");

	    return "Pass : Closed the driver";

	}

	catch (Throwable closeDriverException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error came while closing driver : " + closeDriverException.getMessage());

	    return "Fail : Error came while closing driver : " + closeDriverException.getMessage();

	}

    }

    /**
     * Move to web element and click. This function is useful to move mouse on the
     * webelement and click
     * 
     * <p>
     * This method <code>returns</code>:
     * <ul>
     * <li>Pass if moved to web element and clicked successfully</li>
     * <li>Fail if any exception occurs in between</li>
     * </ul>
     * 
     * @param EventFiringWebDriver
     *                                 driver
     * @param locator
     *                                 Element locator
     * @param elemName
     *                                 Element name
     * 
     * @return Pass/Fail
     */

    public static String moveToWebElementAndClick(By locator, String elemName) {

	APPLICATION_LOGS.debug("Moving to : " + elemName + " ...");

	// Initiate Actions class
	Actions action = new Actions(driver);

	try {

	    // Wait for check-box to appear on the page
	    waitForElementToLoad(locator);

	    // Move to element
	    action.moveToElement(driver.findElement(locator)).click().build().perform();

	    // Log the result
	    APPLICATION_LOGS.debug("Moved to : " + elemName);

	    return "Pass : Moved to : " + elemName;

	}

	catch (Throwable moveToElementException) {

	    APPLICATION_LOGS.debug("Error came while moving to : " + moveToElementException.getMessage());

	    return "Fail : Error came while moving to " + moveToElementException.getMessage();

	}

    }

    // Get count of rows in the table
    public static String getTableRowCount(By locator, String elemName) {

	APPLICATION_LOGS.debug("Getting count of rows in table : " + elemName);
	int count = 0;

	try {
	    count = driver.findElements(locator).size();
	} catch (Throwable tableCountException) {
	    APPLICATION_LOGS.debug("Unable to get the count of rows in table.");
	    return failTest + " : Unable to get the count of rows in table.";
	}

	System.out.println(count);
	return String.valueOf(count);
    }

    public static Date addDate(Date date, int daysToAdd) {

	APPLICATION_LOGS.debug("Get the date after adding  ...");
	Date newDate = new Date();
	try {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, daysToAdd);
	    newDate = c.getTime();
	}

	catch (Throwable addDateException) {

	    // Log error
	    APPLICATION_LOGS.debug("Error while adding days to passed date : " + addDateException.getMessage());

	}

	return newDate;

    }

}
