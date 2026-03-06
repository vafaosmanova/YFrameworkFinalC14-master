package ui_automation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui_automation.constants.Constants;
import ui_automation.pages.ContextMenuPage;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

public class ContextMenuSteps {

    private Alert alert = null;
    private ContextMenuPage contextMenuPage = new ContextMenuPage();
    private Actions actions = new Actions(Driver.getInstance().getDriver());

    @Given("user navigates to jquery application")
    public void user_navigates_to_jquery_application() {
        Driver.getInstance().getDriver().get(ConfigurationReader.getProperty(Constants.UI_CONFIG, "jquery.url"));
    }

    @When("user right clicks on the right click me button")
    public void user_right_clicks_on_the_right_click_me_button() {
        actions.contextClick(contextMenuPage.rightClickMeButton).perform();
        WaitHelper.wait(1);
    }

    @When("selects {string} option from the context menu")
    public void selects_option_from_the_context_menu(String optionName) {
        WebElement currentOption = contextMenuPage.getContextOptionByName(optionName);
        actions.click(currentOption).perform();
        WaitHelper.wait(3);
    }

    @Then("user can see an alert with content as {string}")
    public void user_can_see_an_alert_with_content_as(String expectedText) {
        alert = Driver.getInstance().getDriver().switchTo().alert();
        String alertContent = alert.getText();
        Assert.assertTrue("The content verification failed!", alertContent.endsWith(expectedText));
        WaitHelper.wait(3);
    }

    @Then("can accept the alert")
    public void can_accept_the_alert() {
        alert.accept();
        WaitHelper.wait(3);
    }




}
