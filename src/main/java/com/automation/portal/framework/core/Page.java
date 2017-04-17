package com.automation.portal.framework.core;

import com.automation.portal.framework.InitDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {

    private static final int DEFAULT_WAIT = 15;
    private static final int PAGE_LOAD_WAIT = 30;

    private static final Logger logger = Logger.getLogger(Page.class.getName());

    {
        if (!isPageReady(PAGE_LOAD_WAIT)) {
            logger.info("Page not loaded " + PAGE_LOAD_WAIT);
        }
    }

    @FindBy(css = "errors")
    private List<WebElement> errorTips;

    @FindBy(css = "message.error")
    private WebElement errorMessage;

    @FindBy(css = "message.success")
    private WebElement successMessage;

    @FindBy(css = "message")
    private WebElement message;

    public static <T extends Page> T init(Class<T> page) {
        return PageFactory.initElements(InitDriver.getDriver(), page);
    }

    public String getWindowTitle() {
        return InitDriver.getDriver().getTitle();
    }

    public String getCurrentUrl() {
        String currentUrl = InitDriver.getDriver().getCurrentUrl();
        logger.info("Current url " + currentUrl);
        return currentUrl;
    }

    public boolean isErrorTipsDisplayed() {
        return waitForAvailabilityOf(errorTips, 3).size() > 0;
    }

    public List<WebElement> getErrorTips() {
        return waitForAvailabilityOf(errorTips);
    }

    private List<String> getErrorTipsDescription() {
        List<String> list = new ArrayList<String>();
        for (WebElement element : getErrorTips()) {
            list.add(element.getText());
        }
        return list;
    }

    public boolean isErrorMessageDisplayed() {
        try {
            if (!isVisible(errorMessage)) {
                logger.info("Error message not displayed");
                return false;
            }
        } catch (Exception e) {
            logger.info("Error message is not displayed");
            return false;
        }

        logger.info("Error message is displayed");
        return true;
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            if (!isVisible(successMessage)) {
                logger.info("Success message is not displayed");
                return false;
            }
        } catch (Exception e) {
            logger.info("Success message is not displayed");
            return false;
        }
        logger.info("Success message is displayed");
        return true;
    }

    public boolean isPageReady(int timeout) {
        return new WebDriverWait(InitDriver.getDriver(), timeout).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) InitDriver.getDriver()).executeScript("return document.readyState").equals("complete");
            }
        });
    }

    public WebElement waitForAvailabilityOf(WebElement element) {
        return waitForAvailabilityOf(element, DEFAULT_WAIT);
    }

    public WebElement waitForAvailabilityOf(By by) {
        return new WebDriverWait(InitDriver.getDriver(), DEFAULT_WAIT).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForAvailabilityOf(final WebElement element, int timeout) {
        try {
            logger.debug("WebDriverWait set to " + timeout + " seconds");
            new WebDriverWait(InitDriver.getDriver(), timeout).ignoring(StaleElementReferenceException.class).until(
                    new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver driver) {
                            //return element.isDisplayed() ? element : null;
                            return (element.getSize().getHeight() > 0 && element.getSize().getWidth() > 0) ? element : null;
                        }
                    }
            );
            logger.debug("Element " + element.toString() + " is present in DOM and ready");
        } catch (Exception e) {
            logger.error("Element " + element.toString() + " is not visible in DOM at point: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return element;
    }

    public List<WebElement> waitForAvailabilityOf(List<WebElement> list) {
        return waitForAvailabilityOf(list, DEFAULT_WAIT);
    }

    public List<WebElement> waitForAvailabilityOf(final List<WebElement> list, int timeout) {
        try {
            logger.debug("WebDriverWait set to " + timeout + " seconds");
            new WebDriverWait(InitDriver.getDriver(), timeout).until(
                    new ExpectedCondition<List<WebElement>>() {
                        public List<WebElement> apply(WebDriver driver) {
                            return list.size() > 0 ? list : null;
                        }
                    }
            );
            logger.debug("List elements are ready");
        } catch (Exception e) {
            logger.error("Elements not visible: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return list;
    }

    public WebElement waitForPresenceOf(WebElement element) {
        return waitForPresenceOf(element, DEFAULT_WAIT);
    }

    public WebElement waitForPresenceOf(By by) {
        return waitForPresenceOf(InitDriver.getDriver().findElement(by), DEFAULT_WAIT);
    }

    public WebElement waitForPresenceOf(WebElement element, int timeout) {
        logger.debug("WebDriverWait set to " + timeout + " seconds");
        WebDriverWait wait = new WebDriverWait(InitDriver.getDriver(), timeout);
        return wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibilityOf(WebElement element) {
        waitForInvisibilityOf(element, DEFAULT_WAIT);
    }

    public void waitForInvisibilityOf(final WebElement element, int timeout) {
        try {
            logger.debug("WebDriverWait set to " + timeout + " seconds");
            new WebDriverWait(InitDriver.getDriver(), timeout).ignoring(StaleElementReferenceException.class).until(
                    new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            try {
                                return !element.isDisplayed();
                            } catch (NoSuchElementException e) {
                                return true;
                            } catch (StaleElementReferenceException e) {
                                return true;
                            }
                        }
                    }
            );
            logger.debug("Element " + element.toString() + " no longer in DOM");
        } catch (Exception e) {
            logger.debug("Something went wrong " + element.toString() + " no longer in DOM");
        }
    }

    public boolean isVisible(WebElement element) {
        return isVisible(element, DEFAULT_WAIT);
    }

    public boolean isVisible(By by) {
        boolean result = false;
        try {
            result = isVisible(InitDriver.getDriver().findElement(by), DEFAULT_WAIT);
        } catch (Exception e) {
            logger.debug("Element: " + by.toString() + " is not present in DOM");
        }
        return result;
    }

    public boolean isVisible(WebElement element, String text) {
        return isVisible(element, text, DEFAULT_WAIT);
    }

    public boolean isVisible(final WebElement element, int timeout) {
        logger.debug("WebDriverWait set to " + timeout + " seconds");
        return new WebDriverWait(InitDriver.getDriver(), timeout).ignoring(StaleElementReferenceException.class).until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        try {
                            if (element.isDisplayed()) {
                                logger.debug("Element " + element.toString() + " is in DOM");
                                return true;
                            }
                            logger.debug("Element: " + element.toString() + " is not present in DOM");
                            return false;
                        } catch (Exception e) {
                            logger.debug("Element: " + element.toString() + " is not present in DOM");
                            return false;
                        }
                    }
                }
        );
    }

    public boolean isVisible(WebElement element, String text, int timeout) {
        try {
            logger.debug("WebDriverWait set to " + timeout + " seconds");
            return new WebDriverWait(InitDriver.getDriver(), timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.error("Text " + text + " not found " + e.getMessage());
        }
        return false;
    }

    public void jsExecute(WebElement element, String js) {
        try {
            ((JavascriptExecutor) InitDriver.getDriver()).executeScript(js, element);
            logger.debug("JS code " + js + " successfully executed");
        } catch (Exception e) {
            logger.error("Error on JS code " + js + " execute");
            throw new RuntimeException(e);
        }
    }

    public void jsExecute(String js) {
        try {
            ((JavascriptExecutor) InitDriver.getDriver()).executeScript(js);
            logger.debug("JS code " + js + " successfully executed");
        } catch (Exception e) {
            logger.error("Error on JS code " + js + " execute");
            throw new RuntimeException(e);
        }
    }

    public void writeDataIntoFormField(WebElement element, String data) {
        waitForAvailabilityOf(element);
        logger.debug("Form field visible and ready for actions");
        if (element.getAttribute("value").length() > 0) {
            element.clear();
            logger.debug("Clearing form field");
        }
        element.sendKeys(data);
        logger.debug("Writing data to a form field");
    }

    public void writeDataIntoFormField(WebElement element, String data, int timeout) {
        waitForAvailabilityOf(element, timeout);
        logger.debug("Form field visible and ready for actions");
        InitDriver.getDriver().switchTo().activeElement();
        element.click();
        logger.debug("Clicking on a form field");
        element.clear();
        logger.debug("Clearing form field");
        element.sendKeys(data);
        logger.debug("Writing data to a form field");
    }

    public WebElement scrollToElement(WebElement element) {
        try {
            int x = element.getLocation().getY();
            logger.debug("Element x position: " + x);
            int y = element.getSize().getHeight();
            logger.debug("Element y position: " + y);
            new Actions(InitDriver.getDriver()).moveToElement(element, x, y).build().perform();
            logger.debug("Making waitForAvailabilityOf " + element.toString() + " visible for actions");
        } catch (MoveTargetOutOfBoundsException e) {
            jsExecute(element, "arguments[0].scrollIntoView(true);");
            logger.debug("Trying to scroll use JS to move to element");
        }
        return element;
    }

    public WebElement clickInvisible(WebElement element) {
        jsExecute(element, "arguments[0].click();");
        logger.debug("Trying to click use JS to element");
        return element;
    }


    public WebElement waitUntilClickable(WebElement element) {
        return waitUntilClickable(element, DEFAULT_WAIT);
    }

    public WebElement waitUntilClickable(WebElement element, int timeout) {
        return new WebDriverWait(InitDriver.getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilFrameLoads(WebElement element) {
        waitUntilFrameLoads(element, DEFAULT_WAIT);
    }

    public void waitUntilFrameLoads(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(InitDriver.getDriver(), timeout);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }

    public void clickCheckBox(WebElement element) {
        waitForAvailabilityOf(element).click();
        Boolean isChecked = element.getAttribute("checked").equals("true");
        logger.info("Click checkbox is " + isChecked);
    }

    public boolean isCheckBox(WebElement element) {
        Boolean isChecked = element.getAttribute("checked").equals("true");
        try {
            if (!isChecked) {
                logger.info("Checkbox is false");
                return false;
            }
        } catch (Exception e) {
            logger.info("Checkbox is false");
            return false;
        }
        logger.info("Checkbox is true");
        return true;
    }

    public boolean isPageDisplayed(WebElement element) {
        try {
            if (!isVisible(element)) {
                logger.info("Page is not displayed");
                return false;
            }
        } catch (Exception e) {
            logger.info("Page is not displayed");
            return false;
        }
        logger.info("Page is displayed");
        return true;
    }

    public List<WebElement> getList(List<WebElement> webElementList) {
        List<WebElement> list = waitForAvailabilityOf(webElementList);
        logger.debug("List grabbed");
        return list;
    }

}

