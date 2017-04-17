package com.automation.portal.framework.utilz;

import com.automation.portal.framework.InitDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.cropper.DefaultCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


public class ScreenCapture {

    private static final Logger logger = Logger.getLogger(ScreenCapture.class.getName());

    public void takeScreenshot() {
        try {
            _screenshot();
            logger.debug("Taking screenshot of page");
        } catch (Exception e) {
            logger.error("Unable to take screenshot. " + e.getMessage());
        }
    }

    public void takeScreenshot(By by) {
        try {
            _screenshot(by);
            logger.debug("Taking screenshot of element");
        } catch (Exception e) {
            logger.error("Unable to take screenshot. " + e.getMessage());
        }
    }

    public void takeErrorScreenshot() {
        try {
            _error();
            logger.debug("Taking screenshot of page");
        } catch (Exception e) {
            logger.error("Unable to take screenshot. " + e.getMessage());
        }
    }

    @Attachment(type = "image/png")
    private static byte[] _error() throws Exception {
        return ((TakesScreenshot) InitDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(type = "image/png")
    private static byte[] _screenshot() throws Exception {
        final Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500)).takeScreenshot(InitDriver.getDriver());
        final BufferedImage image = screenshot.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    @Attachment(type = "image/png")
    private static byte[] _screenshot(By by) throws Exception {
        final Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500)).imageCropper(new DefaultCropper()).takeScreenshot(InitDriver.getDriver(), InitDriver.getDriver().findElement(by));
        final BufferedImage image = screenshot.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

}
