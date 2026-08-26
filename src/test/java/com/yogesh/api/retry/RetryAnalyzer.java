package com.yogesh.api.retry;

import com.yogesh.api.config.ConfigManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer

{
    private int retryCount ;

    @Override
    public boolean retry(ITestResult result) {
      if(retryCount < ConfigManager.getRetryCount()){
          System.out.println("Retry Attempt : " + (retryCount + 1));
          retryCount++;
          return true;

      }
      return false;
    }
}
