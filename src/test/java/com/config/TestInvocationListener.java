package com.config;

import org.testng.*;
import ru.yandex.qatools.allure.annotations.Attachment;

public class TestInvocationListener implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        Reporter.clear();
    }

    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            getTestLog();
        }
    }

    @Attachment(value = "log", type = "text/plain")
    private byte[] getTestLog(){
        String log = "";
        for(String s : Reporter.getOutput()){
            log += s;
        }
        return log.getBytes();
    }

}
