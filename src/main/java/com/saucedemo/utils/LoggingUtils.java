package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingUtils {

    private static final Logger logger = LogManager.getLogger(LoggingUtils.class);

    public static void logAction(WebDriver driver, String message) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            try {
                // Capture Screenshot
                String screenshotPath = captureScreenshot(driver, message);
                test.log(Status.INFO, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                logger.error("Error adding screenshot to Extent Report", e);
            }
        }
    }

    private static String captureScreenshot(WebDriver driver, String screenshotName) {
        // Clean the screenshot name to remove any illegal characters
        String sanitizedScreenshotName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Ensure the screenshots folder exists
        String screenshotsDir = System.getProperty("user.dir") + "/reports/screenshots";
        new File(screenshotsDir).mkdirs(); // Create the directory if it does not exist

        String dest = screenshotsDir + "/" + sanitizedScreenshotName + dateName + ".png";
        File destination = new File(dest);

        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            return null; // Handle as per your application's error strategy
        }
        return dest;
    }
}