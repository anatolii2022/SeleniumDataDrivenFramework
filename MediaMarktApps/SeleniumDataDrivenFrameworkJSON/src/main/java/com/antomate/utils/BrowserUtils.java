package com.antomate.utils;

import com.antomate.driver.CreateDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @author Anatolii
 * <p>
 * Browser Utility Class
 */
public class BrowserUtils {

    /**
     * waitFor method to wait up to a designed period before throwing exception
     * (static locator)
     */
    public static void waitFor(WebElement element, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
//wait for the static element to appear
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    /**
     * waitForGone method to wait up a designated period before
     * throwing exception if element stiil esixts
     *
     * @param element
     * @throws Exception
     * @pram timer
     */
    public static void waitForGone(WebElement element, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        //wait for element to disappear
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(element)));

    }

    /**
     * waitForTitle method to poll page title
     *
     * @param title
     * @param timer
     * @throws Exception
     */
    public static void waitForTitle(String title, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(title)));
    }

    /**
     * waitForURL method to poll page URL
     *
     * @param url
     * @param timer
     * @throws Exception
     */
    public static void waitForURL(String url, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
    }

    /**
     * waitForClickable method to poll for clickable
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitForClickable(By by, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
    }

    /**
     * overloaded waitForClickable method to poll for clickable
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitForClickable(WebElement element, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    /**
     * elementExists - wrapper arround the WebDriver method to
     * return true or false
     *
     * @param element
     * @param timer
     * @throws Exception
     */
    public static boolean elementExists(WebElement element, int timer) throws Exception {
        try {
            WebDriver driver = CreateDriver.getInstance().getDriver();
            WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
            exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
            return true;
        } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

}
