@regression
Feature: Display of multiple actions with Actions class

  @multiple-actions
  Scenario: Login with valid credentials with actions class
    Given user navigates to hrm login page
    When user logs in with username "yoll-student" and password "Bootcamp5#" with actions class
    Then user is redirected to home page