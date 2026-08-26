package com.yogesh.api.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static ExtentReports getExtentReports() {
        if (extent == null) {

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("reports/AutomationReport.html");
            sparkReporter.config().setDocumentTitle("API Automation Report");
            sparkReporter.config().setReportName("Rest Assured Automation Framework");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

        }
        return extent;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
