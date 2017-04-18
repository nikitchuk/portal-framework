package com.automation.portal.framework;

import com.automation.portal.framework.properties.TestProps;
import com.automation.portal.framework.utilz.WaiterListener;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

public class InitDriver {

    private static final Logger logger = Logger.getLogger(InitDriver.class.getName());

    private static WebDriver driver;

    public InitDriver() {
        if (TestProps.BROWSER_TYPE.get().length() != 0 && TestProps.URL.get().length() != 0 && TestProps.OS.get().length() != 0) {
            if (TestProps.BROWSER_TYPE.get().equalsIgnoreCase("chrome")) {
                chromeDriver();
            } else if (TestProps.BROWSER_TYPE.get().equalsIgnoreCase("firefox")) {
                fireFoxDriver();
            } else if (TestProps.BROWSER_TYPE.get().equalsIgnoreCase("phantomjs")) {
                phantomJSDriver();
            }
            testUrl(TestProps.URL.get());
        }
    }

    private void setDriver(WebDriver driver) {
        this.driver = initListener(driver);
        if (TestProps.URL.get() != null) {
            testUrl(TestProps.URL.get());
        }
        logger.info("WebDriver is set");
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            logger.error("WebDriver not initiated.");
            throw new RuntimeException("WebDriver not initiated.");
        }
        return driver;
    }

    public InitDriver chromeDriver() {
        if (TestProps.OS.get().equalsIgnoreCase("mac")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/webdriver/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/webdriver/chromedriver.exe");
        }
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        logger.info("Binary path to the ChromeDriver is set");
        setDriver(new ChromeDriver(capabilities));
        logger.info("Setting ChromeDriver");
        return this;
    }

    //TODO add support firefox driver after update to selenium 3.0
    public InitDriver fireFoxDriver() {
        setDriver(new FirefoxDriver());
        logger.info("Setting FireFoxDriver");
        return this;
    }

    public InitDriver phantomJSDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{
                "--ignore-ssl-errors=true",
                "--webdriver-loglevel=NONE",
        });
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "src/main/resources/webdriver/phantomjs.exe");
        logger.info("Binary path to the PhantomJSDriver is set");
        setDriver(new PhantomJSDriver(capabilities));
        logger.info("Starting browser: PhantomJS");
        return this;
    }

    public InitDriver testUrl(String url) {
        getDriver().get(url);
        logger.info("Opening testUrl: " + url);
        return this;
    }

    public void customWindowSize(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
        logger.info("Window dimension set to width: " + width + ", height: " + height);
    }

    public void maximizeWindow() {
        getDriver().manage().window().maximize();
        logger.info("Window maximazed");
    }

    public static void closeDriver() {
        getDriver().close();
        logger.info("Closing driver");
    }

    public static void quitDriver() {
        getDriver().quit();
        driver = null;
        logger.info("Quiting driver");
    }

    private static WebDriver initListener(WebDriver d) {
        EventFiringWebDriver init = new EventFiringWebDriver(d);
        return init.register(new WaiterListener(100, TimeUnit.MILLISECONDS));
    }

}
