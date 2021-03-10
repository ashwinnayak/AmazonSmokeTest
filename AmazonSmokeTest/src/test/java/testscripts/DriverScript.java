package testscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import datatable.XlsReader;
import jxl.JXLException;
import jxl.read.biff.BiffException;
import reports.ReportUtil;
import util.TestUtil;

public class DriverScript {

    // All Hail Automation !

    public static Properties CONFIG;
    public static Properties OR;
    public static Properties APPTEXT;
    public static Properties LOG;
    public static XlsReader controller;
    public static XlsReader testData;
    public static WebDriver wbdv = null;
    public static EventFiringWebDriver driver = null;
    public static String navigationBlockedTitle = "Certificate Error: Navigation Blocked";
    public static WebDriver wbdv2 = null;
    // public static EventFiringWebDriver driver = null;
    public static EventFiringWebDriver driver2 = null;
    public static Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
    public static String currentTest;
    public static int testRepeat;
    public static String object;
    public static int Data_Row_No;
    public static String keyword;
    public static String currentTSID;
    public static String stepDescription;
    // public static String proceedOnFail;
    public static String testStatus;
    public static DesiredCapabilities dc = null;
    public static URL remote_url = null;
    public static java.sql.Statement stmt;
    public static ResultSet rs;
    public static Connection con;
    public static String defaultWindow;
    public static String firstSheetName;
    public static String failTest = "Fail";
    public static String methodReturnResult = null;
    public static Boolean booleanReturnResult = null;
    public static String currentSystemDate = null;
    public static Boolean checkStatus = null;
    public static Boolean checkAlertPresent = false;
    public static Boolean highlightElement = true;
    public static String expectedTitle = null;

    // public static String RUN_DATE = TestUtil.now("ddMMyyhhmmss").toString();

    public static String screenshotPath = System.getProperty("user.dir") + "/Report/";

    // Database connection
    public static String hostname = null;
    public static String port = null;
    public static String sid = null;
    public static String connectionString = null;
    public static String dbUsername = null;
    public static String dbPassword = null;

    @BeforeClass
    public static void initialize() throws IOException {

	// Override default J2SE built-in workable logger built-in
	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

	// Locates and loads the config properties
	CONFIG = new Properties();
	FileInputStream fs = new FileInputStream(
		System.getProperty("user.dir") + "/src/test/java/config/config.properties");
	CONFIG.load(fs);

	// Locates controller sheet
	controller = new XlsReader(System.getProperty("user.dir") + "/src/test/java/config/controller.xls");

	// Locates testData sheet
	testData = new XlsReader(System.getProperty("user.dir") + "/src/test/java/config/testData.xls");

	// Start the process of HTML report generation
	ReportUtil.startTesting(System.getProperty("user.dir") + "/Report/index.html",
		TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), CONFIG.getProperty("env"), CONFIG.getProperty("version"),
		CONFIG.getProperty("test_browser"), CONFIG.getProperty("dischargeCentralURL"));

    }

    @Before
    // Connects to database
    public void databaseConnectivity() {

	try {

	    // Register Oracle driver
	    java.sql.DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

	    // Get the hostname
	    hostname = testData.getCellData("DB_Info", "Hostname", 1);

	    // Get the port
	    port = testData.getCellData("DB_Info", "Port", 1);

	    // Get the SID
	    sid = testData.getCellData("DB_Info", "SID", 1);

	    // Get the username
	    dbUsername = testData.getCellData("DB_Info", "Username", 1);

	    // Get the password
	    dbPassword = testData.getCellData("DB_Info", "Password", 1);

	    // Make connection string
	    connectionString = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + sid;

	    // Connect to the database
	    // con = java.sql.DriverManager.getConnection(connectionString, dbUsername,
	    // dbPassword);
	    // con.setAutoCommit(false);

	}

	catch (Exception e) {
	    e.printStackTrace();
	}

    }

    @Test
    public void testApp() throws NumberFormatException, BiffException, JXLException, IOException {

	String startTime = null;

	// Get the first sheet name under 'controller.xls'

	firstSheetName = controller.getFirstSheetname();

	ReportUtil.startSuite(firstSheetName);

	for (int tcid = 1; tcid < controller.getRowCount(firstSheetName); tcid++) {

	    // Stores the current sub-module
	    currentTest = controller.getCellData(firstSheetName, "TCID", tcid).trim();

	    // Runs the respective sub-module if Runmode for the
	    // sub-module is
	    // 'Y'
	    if (controller.getCellData(firstSheetName, "Runmode", tcid).equals("Y")) {

		APPLICATION_LOGS.debug("Executing test : " + currentTest);

		// Initialize start time of test
		startTime = TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");

		// Implement keyword
		for (int tsid = 1; tsid < controller.getRowCount(currentTest); tsid++) {

		    // values from xls
		    // Stores the current keyword
		    keyword = controller.getCellData(currentTest, "Keyword", tsid).trim();

		    // Stores the current TSID
		    currentTSID = controller.getCellData(currentTest, "TSID", tsid).trim();

		    // Stores the current description
		    stepDescription = controller.getCellData(currentTest, "Description", tsid).trim();

		    try {

			Method method = Keyword.class.getMethod(keyword);
			String result = (String) method.invoke(method);
			APPLICATION_LOGS.debug("Result of test case execution - " + result);

			if (!result.startsWith("Fail")) {
			    ReportUtil.addKeyword(stepDescription, keyword, result, null);
			}

			// Take screenshot - only on
			// error
			if (result.startsWith("Fail")) {

			    testStatus = "Fail";

			    // Give a fileName for
			    // the screenshot and
			    // store
			    String fileName = "Suite1_TC" + tcid + "_TS" + tsid + "_" + keyword + testRepeat + ".jpg";
			    String path = screenshotPath + fileName;
			    TestUtil.takeScreenShot(path);
			    APPLICATION_LOGS.debug("SCREENSHOT taken under : " + path);

			    // Write the test result
			    // to HTML report
			    ReportUtil.addKeyword(stepDescription, keyword, result, fileName);

			    /*
			     * if (proceedOnFail.equalsIgnoreCase("N")) { break; }
			     */

			}

			if (wbdv != null) {
			    FunctionLibrary.closeDriver();
			}
			if (wbdv2 != null) {
			    FunctionLibrary.driver2CloseDriver();
			}
		    }

		    catch (Throwable testException) {
			APPLICATION_LOGS.debug("Error came : " + testException.getMessage());
		    }

		} // keywords -inner for loop

		// Report pass or fail
		if (testStatus == null) {
		    testStatus = "Pass";
		}
		APPLICATION_LOGS.debug("Result of the '" + currentTest + "' test suite execution - " + testStatus);
		ReportUtil.addTestCase(currentTest, startTime, TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);
	    }

	    else {
		APPLICATION_LOGS.debug("Skipping the test : " + currentTest);
		testStatus = "Skip";

		// Report skipped
		ReportUtil.addTestCase(currentTest, TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
			TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);

	    }
	    testStatus = null;
	}

	// End test reporting
	ReportUtil.endSuite();
    }

    @AfterClass
    public static void endScript() throws BiffException, IOException {

	// Update test end time under HTML test report
	ReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));

	APPLICATION_LOGS.debug("*** Test run finished ***");

    }

}
