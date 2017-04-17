package com.portals;

import com.automation.portal.framework.InitDriver;
import com.automation.portal.framework.properties.TestProps;
import com.automation.portal.framework.utilz.ScreenCapture;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());

    protected static ScreenCapture screenCapture = new ScreenCapture();

    @BeforeMethod(alwaysRun = true)
    public void setBaseUrl(ITestContext context) {
        TestProps.BROWSER_TYPE.set(context.getCurrentXmlTest().getParameter("browser"));
        TestProps.URL.set(context.getCurrentXmlTest().getParameter("url"));
        TestProps.OS.set(context.getCurrentXmlTest().getParameter("os"));
        logger.info("Setting test testUrl: " + context.getCurrentXmlTest().getParameter("url"));
        new InitDriver().maximizeWindow();
    }

    @AfterMethod(alwaysRun = true)
    public void endSession() {
        InitDriver.closeDriver();
//        InitDriver.quitDriver();
    }

    @AfterClass()
    public void TearDown() throws Exception {
        InitDriver.quitDriver();
    }


}
