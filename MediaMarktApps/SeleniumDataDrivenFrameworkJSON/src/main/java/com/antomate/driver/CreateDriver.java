package com.antomate.driver;

import com.antomate.utils.Global_VARS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Anatolii Vasylenko
 * Selenium Driver Class
 */
public class CreateDriver {
    //local variables
    private static CreateDriver instance = null;
    private static final int IMPLICIT_TIMEOUT = 15;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    private ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
    private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();

    private String getEnv = null;
    private Properties props = new Properties();

    //constructor
    private CreateDriver() {
    }

    /**
     * getInstance method to retrieve active driver instance
     *
     * @return CreateDriver
     */
    public static CreateDriver getInstance() {
        if (instance == null) {
            instance = new CreateDriver();
        }
        return instance;
    }

    /**
     * setDriver method to create driver instance
     *
     * @param browser
     * @param environment
     * @param platform
     * @param optPreferences
     * @throws Exception
     */
    @SafeVarargs
    public final void setDriver(String environment, String browser, String platform, Map<String, Object>... optPreferences) throws Exception {

        @SuppressWarnings("rawtypes")
        AbstractDriverOptions options = null;
        FirefoxOptions ffOptions = null;
        ChromeOptions chOptions = null;
        EdgeOptions edgeOptions = null;

        String localHub = "http://192.168.178.48:4444";
        String getPlatform = null;
        props.load(new FileInputStream(Global_VARS.SE_PROPS));

        switch (environment) {
            case "local":
                if (browser.equalsIgnoreCase("firefox")) {
                    ffOptions = new FirefoxOptions();
                    if (optPreferences.length > 0) {
                        processFFOptions(ffOptions, optPreferences);
                    }
                    webDriver.set(new FirefoxDriver(ffOptions));
                } else if (browser.equalsIgnoreCase("chrome")) {
                    chOptions = new ChromeOptions();
                    if (optPreferences.length > 0) {
                        processCHOptions(chOptions, optPreferences);
                    }
                    webDriver.set(new ChromeDriver(chOptions));
                } else if (browser.equalsIgnoreCase("MicrosoftEdge")) {
                    edgeOptions = new EdgeOptions();
                    if (optPreferences.length > 0) {
                        processEdgeOptions(edgeOptions, optPreferences);
                    }
                    webDriver.set(new EdgeDriver(edgeOptions));
                }
                break;
            case "remote":
                if (browser.equalsIgnoreCase("firefox")) {
                    options = new FirefoxOptions();
                    processOptions(options, optPreferences);
                } else if (browser.equalsIgnoreCase("chrome")) {
                    options = new ChromeOptions();
                    processOptions(options, optPreferences);
                } else if (browser.equalsIgnoreCase("MicrosoftEdge")) {
                    options = new EdgeOptions();
                    processOptions(options, optPreferences);
                }
                assert options != null;
                webDriver.set(new RemoteWebDriver(new URL(localHub), options));
                break;
        }

        getEnv = environment;
        getPlatform = platform;

        if (environment.equalsIgnoreCase("local")) {
            if (browser.equalsIgnoreCase("firefox")) {
                sessionId.set(((FirefoxDriver) webDriver.get()).getSessionId().toString());
                sessionBrowser.set(ffOptions.getBrowserName());
                sessionVersion.set(ffOptions.getBrowserVersion());
                sessionPlatform.set(getPlatform);
            } else if (browser.equalsIgnoreCase("chrome")) {
                sessionId.set(((ChromeDriver) webDriver.get()).getSessionId().toString());
                sessionBrowser.set(chOptions.getBrowserName());
                sessionVersion.set(chOptions.getBrowserVersion());
                sessionPlatform.set(getPlatform);
            } else if (browser.equalsIgnoreCase("MicrosoftEdge")) {
                sessionId.set(((EdgeDriver) webDriver.get()).getSessionId().toString());
                sessionBrowser.set(edgeOptions.getBrowserName());
                sessionVersion.set(edgeOptions.getBrowserVersion());
                sessionPlatform.set(getPlatform);
            }
        } else if (environment.equalsIgnoreCase("remote")) {
            sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
            sessionBrowser.set(options.getBrowserName());
            sessionVersion.set(options.getBrowserVersion());
            sessionPlatform.set(getPlatform);
        }

        System.out.println("\n*** TEST ENVIRONMENT = "
                + getSessionBrowser().toUpperCase()
                + " / " + getSessionPlatform().toUpperCase()
                + " / " + getEnv.toUpperCase()
                + " /Selenium Version="
                + props.getProperty("selenium.version")
                + " /Session ID="
                + getSessionId()
                + " ***\n");

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT));
        getDriver().manage().window().maximize();
    }

    /**
     * Overloaded setDriver method to switch driver to specific WebDriver if running
     * concurrent drivers
     */
    public void setDriver(WebDriver driver) {
        webDriver.set(driver);
        sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
        sessionBrowser.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getBrowserName());
        sessionPlatform.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getPlatformName().toString());
    }


    /**
     * getDriver method to retrieve active driver
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return webDriver.get();
    }

    /**
     * getCurrentDriver method will retrieve the active WebDriver
     */
    public WebDriver getCurrentDriver() throws Exception {
        return getInstance().getDriver();
    }

    /**
     * driverWait method pauses the driver in seconds
     */
    public void driverWait(int timer) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(timer));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * driverRefresh method reloads the current browser page
     */
    public void driverRefresh() {
        try {
            getCurrentDriver().navigate().refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * closeDriver method to close active driver
     */
    public void closeDriver() {
        try {
            getDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getSessionID method to retrieve active id
     *
     * @return String
     * @throws Exception
     */
    public String getSessionId() throws Exception {
        return sessionId.get();
    }

    /**
     * getSessionBrowser method to retrieve active browser
     *
     * @return String
     * @throws Exception
     */
    public String getSessionBrowser() throws Exception {
        return sessionBrowser.get();
    }

    /**
     * getSessionVersion method
     *
     * @return String
     * @throws Exception
     */
    public String getSessionVersion() throws Exception {
        return sessionVersion.get();
    }

    /**
     * getSessionPlatform method to retrieve active platform
     *
     * @return String
     * @throws Exception
     */
    public String getSessionPlatform() throws Exception {
        return sessionPlatform.get();
    }

    /**
     * process RemoteWebDriver optionsPreferences method to override default browser
     * driver behavior
     */
    private void processOptions(@SuppressWarnings("rawtypes") AbstractDriverOptions options, Map<String, Object>[] preferences) throws Exception {

        for (int i = 0; i < preferences.length; i++) {
            Object[] keys = preferences[i].keySet().toArray();
            Object[] values = preferences[i].values().toArray();

            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    options.setCapability(keys[j].toString(), (int) values[j]);
                } else if (values[j] instanceof Boolean) {
                    options.setCapability(keys[j].toString(), (boolean) values[j]);
                } else if (Boolean.parseBoolean(values[j].toString())) {
                    options.setCapability(keys[j].toString(), Boolean.valueOf(values[j].toString()));
                } else {
                    options.setCapability(keys[j].toString(), values[j].toString());
                }
            }
        }
    }

    /**
     * process Firefox Options Preferences method to override default browser driver
     * behavior
     */
    private void processFFOptions(FirefoxOptions ffOptions, Map<String, Object>[] preferences) throws Exception {
        for (int i = 0; i < preferences.length; i++) {
            Object[] keys = preferences[i].keySet().toArray();
            Object[] values = preferences[i].values().toArray();

            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    ffOptions.setCapability(keys[j].toString(), (int) values[j]);
                } else if (values[j] instanceof Boolean) {
                    ffOptions.setCapability(keys[j].toString(), (boolean) values[j]);
                } else if (Boolean.parseBoolean(values[j].toString())) {
                    ffOptions.setCapability(keys[j].toString(), Boolean.valueOf(values[j].toString()));
                } else {
                    ffOptions.setCapability(keys[j].toString(), values[j].toString());
                }

            }
        }
    }

    /**
     * process Chrome Options method to override defaults browser behavior
     */
    private void processCHOptions(ChromeOptions chOptions, Map<String, Object>[] preferences) throws Exception {
        for (int i = 0; i < preferences.length; i++) {
            Object[] keys = preferences[i].keySet().toArray();
            Object[] values = preferences[i].values().toArray();

            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    chOptions.setCapability(keys[j].toString(), (int) values[j]);
                } else if (values[j] instanceof Boolean) {
                    chOptions.setCapability(keys[j].toString(), (boolean) values[j]);
                } else if (Boolean.parseBoolean(values[j].toString())) {
                    chOptions.setCapability(keys[j].toString(), Boolean.valueOf(values[j].toString()));
                } else {
                    chOptions.setCapability(keys[j].toString(), values[j].toString());
                }

            }
        }

    }


    /**
     * process Edge Options method to override defaults browser behavior
     */
    private void processEdgeOptions(EdgeOptions edgeOptions, Map<String, Object>[] preferences) throws Exception {
        for (int i = 0; i < preferences.length; i++) {
            Object[] keys = preferences[i].keySet().toArray();
            Object[] values = preferences[i].values().toArray();

            for (int j = 0; j < keys.length; j++) {
                if (values[j] instanceof Integer) {
                    edgeOptions.setCapability(keys[j].toString(), (int) values[j]);
                } else if (values[j] instanceof Boolean) {
                    edgeOptions.setCapability(keys[j].toString(), (boolean) values[j]);
                } else if (Boolean.parseBoolean(values[j].toString())) {
                    edgeOptions.setCapability(keys[j].toString(), Boolean.valueOf(values[j].toString()));
                } else {
                    edgeOptions.setCapability(keys[j].toString(), values[j].toString());
                }

            }
        }

    }
}
