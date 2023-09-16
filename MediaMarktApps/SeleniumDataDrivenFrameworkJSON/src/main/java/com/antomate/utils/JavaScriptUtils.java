package com.antomate.utils;

import com.antomate.driver.CreateDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Selenium JavaScript Executor Utility Class
 */
public class JavaScriptUtils {
    //constructor
    public JavaScriptUtils() {

    }

    /**
     * execute - generic method to execute a non-parametrized JS command
     *
     * @param command
     */
    public static void execute(String command) {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(command);
    }

    /**
     * execute - overloaded method to execute a JS command on WebElement
     *
     * @param command
     * @param element
     */
    public static void execute(String command, WebElement element) {

        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(command, element);
    }

    /**
     * click - method to execute a JavaScript click event
     *
     * @param element
     */
    public static void click(WebElement element) {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].click();", element);
    }

    /**
     * click - overloaded method to execute JavaScript click event
     *
     * @param by
     */
    public static void click(By by) {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebElement element = driver.findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].click();", element);
    }

    /**
     * sendKeys - method to execute a JavaScript value event
     *
     * @param keys
     * @param element
     */
    public static void sendKeys(String keys, WebElement element) {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].value='" + keys + "':", element);
    }

    /**
     * isPageReady - method to verify that a page has completely rendered
     *
     * @param driver
     * @return boolean
     */
    public static boolean isPageReady(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return document.readyState").equals("complete");
    }

    /**
     * isAjaxReady - method to verify that an ajax control has rendered
     *
     * @param driver
     * @return boolean
     */
    public static boolean isAjaxReady(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return jQuery.active == 0");
    }
}
