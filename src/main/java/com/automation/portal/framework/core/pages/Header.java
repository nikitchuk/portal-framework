package com.automation.portal.framework.core.pages;

import com.automation.portal.framework.core.Page;
import com.automation.portal.framework.properties.TestData;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

public class Header extends Page {

    @FindBy(how = How.CSS, using = "header a[href*=signin]")
    private WebElement signIn;

    @FindBy(how = How.CSS, using = "header")
    private WebElement header;

    @FindBy(how = How.CSS, using = "#header_search_area")
    private WebElement search;

    @FindBy(how = How.CSS, using = "header a[role=button]:first-child")
    private WebElement userMenu;

    @FindBy(how = How.CSS, using = "header a[href*=lgout]")
    private WebElement logout;

    @FindBy(how = How.CSS, using = "header input[type=text]")
    private WebElement searchInput;

    @FindBy(how = How.CSS, using = "header input[type=submit]")
    private WebElement searchButton;

    @FindBy(how = How.CSS, using = "[id*=cart-n]")
    private List<WebElement> amountInBasket;
    @FindBy(how = How.CSS, using = "[id*=cart-i]")
    private WebElement basketIcon;


    private static final Logger logger = Logger.getLogger(Header.class.getName());


    @Step
    public boolean isHeaderIsDisplayed() {
        return isPageDisplayed(header);
    }

    @Step
    public boolean isBasketEmpty() {
        return amountInBasket.size() > 0;
    }

    @Step
    public Header clickBasket() {
        waitUntilClickable(this.basketIcon).click();
        logger.info("Go to Basket");
        return this;
    }

    @Step
    public Header openLoginForm() {
        waitUntilClickable(this.signIn).click();
        logger.info("Sign in button submitted");
        return this;
    }

    @Step
    public Header searchSubmit() {
        waitUntilClickable(this.searchButton).click();
        logger.info("Search button submitted");
        return this;
    }

    @Step
    public Header openUserMenu() {
        waitUntilClickable(this.userMenu).click();
        logger.info("Click on user menu ");
        return this;
    }

    @Step
    public Header setSearch(TestData searchRequest) {
        writeDataIntoFormField(this.searchInput, searchRequest.get());
        logger.info("Search field set with " + searchRequest.get());
        return this;
    }

    @Step
    public String getSearch() {
        String searchdVal = searchInput.getAttribute("value");
        logger.info("Search field equals " + searchdVal);
        return searchdVal;
    }

    @Step
    public Header submitLogout() {
        waitUntilClickable(this.logout).click();
        return this;
    }

    @Step
    public Boolean isLogoutPresent() {
        Boolean isLogoutPresent = isVisible(logout);
        logger.info("Logout is present " + isLogoutPresent);
        return isLogoutPresent;
    }
}
