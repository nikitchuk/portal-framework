package com.automation.portal.framework.core.pages;


import com.automation.portal.framework.core.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

public class Basket extends Page {

    @FindBy(how = How.CSS, using = "#ShopCart")
    private WebElement basket;

    @FindBy(how = How.CSS, using = "[id*=sellerBucket]")
    private List<WebElement> amountOfItemInCard;

    @FindBy(how = How.CSS, using = "a[class*=actionLink]:first-child")
    private List<WebElement> removeItemList;


    private static final Logger logger = Logger.getLogger(Basket.class.getName());

    @Step
    public boolean isBasketPageDisplayed() {
        return isPageDisplayed(basket);
    }

    @Step
    public int getAmountInCard() {
        return getList(amountOfItemInCard).size();
    }

    @Step
    public Basket deleteItemFromBasket() {
        for (int i = getList(amountOfItemInCard).size() + 1; i > 1; i--) {
            basket.findElement(By.cssSelector("[id*=sellerBucket]:nth-child(" + i + ") a[class*=actionLink]:first-child")).click();
            logger.debug("Item remove from basket");
        }
        logger.info("All items deleted from basket");
        return this;
    }


}
