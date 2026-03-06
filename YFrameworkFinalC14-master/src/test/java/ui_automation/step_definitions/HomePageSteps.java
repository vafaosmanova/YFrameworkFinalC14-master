package ui_automation.step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui_automation.pages.OhrmHomePage;
import ui_automation.pages.OhrmLoginPage;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

import java.util.List;

public class HomePageSteps {

    private OhrmHomePage homePage = new OhrmHomePage();
    private Actions actions = new Actions(Driver.getInstance().getDriver());
    private OhrmLoginPage ohrmLoginPage = new OhrmLoginPage();


    @Then("user is redirected to home page")
    public void user_is_redirected_to_home_page() {
        String actualWelcomeMessage = homePage.welcomeMessage.getText();
        String expectedWelcomeMessage = "Welcome Yoll";
        Assert.assertEquals("Welcome message verification failed", expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("user can see following tabs:")
    public void user_can_see_following_tabs(List<String> eTabs) {
        List<String> expectedTabs = eTabs;
        System.out.println("expectedTabs size: " + expectedTabs.size());
        List<WebElement> actualTabs = homePage.tabs;
        System.out.println("actualTabs size: " + actualTabs.size());

        if (expectedTabs.size() != actualTabs.size()) {
            Assert.fail("The actual count of tabs is not matching expected count!");
        }

        for (int i = 0; i < actualTabs.size(); i++) {
            String expectedTab = expectedTabs.get(i);
            String actualTab = actualTabs.get(i).getText();
            Assert.assertEquals("Tab verification failed.", expectedTab, actualTab);
        }
    }


    @Then("user can see page header {string}")
    public void user_can_see_page_header(String expectedPageHeader) {
        String actualPageHeader = homePage.systemUsersPageHeader.getText();
        Assert.assertEquals("Page header verification failed!", expectedPageHeader, actualPageHeader);
    }


    @When("hovers over the {string} tab")
    public void hovers_over_the_tab(String tabName) {
        WebElement currentTab = homePage.getTabByName(tabName);
        // We have in our hands the element we want top hover over to
        // Now we need the actions class object
        actions.moveToElement(currentTab).perform();
        WaitHelper.wait(3);
    }

    @When("hovers over the User Management sub tab")
    public void hovers_over_the_User_Management_sub_tab() {
        // we have in our hands already the user management sub tab located
        actions.moveToElement(homePage.userManagementSubTab).perform();
        WaitHelper.wait(3);
    }

    @When("hovers over the Users sub tab")
    public void hovers_over_the_Users_sub_tab() {
        actions.moveToElement(homePage.usersSubTab).perform();
        WaitHelper.wait(3);
    }

    @When("clicks on Users")
    public void clicks_on_Users() {
        // let's also click on element by using actions class
        actions.click().perform();
        WaitHelper.wait(3);
    }

    @When("user logs in with username {string} and password {string} with actions class")
    public void user_logs_in_with_username_and_password_with_actions_class(String username, String password) {
        WaitHelper.wait(3);
        // when you chain multiple actions together it is not enough to just use perform()
        // we first need to build() and perform()
        // build() method will join all the actions in one single one and then perform
        actions.
                moveToElement(ohrmLoginPage.usernameInputBox).
                click().
                sendKeys(username).
                sendKeys(Keys.TAB).
                sendKeys(password).
                moveToElement(ohrmLoginPage.loginButton).
                click().
                build().perform();

        WaitHelper.wait(3);
    }


}
