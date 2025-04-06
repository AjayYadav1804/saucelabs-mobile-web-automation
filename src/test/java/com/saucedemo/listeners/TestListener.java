package com.saucedemo.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.saucedemo.utils.EmailSender;
import com.saucedemo.utils.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting Test Suite: {}", context.getName());
        //ExtentReportManager.initializeReport();
    }

  
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finished Test Suite: {}", context.getName());
        
        // Define the path to the report
        String reportPath = System.getProperty("user.dir") +"/reports/testReport.html";
   
        // Send the email with the report attached
        EmailSender.sendEmailWithAttachment(
                "TestAutomation1.Ajay@gmail.com",  // Replace with recipient
                "gajayyadav1996@gmail.com",  // Replace with CC recipient, or null/empty if not needed
                "Test Automation Report",
                "Please find attached the test automation report.",
                reportPath
        );
        logger.info("Report email sent.");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting Test: {}", result.getMethod().getMethodName());
        //ExtentReportManager.createTest(result.getMethod().getMethodName(), 
         //                              result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test skipped");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test Failed but within success percentage: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test failed within success percentage");
        }
    }
}