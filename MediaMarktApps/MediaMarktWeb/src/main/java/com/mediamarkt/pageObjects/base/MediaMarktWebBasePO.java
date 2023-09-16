package com.mediamarkt.pageObjects.base;

import com.antomate.driver.CreateDriver;
import com.antomate.utils.BrowserUtils;
import com.antomate.utils.Global_VARS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * @author Anatolii Vasylenko
 * MediaMarkt Web App Base Page Object Class
 */
public abstract class MediaMarktWebBasePO<M extends WebElement> {

    //local variable
    protected String pageTitle = "";

    //constructor
    public MediaMarktWebBasePO() throws Exception {
        PageFactory.initElements(CreateDriver.getInstance().getDriver(), this);
    }

    //element
    @FindBy(xpath = "//img[@src='https://cms-images.mmst.eu/2rj3gcd43pmw/2xslVTe29DChwWBl7wq8if/d22b63ac87845dbeddb5a963f8ed90b4/MMSE_Logo_new.svg?q=80']")
    protected M mediaMarktImg;


    // abstract methods
    protected abstract void setTitle(String pageTitle);

    protected abstract String getTitle();

    //common methods

    /**
     * verifyTitle method to verify page title
     *
     * @param title
     * @throws AssertionError
     */
    public void verifyTitle(String title) throws AssertionError {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        Assert.assertEquals(driver.getTitle(), title, "Verify Page Title");

    }

    /**
     * navigate method to switch pages in app
     *
     * @param page
     * @throws Exception
     */
    public void navigate(String page) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        BrowserUtils.waitForClickable(By.xpath("//a[contains(text(),'" + page + "')]"), Global_VARS.TIMEOUT_MINUTE);
        driver.findElement(By.xpath("//a[contains(text(),'" + page + "')]")).click();
        //wait for page title
        BrowserUtils.waitForTitle(this.getTitle(), Global_VARS.TIMEOUT_ELEMENT);
    }

    /**
     * loadPage method to navigate to target URL
     *
     * @param url
     * @param timeout
     * @throws Exception
     */
    public void loadPage(String url, int timeout) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        driver.navigate().to(url);
        //wait for page URL
        BrowserUtils.waitForURL(Global_VARS.LOGIN_TARGET_URL, timeout);
    }

    /**
     * verifyText method to verify pag text
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifySpan(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();
        getText = driver.findElement(By.xpath("//span[contains(text), '" + pattern + "')]")).getText();
        Assert.assertEquals(getText, text, "Verify Span Text");
    }

    /**
     * verifyHeading method to verify page headings
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifyHeading(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();
        getText = driver.findElement(By.xpath("//h1[contains(text(), '" + pattern + "')]")).getText();
        Assert.assertEquals(getText, text, "Verify Heading Text");
    }

    /**
     * verifyParagraph method to verify paragraph text
     *
     * @param pattern
     * @param text
     * @throws AssertionError
     */
    public void verifyParagraph(String pattern, String text) throws AssertionError {
        String getText = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();
        getText = driver.findElement(By.xpath("//p[contains(text(), '" + pattern + "')]")).getText();
        Assert.assertEquals(getText, text, "Verify Paragaph Text");
    }


    /**
     * verifyLogoImgSrc method to verify logo page image source
     * @param src
     * @throws AssertionError
     */
    public void verifyLogoImgSrc(String src) throws AssertionError {
        String getText = mediaMarktImg.getAttribute("src");
        Assert.assertEquals(getText, src, "Verify Logo Image Source");

    }

}
