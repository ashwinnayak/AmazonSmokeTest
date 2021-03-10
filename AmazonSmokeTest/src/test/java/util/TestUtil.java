package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.apache.commons.io.FileUtils;

import testscripts.DriverScript;



public class TestUtil extends DriverScript {


    // returns current date and time
    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());

    }
    
 // returns current date and time
    public static String nowInEST(String dateFormat) throws ParseException {
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String date = sdf.format(new Date());
		return date;
    }

    // Capture screenshot and store
    public static void takeScreenShot(String filePath) 
    {
    	
    	File scrFile = null;
    	
        try 
        {
        	
        	// Take Screenshot from Remote machine
        	if (CONFIG.getProperty("is_remote").equals("true")) 
        	{
        		
        		WebDriver augmentedDriver = new Augmenter().augment(wbdv);
        		scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        		
        	}
        	
        	// Take Screenshot from local machine
        	else
        		scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        		
        	// Store screenshot to the path provided
        	FileUtils.copyFile(scrFile, new File(filePath));

        }
        
        catch (Throwable screenCaptureException) 
        {
        	
            // Log error
            APPLICATION_LOGS.debug("Error while taking screenshot : " +screenCaptureException.getMessage());
            System.err.println("Error while taking screenshot : " +screenCaptureException.getMessage());
            
        }

    }
    
    

    // make zip of reports
    public static void zip(String filepath) {
        try {
            File inFolder = new File(filepath);
            File outFolder = new File("Reports.zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++) { in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in .read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}