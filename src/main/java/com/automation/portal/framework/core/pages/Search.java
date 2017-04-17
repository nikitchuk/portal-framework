package com.automation.portal.framework.core.pages;

import com.automation.portal.framework.core.Page;
import com.automation.portal.framework.properties.TestData;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

public class Search extends Page {
    @FindBy(how = How.CSS, using = "#Results")
    private WebElement searchPage;

    @FindBy(how = How.CSS, using = ".kwcat>b")
    private WebElement searchRequestResultFor;

    @FindBy(how = How.CSS, using = "#ConstraintCaptionContainer b")
    private WebElement constraintCaptionContainer;

    @FindBy(how = How.CSS, using = ".sresult")
    private List<WebElement> searchResultList;

    @FindBy(how = How.CSS, using = "a[href*=Model]")
    private List<WebElement> compatibleModel;


    private static final Logger logger = Logger.getLogger(Search.class.getName());

    @Step
    public boolean isSearchPageDisplayed() {
        return isPageDisplayed(searchPage);
    }

    @Step
    public String getSearchRequestFor() {
        String searchResultFor = searchRequestResultFor.getText();
        logger.info("Search result display for " + searchResultFor);
        return searchResultFor;
    }

    @Step
    public String getConstraintCaptionContainer() {
        String constraintCaptionContainerVal = constraintCaptionContainer.getAttribute("data-name");
        logger.info("Constraint Caption Container  " + constraintCaptionContainerVal);
        return constraintCaptionContainerVal;
    }

    @Step
    public Search clickItemNumber(TestData number) {
        for (WebElement element : getList(searchResultList)) {
            if (element.getAttribute("r").equals(number.get())) {
                WebElement itemLink = element.findElement(By.cssSelector(".vip"));
                itemLink.click();
                logger.info("Opening item " + number.get());
                break;
            }
        }
        return this;
    }

    @Step
    public Search clickCompatibleModel(TestData model) {
        String ebayText = "For " + model.get();
        for (WebElement element : getList(compatibleModel)) {
            if (element.getText().split(" \\(")[0].equalsIgnoreCase(ebayText)) {
                element.click();
                logger.info("Compatible Model select " + model.get());
                break;
            }
        }
        return this;
    }

}
