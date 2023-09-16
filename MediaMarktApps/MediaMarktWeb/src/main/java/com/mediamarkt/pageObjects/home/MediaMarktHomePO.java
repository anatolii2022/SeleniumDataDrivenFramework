package com.mediamarkt.pageObjects.home;

import com.antomate.driver.CreateDriver;
import com.antomate.utils.BrowserUtils;
import com.antomate.utils.Global_VARS;
import com.antomate.utils.JavaScriptUtils;
import com.mediamarkt.pageObjects.base.MediaMarktWebBasePO;
import com.mediamarkt.pageObjects.login.MediaMarktLoginPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v113.network.model.IPAddressSpace;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.concurrent.TimeoutException;

/**
 * @param <M>
 * @author Anatolii
 * <p>
 * MediaMarkt Welcome Sub-class Page Object Class
 */
public class MediaMarktHomePO<M extends WebElement> extends MediaMarktWebBasePO<M> {

    //local variables
    private static final String HOME_TITLE = "Elektronik, Trends & Technik kaufen im Onlineshop | MediaMarkt";
    private static final String PATTERN = "Alle";
    private static final String MENU_TITLE = "Alle Kategorien";

    public enum MENU_LINKS {
        COMPUTER_BUERO, TV_AUDIO, SMARTPHONE_TARIFE, HAUSHALTSGROSSGERAETE, HAUSHALT_WOHNEN,
        FOTO_DROHNEN, GESUNDHEIT_KOERPERPFLEGE, FILM_MUSIK, SPORT_FREIZEIT, SMART_HOME, HEIMWERKEN_GARTEN,
        SPIELZEUG_BABY, ERNEUBARE_ENERGIE
    }

    //constructor
    public MediaMarktHomePO() throws Exception {
        super();
        setTitle(HOME_TITLE);
    }

    //elements
    @FindBy(id = "mms-app-header-category-button")
    protected M categoryOpenButton;
    @FindBy(id = "app-header-categorymenu-close-button")
    protected M categoryCloseButton;

    @FindBy(xpath = "//button[@data-test='myaccount-dropdown-button']")
    protected M myAccountDropdownButton;

    @FindBy(xpath = "//a[href='/webapp/wcs/stores/servlet/MultiChannelDisplayBasket']")
    protected M warenKorbButton;

    @FindBy(id = "search-form")
    protected M searchBox;

    @FindBy(xpath = "//span[contains(text(),'Abmelden')]")
    protected M abmeldenButton;

    @FindBy(xpath = "//h4[contains(text(),'Alle Kategorien')]")
    protected M categoryHeader;

    @FindBy(xpath = "//div[@data-test='myaccount-dropdown-desktop-list']")
    protected M myAccountDropdownMenu;

    //abstract methods

    /**
     * setTitle method to set page title
     *
     * @param pageTitle
     */
    @Override
    protected void setTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * getTitle method to get page title
     *
     * @return String
     */
    @Override
    public String getTitle() {
        return this.pageTitle;
    }

    //common methods

    /**
     * gotoHome method to navigate to Home Page
     */
    public void gotoHome() throws Exception {
        BrowserUtils.waitForClickable(mediaMarktImg, Global_VARS.TIMEOUT_ELEMENT);
        mediaMarktImg.click();
        BrowserUtils.waitForTitle(HOME_TITLE, Global_VARS.TIMEOUT_MINUTE);
    }


    /**
     * gotoAllCategories method to open menu
     */
    public void gotoAllCategories() throws Exception {
        categoryOpenButton.click();
        BrowserUtils.waitFor(categoryHeader, Global_VARS.TIMEOUT_ELEMENT);
    }

    /**
     * navigateMenuLink method to navigate page menu links
     *
     * @param link
     * @param title
     * @throws AssertionError
     */
    public void navigateMenuLink(MENU_LINKS link, String title) throws Exception {
        String pattern = null;
        String getTitle = null;
        WebDriver driver = CreateDriver.getInstance().getDriver();

        switch (link) {
            case COMPUTER_BUERO -> pattern = "computer-b%C3%BCro-344";
            case TV_AUDIO -> pattern = "tv-audio-202";
            case SMARTPHONE_TARIFE -> pattern = "smartphone-tarife-577";
            case HAUSHALTSGROSSGERAETE -> pattern = "haushaltsgro%C3%9Fger%C3%A4te-8000";
            case HAUSHALT_WOHNEN -> pattern = "haushalt-wohnen-1";
            case FOTO_DROHNEN -> pattern = "foto-drohnen-523";
            case GESUNDHEIT_KOERPERPFLEGE -> pattern = "gesundheit-körperpflege-22000";
            case FILM_MUSIK -> pattern = "film-musik-485";
            case SPORT_FREIZEIT -> pattern = "sport-freizeit-145";
            case SMART_HOME -> pattern = "smart-home-125";
            case HEIMWERKEN_GARTEN -> pattern = "heimwerken-garten-115";
            case SPIELZEUG_BABY -> pattern = "spielzeug-baby-8007";
            case ERNEUBARE_ENERGIE -> pattern = "erneuerbare-energien-2000";
        }

        //Firefox occasionally fails to execute WebDriver API click
        String query = "//a[@href='/de/category/" + pattern + ".html']";

        try {
            driver.findElement(By.xpath(query)).click();
            getTitle = driver.getTitle();
            BrowserUtils.waitForTitle(getTitle, Global_VARS.TIMEOUT_ELEMENT);
        } catch (Exception e) {
            JavaScriptUtils.click(By.xpath(query));
            getTitle = driver.getTitle();
            BrowserUtils.waitForTitle(getTitle, Global_VARS.TIMEOUT_ELEMENT);
        }

        if (JavaScriptUtils.isPageReady(driver)) {
            Assert.assertEquals(getTitle, title, "Navigate Menu Link");
        } else {
            CreateDriver.getInstance().driverWait(Global_VARS.TIMEOUT_MINUTE);
            Assert.assertEquals(getTitle, title, "Navigate Menu Link");
        }


    }

    /**
     * set Text in SearchBox
     */
    public void setTextSearchBox(String text) {
        // add a method to BrowserUtils Class to waitForVisible()
        searchBox.sendKeys(text);
    }

    /**
     * navigate to MyAccount
     */
    public void gotoMyAccount() throws Exception {
        BrowserUtils.waitForClickable(myAccountDropdownButton, Global_VARS.TIMEOUT_ELEMENT);
        myAccountDropdownButton.click();
    }

    /**
     * navigate to Warenkorb
     */
    public void gotoWarenkorb() throws Exception {
        BrowserUtils.waitForClickable(warenKorbButton, Global_VARS.TIMEOUT_ELEMENT);
        warenKorbButton.click();
    }

    /**
     * logout method to logout
     */
    public void logout() throws Exception {
        gotoMyAccount();
        BrowserUtils.waitForClickable(abmeldenButton, Global_VARS.TIMEOUT_ELEMENT);
        abmeldenButton.click();
        BrowserUtils.waitForGone(myAccountDropdownMenu, Global_VARS.TIMEOUT_ELEMENT);
    }

}
