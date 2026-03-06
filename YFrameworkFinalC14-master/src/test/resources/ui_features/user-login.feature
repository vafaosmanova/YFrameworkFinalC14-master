@regression @login @smoke
Feature: User Login Process

  Background: Preliminary Steps
    Given user navigates to hrm login page

  @loginValid
  Scenario: Login with valid credentials
    When user logs in with username "yoll-student" and password "Bootcamp5#"
    Then user is redirected to home page

  @loginInvalid
  Scenario Outline: Login with invalid credentials <test_point>
    When user logs in with username "<username>" and password "<password>"
    Then user can see error message "<errorMessage>"
    Examples:
      | test_point          | username | password | errorMessage             |
      | when both invalid   | invalid  | invalid  | Invalid credentials      |
      | when username empty |          | invalid  | Username cannot be empty |
      | when password empty | invalid  |          | Password cannot be empty |