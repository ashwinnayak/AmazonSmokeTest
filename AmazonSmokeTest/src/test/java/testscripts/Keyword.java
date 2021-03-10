package testscripts;

import java.io.IOException;
import java.sql.SQLException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import testcases.AmazonTest;

public class Keyword {
    public static String methodReturnResult = null;

    /*****************************
     * DischargeComplete
     * 
     * @throws WriteException
     * @throws RowsExceededException
     * @throws SQLException
     * @throws ParseException
     * @throws FindFailed
     *****************************************/

    // Navigate to the App, search for a product, add to cart, take screenshot
    public static String navigateToAmazonSearchForLancerShoesAddToCartAndTakeScreenshot() throws BiffException,
	    InterruptedException, IOException, RowsExceededException, WriteException, SQLException {
	return AmazonTest.navigateToAmazonSearchForLancerShoesAddToCartAndTakeScreenshot();
    }
}
