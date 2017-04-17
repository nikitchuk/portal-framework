package com.portals.portal.desktop;

import com.automation.portal.framework.core.Page;
import com.automation.portal.framework.core.pages.*;
import com.automation.portal.framework.properties.Credentials;
import com.automation.portal.framework.properties.TestData;
import com.portals.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;


public class E2ETest extends BaseTest {

    @Test
    public void clearBasket () throws Exception {
        Login login = Page.init(Login.class);
        Main main = Page.init(Main.class);
        Header header = Page.init(Header.class);
        Basket basket = Page.init(Basket.class);

        Assert.assertEquals(login.getCurrentUrl(), "http://www.ebay.com/", "Incorrect url is opened or empty");

        header.openLoginForm();
        Assert.assertTrue(login.isLoginPageDisplayed(), "Login page is not displayed");

        login.setLogin(Credentials.EMAIL);
        Assert.assertEquals(login.getLogin(), Credentials.EMAIL.get(), "Incorrect email enter to Email Field or field empty");
        login.setPassword(Credentials.PASSWORD);
        Assert.assertEquals(login.getPassword(), Credentials.PASSWORD.get(), "Incorrect password enter to Password Field or field empty");
        login.submitLogin();

        Assert.assertTrue(main.isMainPageDisplayed(), "Main page is not displayed");
        Assert.assertTrue(header.isHeaderIsDisplayed(), "Header is not displayed");
        header.openUserMenu();
        Assert.assertTrue(header.isLogoutPresent(), "User isn't login, logout is not present ");
        if (header.isBasketEmpty()) {
            header.clickBasket();
            Assert.assertTrue(basket.isBasketPageDisplayed(), "Basket is not displayed");
            basket.deleteItemFromBasket();
        }
    }

    @Test(description = "E2E")
    @Features("E2E")
    @Stories("User should be able done all steps from E2E case")
    public void ebayTest() throws Exception {
        Login login = Page.init(Login.class);
        Main main = Page.init(Main.class);
        Header header = Page.init(Header.class);
        Search search = Page.init(Search.class);
        Item item = Page.init(Item.class);
        Basket basket = Page.init(Basket.class);

        Assert.assertEquals(login.getCurrentUrl(), "http://www.ebay.com/", "Incorrect url is opened or empty");

        header.openLoginForm();
        Assert.assertTrue(login.isLoginPageDisplayed(), "Login page is not displayed");

        login.setLogin(Credentials.EMAIL);
        Assert.assertEquals(login.getLogin(), Credentials.EMAIL.get(), "Incorrect email enter to Email Field or field empty");
        login.setPassword(Credentials.PASSWORD);
        Assert.assertEquals(login.getPassword(), Credentials.PASSWORD.get(), "Incorrect password enter to Password Field or field empty");
        login.submitLogin();

        Assert.assertTrue(main.isMainPageDisplayed(), "Main page is not displayed");
        Assert.assertTrue(header.isHeaderIsDisplayed(), "Header is not displayed");
        header.openUserMenu();
        Assert.assertTrue(header.isLogoutPresent(), "User isn't login, logout is not present ");

        header.setSearch(TestData.SEARCH_REQUEST);
        Assert.assertEquals(header.getSearch(), TestData.SEARCH_REQUEST.get(), "Incorrect search request enter to Search Field or field empty");
        header.searchSubmit();

        Assert.assertEquals(search.getSearchRequestFor(), TestData.SEARCH_REQUEST.get(), "Correct search request is not displayed");
        search.clickCompatibleModel(TestData.SEARCH_MODEL);
        Assert.assertEquals(search.getConstraintCaptionContainer(), TestData.SEARCH_CONSTRAINT_CAPTION_CONTAINER.get(), "Constraint Caption Container is empty or has wrong data");
        search.clickItemNumber(TestData.NUMBER_ITEM);
        Assert.assertTrue(item.isItemDisplayed(), "Item page is not displayed");
        item.selectMandatoryData();
        Assert.assertTrue(item.isMandatoryDataSelected(), "Mandatory field is selected");
        item.submitAddToCard();
        Assert.assertTrue(basket.isBasketPageDisplayed(), "Basket page is not displayed");

        Assert.assertEquals(basket.getAmountInCard(), 1, "Item didn't displayed in card");
        header.openUserMenu()
                .submitLogout();
        header.openLoginForm();
        Assert.assertTrue(login.isLoginPageDisplayed(), "Login page is not displayed");
    }

}
