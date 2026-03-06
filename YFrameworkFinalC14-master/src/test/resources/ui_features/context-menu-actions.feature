@regression
Feature: Use actions to interact with context menu

  @context
  Scenario Outline: Context click show alert with a message
    Given user navigates to jquery application
    When user right clicks on the right click me button
    And selects "<Option>" option from the context menu
    Then user can see an alert with content as "<Text>"
    And can accept the alert
    Examples:
      | Option | Text   |
      | Edit   | edit   |
      | Cut    | cut    |
      | Copy   | copy   |
      | Paste  | paste  |
      | Delete | delete |
      | Quit   | quit   |