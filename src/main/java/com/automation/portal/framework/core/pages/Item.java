package com.automation.portal.framework.core.pages;

import com.automation.portal.framework.core.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;


public class Item extends Page {

    @FindBy(how = How.CSS, using = ".itemAttr")
    private WebElement itemPage;

    @FindBy(how = How.CSS, using = "#CenterPanel select")
    private List<WebElement> itemSelect;

    @FindBy(how = How.CSS, using = "#isCartBtn_btn")
    private WebElement addToCardButton;


    private static final Logger logger = Logger.getLogger(Item.class.getName());


    @Step
    public boolean isItemDisplayed() {
        return isPageDisplayed(itemPage);
    }

    @Step
    public void submitAddToCard() {
        waitUntilClickable(this.addToCardButton).click();
        logger.info("Add to Card button submitted");
    }

    @Step
    public Item selectMandatoryData() {
        for (WebElement element : getList(itemSelect)) {
            waitUntilClickable(element).click();
            List<WebElement> droplist = element.findElements(By.cssSelector("option:not([disabled=disabled]):not([value=\"-1\"])"));
            for (WebElement elementDroplist : droplist) {
                if (elementDroplist.isDisplayed()) {
                    waitUntilClickable(elementDroplist).click();
                    break;
                }
                logger.debug("Item selected");
            }
        }
        return this;
    }

    @Step
    public boolean isMandatoryDataSelected() {
        for (WebElement element : getList(itemSelect)) {
            if (element.getAttribute("value").equals("-1")) {
                logger.error("Mandatory field is not selected" + element.getAttribute("value"));
                return false;
            }
        }
        return true;
    }


}
