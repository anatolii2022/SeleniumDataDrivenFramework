package com.mediamarkt.tests.home;

import com.antomate.driver.CreateDriver;
import com.antomate.driver.JSONDataProvider;
import com.antomate.utils.Global_VARS;
import com.mediamarkt.pageObjects.home.MediaMarktHomePO;
import com.mediamarkt.pageObjects.login.MediaMarktLoginPO;
import com.mediamarkt.tests.test_setup.BaseSetUp;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({com.antomate.listeners.ExtentTestNGIReporterListener.class, com.antomate.listeners.TestNGConsoleRunner.class})
public class MediaMarktNavigateMenuTest extends BaseSetUp {
    //local variables
    private MediaMarktLoginPO<WebElement> loginPO = null;
    private MediaMarktHomePO<WebElement> homePO = null;
    private static final String DATA_FILE = "src\\test\\resources\\homedata\\MediaMarktNavigateMenu.json";

    //abstract methods
    @Override
    @BeforeClass
    protected void testClassSetup(ITestContext context) throws Exception {
        //instantiate page object classes
        loginPO = new MediaMarktLoginPO<WebElement>();
        homePO = new MediaMarktHomePO<WebElement>();

        //set datafile for data provider
        JSONDataProvider.dataFile = DATA_FILE;

        //load page
        loginPO.loadPage(Global_VARS.LOGIN_TARGET_URL, Global_VARS.TIMEOUT_MINUTE);
        loginPO.denyCookies();

    }

    @Override
    @AfterClass
    protected void testClassTeardown(ITestContext context) throws Exception {

    }

    @Override
    @BeforeMethod
    protected void testMethodSetup(ITestResult result) throws Exception {

    }

    @Override
    @AfterMethod
    protected void testMethodTeardown(ITestResult result) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        if (!driver.getTitle().equals("Elektronik, Trends & Technik kaufen im Onlineshop | MediaMarkt")) {
            homePO.gotoHome();
        }

    }

    //test methods
    @Test(dataProvider = "fetchDataJSON", dataProviderClass = JSONDataProvider.class)
    public void tc001_login(String rowID, String description, JSONObject testData) throws Exception {
        String email = testData.get("email").toString();
        String password = testData.get("password").toString();
        String title = testData.get("title").toString();
        loginPO.login(email, password);
        loginPO.verifyTitle(title);
    }


    @Test(dataProvider = "fetchDataJSON", dataProviderClass = JSONDataProvider.class)
    public void tc002_navigateToMenu(String rowID, String description, JSONObject testData) throws Exception {

        homePO.gotoAllCategories();
        homePO.navigateMenuLink(MediaMarktHomePO.MENU_LINKS.valueOf(testData.get("element").toString()), testData.get("title").toString());
    }
}
