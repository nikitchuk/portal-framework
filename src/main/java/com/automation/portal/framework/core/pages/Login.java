package com.automation.portal.framework.core.pages;

import com.automation.portal.framework.core.Page;
import com.automation.portal.framework.properties.Credentials;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;


public class Login extends Page {

    @FindBy(how = How.CSS, using = "#pri_signin  input[type=text]")
    private WebElement login;

    @FindBy(how = How.CSS, using = "#pri_signin  input[type=password]")
    private WebElement password;

    @FindBy(how = How.CSS, using = "#SignInForm [type=submit]")
    private WebElement submitLogin;

    @FindBy(how = How.CSS, using = "#SignInForm")
    private WebElement signInForm;


    private static final Logger logger = Logger.getLogger(Login.class.getName());


    @Step
    public Login setLogin(Credentials email) {
        writeDataIntoFormField(this.login, email.get());
        logger.info("Email field set with " + email.get());
        return this;
    }

    @Step
    public Login setPassword(Credentials password) {
        writeDataIntoFormField(this.password, password.get());
        logger.info("Password field set with " + password.get());
        return this;
    }

    @Step
    public void submitLogin() {
        waitUntilClickable(this.submitLogin).click();
        logger.info("Login button submitted");
    }

    @Step
    public boolean isLoginPageDisplayed() {
        return isPageDisplayed(signInForm);
    }

    @Step
    public String getLogin() {
        String loginVal = login.getAttribute("value");
        logger.info("Email equals " + loginVal);
        return loginVal;
    }

    @Step
    public String getPassword() {
        String passwordVal = password.getAttribute("value");
        logger.info("Password equals " + passwordVal);
        return passwordVal;
    }

}
