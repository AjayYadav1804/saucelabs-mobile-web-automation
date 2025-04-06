package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReportManager {
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    public static synchronized void initializeReport() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
            String path = System.getProperty("user.dir") + "/reports/testReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Sauce Lab Test Automation Report");
            sparkReporter.config().setReportName("Sauce Lab Test Execution Report");
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Environment", "QA");
            extentReports.setSystemInfo("Tester", "Ajay Yadav");
            logger.info("ExtentReports initialized");
        }
    }

    public static synchronized ExtentTest createTest(String testName, String description) {
        if (extentReports == null) {
            logger.error("ExtentReports not initialized. Cannot create test.");
            return null;
        }
        ExtentTest test = extentReports.createTest(testName, description);
        extentTest.set(test);  // Set the test in ThreadLocal
        logger.info("Created test: {} - {}", testName, description);
        return test;
    }

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void endReport() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("ExtentReports flushed");
        }
    }
}