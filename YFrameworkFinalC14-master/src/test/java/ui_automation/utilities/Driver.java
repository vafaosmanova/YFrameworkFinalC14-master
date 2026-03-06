package ui_automation.utilities;

import org.openqa.selenium.WebDriver;

public class Driver {

    private Driver() {
    }

    private static final Driver driverInstance = new Driver();

    public static Driver getInstance() {
        return driverInstance;
    }

    ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver() {
        WebDriver driver = threadLocalDriver.get();
        return driver;
    }

    public void setDriver(WebDriver driverParameter) {
        threadLocalDriver.set(driverParameter);
    }

    public void removeDriver() {
        WebDriver driver = threadLocalDriver.get();
        driver.quit();
        threadLocalDriver.remove();
    }
}