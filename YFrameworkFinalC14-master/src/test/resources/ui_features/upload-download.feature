@upload-download
Feature: Uploading and Downloading Files With Selenium

  Background: Set Up
    Given user navigates to heroku app

  @upload
  Scenario: Upload a file to heroku app
    And clicks on file upload page
    When user uploads a file called "person.png"
    Then the file is successfully uploaded

  @download
  Scenario: Download a file from heroku app
    And clicks on file download page
    When user clicks on "LambdaTest.txt"
    Then the file is successfully downloaded
    And the file content matches the "ExpectedLambdaTest.txt"