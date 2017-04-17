package com.automation.portal.framework.core.pages;

import com.automation.portal.framework.core.Page;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.yandex.qatools.allure.annotations.Step;

public class Main extends Page {
    @FindBy(how = How.CSS, using = ".main")
    private WebElement main;


    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Step
    public boolean isMainPageDisplayed() {
        return isPageDisplayed(main);
    }

}
