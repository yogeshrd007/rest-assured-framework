package com.yogesh.api.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.yogesh.api.reporting.ExtentManager;
import com.yogesh.api.reporting.ExtentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Extent Listener Started");

        ExtentTest extentTest = ExtentManager.getExtentReports()
                .createTest(result.getMethod().getMethodName());

        ExtentTestManager.set(extentTest);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> onTestSuccess called");
        ExtentTestManager.get().pass("Test Passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        if (result.getThrowable() != null) {
            ExtentTestManager.get().skip(result.getThrowable());
        } else {
            ExtentTestManager.get().skip("Test Skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(">>> onFinish called");
        ExtentManager.flush();
    }
}
