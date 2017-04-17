package com.automation.portal.framework.utilz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.concurrent.TimeUnit;

public class WaiterListener extends AbstractWebDriverEventListener {

    private final long timeout;

    public WaiterListener(long timeout, TimeUnit unit) {
        this.timeout = TimeUnit.MILLISECONDS.convert(Math.max(0, timeout), unit);

    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        sleep();
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        sleep();
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        sleep();
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        sleep();
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        sleep();
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        sleep();
        super.beforeNavigateRefresh(driver);
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        sleep();
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        sleep();
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        sleep();
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        sleep();
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        sleep();
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
