package com.mediamarkt.tests.test_setup;

import com.antomate.driver.CreateDriver;
import com.antomate.utils.Global_VARS;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Test Setup Base Class
 *
 * @quthor Anatolii
 */
public abstract class BaseSetUp {
    // abstract methods
    protected abstract void testClassSetup(ITestContext context) throws Exception;

    protected abstract void testClassTeardown(ITestContext context) throws Exception;

    protected abstract void testMethodSetup(ITestResult result) throws Exception;

    protected abstract void testMethodTeardown(ITestResult result) throws Exception;

    //common methods
    @Parameters("environment")
    @BeforeSuite(alwaysRun = true, enabled = true)
    protected void suiteSetup(@Optional(Global_VARS.ENVIRONMENT) String environment, ITestContext context) throws Exception {
        Global_VARS.DEF_ENVIRONMENT = System.getProperty("environment", environment);
    }

    @AfterSuite(alwaysRun = true, enabled = true)
    protected void suiteTeardown(ITestContext context) throws Exception {

    }

    @Parameters({"browser", "platform"})
    @BeforeTest(alwaysRun = true, enabled = true)
    protected void testSetup(@Optional(Global_VARS.BROWSER) String browser, @Optional(Global_VARS.PLATFORM) String platform, ITestContext context) throws Exception {

        //global variable
        Global_VARS.DEF_BROWSER = System.getProperty("browser", browser);
        Global_VARS.DEF_PLATFORM = System.getProperty("platform", platform);

        //setup driver
        CreateDriver.getInstance().setDriver(Global_VARS.DEF_ENVIRONMENT, Global_VARS.DEF_BROWSER, Global_VARS.DEF_PLATFORM);
    }

    @AfterTest(alwaysRun = true, enabled = true)
    protected void testTeardown(ITestContext context) throws Exception {
        //close driver
        CreateDriver.getInstance().closeDriver();
    }

    @BeforeClass(alwaysRun = true, enabled = true)
    protected void classSetup(ITestContext context) throws Exception {

    }

    @AfterClass(alwaysRun = true, enabled = true)
    protected void classTeardown(ITestContext context) throws Exception {

    }

    @BeforeMethod(alwaysRun = true, enabled = true)
    protected  void methodSetup(ITestResult result) throws Exception {

    }

    @AfterMethod(alwaysRun = true, enabled = true)
    protected void methodTeardown(ITestResult result) throws Exception {

    }
}
