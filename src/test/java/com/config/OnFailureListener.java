package com.config;

import com.automation.portal.framework.InitDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.yandex.qatools.allure.annotations.Attachment;


public class OnFailureListener extends TestListenerAdapter {

    @Attachment(value = "error attachment", type = "image/png")
    private byte[] screenshot() {
        return ((TakesScreenshot) InitDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        screenshot();
    }

}
