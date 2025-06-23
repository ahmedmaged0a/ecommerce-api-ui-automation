package com.shopping.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AllureUtils {
    public static final String ALLURE_RESULTS_PATH = "test-outputs/allure-results";
    static Properties props = new Properties();

    // Example: get values from config, system properties, or hardcode for now
    static String environment = PropertiesUtils.getPropertyValue("Environment");
    static String browser = PropertiesUtils.getPropertyValue("browserType");
    static String baseUrl = PropertiesUtils.getPropertyValue("baseURI");
    static String build = "1.0.0";
    static String executedBy = "Ahmed Maged";

    private AllureUtils() {
        super();
    }

    public static void attachLogToAllure() {
        try {
            File logFile = FilesUtils.getLatestFileFromDirectory(LogsManager.LOGS_PATH);
            if (!logFile.exists()) {
                LogsManager.warn("Log file does not exist: " + logFile.getAbsolutePath());
                return;
            }
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getAbsolutePath())));
        } catch (Exception e) {
            LogsManager.error("Failed to attach log file to Allure: " + e.getMessage());
        }
    }

    public static void attachScreenshotToAllure(String screenshotName) {
        try {
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(ScreenShotUtils.SCREENSHOT_PATH + screenshotName + ".png")));
        } catch (Exception e) {
            LogsManager.error("Failed to attach screenshot to Allure: " + e.getMessage());
        }
    }

    public static void createAllureEnvironmentFile() {
        props.setProperty("Environment", environment);
        props.setProperty("Browser", browser);
        props.setProperty("BaseURL", baseUrl);
        props.setProperty("Build", build);
        props.setProperty("ExecutedBy", executedBy);

        File resultsDir = new File(ALLURE_RESULTS_PATH);
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        File envFile = new File(resultsDir, "environment.properties");
        try (FileWriter writer = new FileWriter(envFile)) {
            props.store(writer, "Allure Environment Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
