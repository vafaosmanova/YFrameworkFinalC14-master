package ui_automation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import ui_automation.constants.Constants;
import ui_automation.pages.*;
import ui_automation.utilities.ConfigurationReader;
import ui_automation.utilities.DBUtility;
import ui_automation.utilities.Driver;
import ui_automation.utilities.WaitHelper;

import java.util.List;
import java.util.Map;

public class MealBExpenseCommonSteps {
    private static final Logger LOGGER = LogManager.getLogger(MealBExpenseCommonSteps.class);

    private MealBLandingPage mealBLandingPage = new MealBLandingPage();
    private MealBLoginPage mealBLoginPage = new MealBLoginPage();
    private MealBDashboardPage mealBDashboardPage = new MealBDashboardPage();
    private MealBExpensesPage mealBExpensesPage = new MealBExpensesPage();
    private MealBCreateExpenseFormPage mealBCreateExpenseFormPage = new MealBCreateExpenseFormPage();

    @Given("user navigates to MealB landing Page")
    public void user_navigates_to_MealB_landing_Page() {
        Driver.getInstance().getDriver().get(ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_URL));
        LOGGER.info("Navigated to url: " + Driver.getInstance().getDriver().getCurrentUrl());
        WaitHelper.waitForPageToLoad(20);
    }

    @Given("clicks on Sign In button")
    public void clicks_on_Sign_In_button() {
        LOGGER.info("Clicking on Sign In Button ");
        WaitHelper.waitForClickablility(mealBLandingPage.signInButton, 10);
        mealBLandingPage.signInButton.click();
    }

    @Given("logs in with employee account")
    public void logs_in_with_employee_account() {
        LOGGER.info("Sign In attempt with username: " + ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_USERNAME));
        mealBLoginPage.login(
                ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_USERNAME),
                ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.MEALB_PASSWORD)
        );

    }

    @Given("user is on the Dashboard page")
    public void user_is_on_the_Dashboard_page() {
        WaitHelper.waitForClickablility(mealBDashboardPage.expensesTab, 20);
        String currentUrl = Driver.getInstance().getDriver().getCurrentUrl();
        LOGGER.info("Navigated to Dashboard with url: " + currentUrl);
        Assert.assertTrue("Dashboard Verification Failed!", currentUrl.endsWith(Constants.MEALB_DASHBOARD_ENDPOINT));
    }

    @When("user clicks on the Expenses tab")
    public void user_clicks_on_the_Expenses_tab() {
        LOGGER.info("Clicking Expense Tab.");
        WaitHelper.waitForClickablility(mealBDashboardPage.expensesTab, 10);
        mealBDashboardPage.expensesTab.click();
        WaitHelper.waitForPageToLoad(10);
    }

    @When("clicks on the Add Expense button")
    public void clicks_on_the_Add_Expense_button() {
        LOGGER.info("Clicking Expense Dropdown.");
        WaitHelper.waitForClickablility(mealBExpensesPage.addExpenseDropdown, 10);
        mealBExpensesPage.addExpenseDropdown.click();
    }

    @When("selects {string} as the expense type")
    public void selects_as_the_expense_type(String expenseType) {
        LOGGER.info("Provided expense type: " + expenseType);
        if (expenseType.equals("Other")) {
            LOGGER.info("Choosing " + expenseType + " from dropdown.");
            WaitHelper.waitForClickablility(mealBExpensesPage.otherExpenseOption, 10);
            mealBExpensesPage.otherExpenseOption.click();
        }
    }

    @Then("a modal pop-up is shown as a form")
    public void a_modal_pop_up_is_shown_as_a_form() {
        WaitHelper.waitForVisibility(mealBCreateExpenseFormPage.formModalHeader, 10);
        boolean formIsDisplayed = mealBCreateExpenseFormPage.formModalHeader.isDisplayed();
        LOGGER.info("Modal with form is displayed (" + formIsDisplayed + ")");
        Assert.assertTrue("Form visibility verification failed!", formIsDisplayed);
    }

    @When("user enters todays date as the expense date")
    public void user_enters_todays_date_as_the_expense_date() {
        LOGGER.info("Choosing today's date.");
        WaitHelper.waitForClickablility(mealBCreateExpenseFormPage.expenseDateInputBox, 10);
        mealBCreateExpenseFormPage.expenseDateInputBox.click();
        WaitHelper.waitForClickablility(mealBCreateExpenseFormPage.todaysDateButton, 10);
        mealBCreateExpenseFormPage.todaysDateButton.click();
    }

    @When("enters {string} as the expense amount")
    public void enters_as_the_expense_amount(String amount) {
        LOGGER.info("Entering amount " + amount);
        WaitHelper.waitForVisibility(mealBCreateExpenseFormPage.expenseAmountInputBox, 10);
        mealBCreateExpenseFormPage.expenseAmountInputBox.sendKeys(amount);
    }

    @When("selects {string} as expense name")
    public void selects_as_expense_name(String expenseName) {
        LOGGER.info("Entering expense name " + expenseName);
        WaitHelper.waitForClickablility(mealBCreateExpenseFormPage.expenseNameDropdown, 12);
        mealBCreateExpenseFormPage.expenseNameDropdown.click();
        WaitHelper.waitForVisibility(mealBCreateExpenseFormPage.otherExpenseNameOption, 20);
        mealBCreateExpenseFormPage.otherExpenseNameOption.click();
    }

    @When("enters {string} as other expense name")
    public void enters_as_other_expense_name(String expenseName) {
        LOGGER.info("Entering other expense name " + expenseName);
        WaitHelper.waitForVisibility(mealBCreateExpenseFormPage.otherExpenseNameInputBox, 10);
        mealBCreateExpenseFormPage.otherExpenseNameInputBox.sendKeys(expenseName);
    }

    @When("the user clicks the Save button")
    public void the_user_clicks_the_Save_button() {
        WaitHelper.waitForClickablility(mealBCreateExpenseFormPage.saveButton, 10);
        mealBCreateExpenseFormPage.saveButton.click();
    }

    @When("the expense {string} with amount {string} is listed on the Expenses page")
    public void the_expense_with_amount_is_listed_on_the_Expenses_page(String string, String string2) {
        //TODO - Implement the logic to verify if the expense is present in table with data created previously
    }

    @When("a record for {string} with amount {string} is created in the database")
    public void a_record_for_with_amount_is_created_in_the_database(String expectedExpenseName, String expectedExpenseAmount) {
        LOGGER.info("Starting a BD connection with these parameters: ");
        LOGGER.info("DB URL: " + Constants.MEALB_DB_URL);
        LOGGER.info("DB Driver: " + Constants.MEALB_DB_DRIVER);
        LOGGER.info("DB Username: " + Constants.MEALB_DB_USERNAME);
        LOGGER.info("DB Password: " + Constants.MEALB_PASSWORD);
        DBUtility.openConnection();

        // If you have only one record back you can do direct verification
        String query = "SELECT * FROM Expenses\n" +
                "WHERE CreatorUserId = (SELECT Id FROM AbpUsers WHERE UserName = 'user_test')\n" +
                "AND Name = '" + expectedExpenseName + "'\n" +
                "AND Amount = " + expectedExpenseAmount + "\n" +
                "AND DeletionTime IS NULL";

        LOGGER.info("--------- CODE BLOCK ----------");
        LOGGER.info("Query to be executed: \n" + query);
        LOGGER.info("--------- CODE BLOCK END----------");

        List<Map<String, Object>> records = DBUtility.executeSQLQuery(query);
        LOGGER.info("Results from DB: \n" + records);
        Assert.assertFalse("Expense DB record verification failed", records.isEmpty());

        Map<String, Object> record = records.get(0);
        String actualExpenseName = record.get("Name").toString();
        String actualExpenseAmount = record.get("Amount").toString();
        Assert.assertEquals(expectedExpenseName, actualExpenseName);
        Assert.assertEquals(expectedExpenseAmount, actualExpenseAmount);
        LOGGER.info("Finishing up the test...");

        //        String query = "SELECT * FROM Expenses\n" +
//                "WHERE CreatorUserId = (SELECT Id FROM AbpUsers WHERE UserName = 'user_test')\n";
//        // If there are more records getting back to you
//        // You can iterate over the list and do any logic required
//        List<Map<String, Object>> records = DBUtility.executeSQLQuery(query);
//
//        // Count first the amount of "Bahamas trip" records it should be 2
//        // Do the verification on the one that is not delelted
//       for(Map<String, Object> record : records){
//           String expenseName = record.get("Name").toString();
//           String expenseAmount = record.get("Amount").toString();
//           if(expenseName.equals(expectedExpenseName) && record.get("IsDeleted").toString().equals("false")){
//                // do some logic
//              Assert.assertEquals(expectedExpenseAmount, expenseAmount);
//           }
//       }


    }

}
