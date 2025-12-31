package com.restassured.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final String reportPath = "test-output/ExtentReport/ExtentReport.html";
    
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            createExtentReports();
        }
        return extent;
    }
    
    private static void createExtentReports() {
        String reportDir = "test-output/ExtentReport";
        File reportDirFile = new File(reportDir);
        if (!reportDirFile.exists()) {
            reportDirFile.mkdirs();
        }
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("REST Assured Test Automation Report");
        sparkReporter.config().setReportName("REST Assured API Test Report");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Automation Tool", "REST Assured + Selenium");
    }
    
    public static void tearDown() {
        if (extent != null) {
            extent.flush();
        }
    }
}
