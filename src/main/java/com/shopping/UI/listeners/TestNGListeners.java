package com.shopping.UI.listeners;

import com.shopping.utils.LogsManager;
import com.shopping.utils.ScreenShotUtils;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.File;

import static com.shopping.UI.drivers.DriverManager.isDriverSet;
import static com.shopping.utils.AllureUtils.attachLogToAllure;
import static com.shopping.utils.AllureUtils.createAllureEnvironmentFile;
import static com.shopping.utils.FilesUtils.CleanDirectory;
import static com.shopping.utils.FilesUtils.DeleteFiles;
import static com.shopping.utils.PropertiesUtils.loadProperties;

public class TestNGListeners implements ITestListener, IExecutionListener, IInvokedMethodListener {
    File allureResults = new File("test-outputs/allure-results");
    File Screenshots = new File("test-outputs/screenshots");

    @Override
    public void onExecutionStart() {
        LogsManager.info("Test Execution Started .......");
        loadProperties();
        DeleteFiles(allureResults);
        CleanDirectory(Screenshots);
        createAllureEnvironmentFile();

    }

    @Override
    public void onExecutionFinish() {
        LogsManager.info("Test Execution Finished .......");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        String[] groups = testResult.getMethod().getGroups();
        for (String group : groups) {
            Allure.label("tag", group);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (isDriverSet()) {
            if (method.isTestMethod()) {
                attachLogToAllure();
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS:
                        ScreenShotUtils.takeScreenshot("Passed-" + testResult.getName());
                        break;
                    case ITestResult.FAILURE:
                        ScreenShotUtils.takeScreenshot("failed-" + testResult.getName());
                        break;
                    case ITestResult.SKIP:
                        ScreenShotUtils.takeScreenshot("skipped-" + testResult.getName());
                        break;
                    default:
                        LogsManager.info("Test Method: " + method.getTestMethod().getMethodName() + " has an unknown status.");
                }
            }
        } else {
            LogsManager.info("API tests do not require a driver instance.");
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsManager.error("Test case " + result.getName() + " failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsManager.warn("Test case " + result.getName() + " was skipped.");
    }
}
