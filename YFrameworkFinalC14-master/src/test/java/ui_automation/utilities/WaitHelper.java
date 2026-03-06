package ui_automation.utilities;

import com.google.common.base.Function;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitHelper {

    private static final Logger LOGGER = LogManager.getLogger(WaitHelper.class);

    public static void shortWait() {
        LOGGER.info("Short wait (5 seconds) initiated.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error("An exception occurred during shortWait", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void mediumWait() {
        LOGGER.info("Medium wait (15 seconds) initiated.");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            LOGGER.error("An exception occurred during mediumWait", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void longWait() {
        LOGGER.info("Long wait (30 seconds) initiated.");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            LOGGER.error("An exception occurred during longWait", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void wait(int secs) {
        LOGGER.info("Waiting for " + secs + " seconds.");
        try {
            Thread.sleep(1000L * secs);
        } catch (InterruptedException e) {
            LOGGER.error("An exception occurred during wait", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void waitForVisibility(WebElement element, int timeToWaitInSec) {
        LOGGER.info("Waiting for visibility of element for " + timeToWaitInSec + " seconds.");
        WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeToWaitInSec);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            LOGGER.error("An exception occurred while waiting for element visibility: " + e.getMessage(), e);
            throw e;
        }
    }

    public static void waitForVisibility(List<WebElement> elements, int timeToWaitInSec) {
        LOGGER.info("Waiting up to " + timeToWaitInSec + " seconds for visibility of all elements.");
        WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeToWaitInSec);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            LOGGER.info("All elements are now visible.");
        } catch (Exception e) {
            LOGGER.error("Not all elements were visible after " + timeToWaitInSec + " seconds", e);
        }
    }

    public static void waitForVisibility(int timeToWaitInSec, WebElement... elements) {
        LOGGER.info("Waiting up to " + timeToWaitInSec + " seconds for visibility of all elements.");
        WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeToWaitInSec);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            LOGGER.info("All elements are now visible.");
        } catch (Exception e) {
            LOGGER.error("Not all elements were visible after " + timeToWaitInSec + " seconds", e);
        }
    }


    public static void waitForClickablility(WebElement element, int timeToWaitInSec) {
        LOGGER.info("Waiting up to " + timeToWaitInSec + " seconds for element to be clickable.");
        WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeToWaitInSec);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            LOGGER.info("Element is now clickable.");
        } catch (Exception e) {
            LOGGER.error("Element was not clickable after " + timeToWaitInSec + " seconds", e);
        }
    }

    public static void waitForTextToBePresentInElement(WebElement element, String text, int timeToWaitInSec) {
        LOGGER.info("Waiting for text to be present in element for " + timeToWaitInSec + " seconds. Text: " + text);
        WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeToWaitInSec);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            LOGGER.error("An exception occurred while waiting for text to be present in element: " + e.getMessage(), e);
            throw e;
        }
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        LOGGER.info("Waiting for page to load for " + timeOutInSeconds + " seconds.");
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getInstance().getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            LOGGER.error("Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds", error);
        }
    }

    public static WebElement fluentWait(final WebElement webElement, int timeinsec) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(timeinsec))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    public WebElement handleStaleElement(By locator, int retryCount, int delayInSeconds) throws InterruptedException {
        LOGGER.info("Handling stale element for locator: " + locator);
        WebElement element = null;

        while (retryCount >= 0) {
            try {
                element = Driver.getInstance().getDriver().findElement(locator);
                return element;
            } catch (StaleElementReferenceException e) {
                LOGGER.warn("StaleElementReferenceException caught, retrying. Retries left: " + retryCount, e);
                wait(1); // Converting seconds to milliseconds
                retryCount--;
            }
        }
        LOGGER.error("Element with locator: " + locator + " could not be recovered after retrying.");
        throw new StaleElementReferenceException("Element cannot be recovered");
    }


}
