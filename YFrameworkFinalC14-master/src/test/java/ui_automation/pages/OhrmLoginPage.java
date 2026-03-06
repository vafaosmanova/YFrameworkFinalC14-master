package ui_automation.pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

public class OhrmLoginPage {
    private static final Logger LOGGER = LogManager.getLogger(OhrmLoginPage.class);

    public OhrmLoginPage() {
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
    }

    @FindBy(id = "txtUsername")
    public WebElement usernameInputBox;
    @FindBy(id = "txtPassword")
    public WebElement passwordInputBox;
    @FindBy(id = "btnLogin")
    public WebElement loginButton;
    @FindBy(id = "spanMessage")
    public WebElement errorMessage;

    public void login(String username, String password) {
        try {
            LOGGER.info("Attempting to log in with username: " + username);
            WaitHelper.waitForVisibility(usernameInputBox, 10);
            usernameInputBox.sendKeys(username);
            LOGGER.debug("Username entered in the input box.");
            WaitHelper.waitForVisibility(passwordInputBox, 10);
            passwordInputBox.sendKeys(password);
            LOGGER.debug("Password entered in the input box.");
            WaitHelper.waitForClickablility(loginButton, 10);
            loginButton.click();
            LOGGER.info("Login button clicked.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while trying to log in.", e);
            throw e;
        }
    }

}
