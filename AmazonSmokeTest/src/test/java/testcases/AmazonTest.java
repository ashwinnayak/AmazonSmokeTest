package testcases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.openqa.selenium.By;

import testscripts.AmazonLibrary;
import testscripts.DriverScript;
import testscripts.FunctionLibrary;
import util.TestUtil;

//All Hail Automation !

public class AmazonTest extends DriverScript {
    /*
     * Name of the WebElements present on the WebPage
     */
    /* .............. Locators for the test ................. */
    public static By locatorSearchField = By.id("twotabsearchtextbox");
    public static By locatorSearchButton = By.className("nav-input");
    public static By locatorLancerShoe = By.xpath("//*[contains(@src,'81LvL0bXVwL._AC_UL320_.jpg')]");
    public static By locatorShowSizeDropdown = By.id("native_dropdown_selected_size_name");
    public static By locatorAddToCartButton = By.id("add-to-cart-button");
    public static By locatorCartButton = By.id("hlb-view-cart-announce");
    public static By locatorProductName = By.xpath(
	    "//a[@class=\"a-link-normal sc-product-link\"]/span[contains(text(),\"Lancer Men's Black Formal Shoes-10 UK/India\")]");

    public static Boolean ProductPageShot;
    public static Boolean CartPageShot;

    // Navigate to the App, search for a product, add to cart, take screenshot
    public static String navigateToAmazonSearchForLancerShoesAddToCartAndTakeScreenshot()
	    throws SQLException, InterruptedException, IOException {

	APPLICATION_LOGS.debug(
		"Executing test case : Navigate To Amazon, Search For Lancer Shoes, Add To Cart And Take a Screenshot");

	// Navigate to the App
	methodReturnResult = AmazonLibrary.navigateToAppWebsite();
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	FunctionLibrary.waitForElementToLoad(locatorSearchField);

	// input product name
	methodReturnResult = FunctionLibrary.clearAndInput(locatorSearchField, "Search Field",
		"Lancer Men's Formal Shoes");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	// click on search button
	methodReturnResult = FunctionLibrary.clickAndWait(locatorSearchButton, "Search Button");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	// assert page title
	methodReturnResult = FunctionLibrary.assertTitle("Amazon.in: Lancer Men's Formal Shoes");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	FunctionLibrary.waitForElementToLoad(locatorLancerShoe);

	// verify if element displayed
	if (FunctionLibrary.isElementDisplayed(locatorLancerShoe, "Lancer Shoe")) {
	    methodReturnResult = FunctionLibrary.clickAndWait(locatorLancerShoe, "Lancer Shoe");
	    if (methodReturnResult.contains(failTest)) {
		return methodReturnResult;
	    }
	}

	// switch tab
	ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
	driver.switchTo().window(tabs2.get(1));

	// take screenshot of product page
	if (FunctionLibrary.isElementDisplayed(locatorShowSizeDropdown, "Shoe Size Dropdown")) {
	    // Take screenshot of product page
	    String fileName = "LancerMenFormalShoeProductPage.jpg";
	    String path = screenshotPath + fileName;
	    TestUtil.takeScreenShot(path);
	    ProductPageShot = true;
	}

	// choose show size
	methodReturnResult = FunctionLibrary.selectValueByVisibleText(locatorShowSizeDropdown, "10 UK/India (44 EU)",
		"Shoe Size Dropdown");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	Thread.sleep(3000);

	// click on add to cart button
	methodReturnResult = FunctionLibrary.clickAndWait(locatorAddToCartButton, "Add To Cart Button");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	FunctionLibrary.waitForPageToLoad();

	// click on cart button
	if (FunctionLibrary.isElementDisplayed(locatorCartButton, "Cart Button")) {
	    methodReturnResult = FunctionLibrary.clickAndWait(locatorCartButton, "Cart Button");
	    if (methodReturnResult.contains(failTest)) {
		return methodReturnResult;
	    }
	}

	FunctionLibrary.waitForPageToLoad();

	// assert page title
	methodReturnResult = FunctionLibrary.assertTitle("Amazon.in Shopping Cart");
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	// take screenshot of product
	if (FunctionLibrary.isElementDisplayed(locatorProductName, "Product")) {
	    // Take screenshot of product page
	    String fileName = "Cart.jpg";
	    String path = screenshotPath + fileName;
	    TestUtil.takeScreenShot(path);
	    CartPageShot = true;
	}

	Thread.sleep(5000);

	// Close Driver
	FunctionLibrary.closeDriver();

	// verify if screenshots taken
	if (ProductPageShot == true && CartPageShot == true) {
	    return "Pass: Product Screenshots can be found in reports folder";
	}

	return "Fail: Not able to take screenshots";

    }

}