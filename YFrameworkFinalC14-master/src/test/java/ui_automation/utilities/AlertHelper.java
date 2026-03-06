package ui_automation.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

public class AlertHelper {

    private static final Logger LOGGER = LogManager.getLogger(AlertHelper.class);

    public AlertHelper() {
        LOGGER.debug("AlertHelper : " + Driver.getInstance().getDriver().hashCode());
    }

    public Alert getAlert() {
        LOGGER.debug("");
        return Driver.getInstance().getDriver().switchTo().alert();
    }

    public void acceptAlert() {
        LOGGER.info("");
        getAlert().accept();
    }

    public void dismissAlert() {
        LOGGER.info("");
        getAlert().dismiss();
    }

    public String getAlertText() {
        String text = getAlert().getText();
        LOGGER.info(text);
        return text;
    }

    public boolean isAlertPresent() {
        try {
            Driver.getInstance().getDriver().switchTo().alert();
            LOGGER.info("true");
            return true;
        } catch (NoAlertPresentException e) {
            // Ignore
            LOGGER.info("false");
            return false;
        }
    }

    public void acceptAlertIfPresent() {
        if (!isAlertPresent())
            return;
        acceptAlert();
        LOGGER.info("");
    }

    public void dismissAlertIfPresent() {

        if (!isAlertPresent())
            return;
        dismissAlert();
        LOGGER.info("");
    }

    public void acceptPrompt(String text) {

        if (!isAlertPresent())
            return;

        Alert alert = getAlert();
        alert.sendKeys(text);
        alert.accept();
        LOGGER.info(text);
    }

}
