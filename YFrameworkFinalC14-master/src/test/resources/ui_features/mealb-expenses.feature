@regression @dbtest
Feature: MealB Expenses

  Background:
    Given user navigates to MealB landing Page
    And clicks on Sign In button
    And logs in with employee account

  Scenario: Add Expense
    Given user is on the Dashboard page
    When user clicks on the Expenses tab
    And clicks on the Add Expense button
    And selects "Other" as the expense type
    Then a modal pop-up is shown as a form
    When user enters todays date as the expense date
    And enters "9990" as the expense amount
    And selects "Other" as expense name
    And enters "Bahamas Trip Very Cool" as other expense name
    And the user clicks the Save button
    And the expense "Bahamas Trip Very Cool" with amount "9990.00" is listed on the Expenses page
    And a record for "Bahamas Trip Very Cool" with amount "9990.00" is created in the database
