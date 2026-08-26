package com.yogesh.api.reporting;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> EXTENT_TEST =
            new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void set(ExtentTest extentTest) {
        EXTENT_TEST.set(extentTest);
    }

    public static ExtentTest get() {
        return EXTENT_TEST.get();
    }

    public static void clear() {
        EXTENT_TEST.remove();
    }
}
