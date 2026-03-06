package ui_automation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import ui_automation.constants.Constants;
import ui_automation.pages.OhrmLoginPage;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

public class LoginSteps {

    private OhrmLoginPage ohrmLoginPage = new OhrmLoginPage();

    @Given("user navigates to hrm login page")
    public void user_navigates_to_hrm_login_page() {
        String url = ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.OHRM_URL);
        Driver.getInstance().getDriver().get(url);
    }

    @When("user clicks login button")
    public void user_clicks_login_button() {
        ohrmLoginPage.loginButton.click();
    }

    @When("user logs in with username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
            ohrmLoginPage.login(username, password);
    }

    @Then("user can see error message {string}")
    public void user_can_see_error_message(String expectedErrorMessage) {
        String actualErrorMessage = ohrmLoginPage.errorMessage.getText();
        Assert.assertEquals("Error message verification failed", expectedErrorMessage, actualErrorMessage);
    }

}
