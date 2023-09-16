package com.antomate.utils;

import java.io.File;

/**
 * @author Anatolii Vasylenko
 */
public class Global_VARS {
    //browser defaults
    public static final String BROWSER = "firefox";
    public static final String PLATFORM = "windows";
    public static final String ENVIRONMENT = "local";
    public static String DEF_BROWSER = null;
    public static String DEF_PLATFORM = null;
    public static String DEF_ENVIRONMENT = null;

    // suite folder defaults
    public static final String SUITE_NAME = "suite";
    public static final String HOME_TARGET_URL = "https://www.mediamarkt.de/";

    public static final String LOGIN_TARGET_URL = "https://www.mediamarkt.de/de/myaccount/auth/login?redirectURL=%2F";

    public static String propFile = "D:\\01_software_development\\03_Workspaces\\01_intellij-workspace\\SeleniumDataDrivenFramework\\MediaMarktApps\\SeleniumDataDrivenFrameworkJSON\\src\\main\\java\\com\\antomate\\driver\\selenium.properties";
    public static final String SE_PROPS = new File(propFile).getAbsolutePath();

    public static final String TEST_OUTPUT_PATH = "test-output/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public static final String REPORT_PATH = TEST_OUTPUT_PATH + "Reports/";
    public static final String REPORT_CONFIG_FILE = "D:\\01_software_development\\03_Workspaces\\01_intellij-workspace\\SeleniumDataDrivenFramework\\MediaMarktApps\\SeleniumDataDrivenFrameworkJSON\\src\\main\\resources\\extent-config.xml";
    //suite timeout defaults
    public static final int TIMEOUT_SECOND = 1;
    public static final int TIMEOUT_MINUTE = 60;
    public static final int TIMEOUT_ELEMENT = 10;
}
