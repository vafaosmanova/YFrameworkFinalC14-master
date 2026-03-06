@regression
Feature: User Management Feature

  @run
  Scenario: Tab navigation with actions class
    Given user navigates to hrm login page
    When user logs in with username "yoll-student" and password "Bootcamp5#"
    And hovers over the "Admin" tab
    And hovers over the User Management sub tab
    And hovers over the Users sub tab
    And clicks on Users
    Then user can see page header "System Users"