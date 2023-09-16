package com.mediamarkt.pageObjects.login;

import com.antomate.driver.CreateDriver;
import com.antomate.utils.BrowserUtils;
import com.antomate.utils.Global_VARS;
import com.mediamarkt.pageObjects.base.MediaMarktWebBasePO;
import com.mediamarkt.pageObjects.home.MediaMarktHomePO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class MediaMarktLoginPO<M extends WebElement> extends MediaMarktWebBasePO<WebElement> {

    //local variables
    private static final String LOGIN_TITLE = "Login | MediaMarkt";

    //constructor
    public MediaMarktLoginPO() throws Exception {
        super();
        setTitle(LOGIN_TITLE);
    }

    //elements
    @FindBy(xpath = "//button[@data-test='pwa-consent-layer-deny-all']")
    protected M alleAblehnenButton;

    @FindBy(xpath = "//button[@data-test='pwa-consent-layer-accept-all']")
    protected M alleZulassenButton;
    @FindBy(id = "email")
    protected M username;

    @FindBy(id = "password")
    protected M password;

    @FindBy(id = "mms-login-form__login-button")
    protected M einloggenButton;

    @FindBy(xpath = "//p[contains(text(),'Bitte gib dein Passwort ein.') or contains(text(),'Bitte gib eine gültige E-Mail-Adresse ein.')]")
    protected M error;


    //abstract methods
    @Override
    protected void setTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public String getTitle() {
        return this.pageTitle;
    }

    //common methods
    /**
     * denyCookies method to deny all cookies
     */
    public void denyCookies() throws Exception {
        BrowserUtils.waitForClickable(alleAblehnenButton, Global_VARS.TIMEOUT_ELEMENT);
        alleAblehnenButton.click();
    }

    /**
     * acceptCookies method to deny all cookies
     */
    public void acceptCookies() throws Exception {
        BrowserUtils.waitForClickable(alleZulassenButton, Global_VARS.TIMEOUT_ELEMENT);
        alleZulassenButton.click();
    }

    /**
     * login - method to login to app with error handling
     *
     * @param username
     * @param password
     * @throws Exception
     */
    public void login(String username, String password) throws Exception {

        BrowserUtils.waitForTitle(getTitle(), Global_VARS.TIMEOUT_ELEMENT);

        if (!this.username.getAttribute("value").equals("")) {
            this.username.clear();
        }
        this.username.sendKeys(username);

        if (!this.password.getAttribute("value").equals("")) {
            this.password.clear();
        }
        this.password.sendKeys(password);
        einloggenButton.click();

        //exception handling
        if (BrowserUtils.elementExists(error, Global_VARS.TIMEOUT_SECOND)) {
            String getError = error.getText();
            throw new Exception("Login Failed with error = " + getError);
        }

        BrowserUtils.waitForTitle(new MediaMarktHomePO<WebElement>().getTitle(), Global_VARS.TIMEOUT_ELEMENT);


    }

}
