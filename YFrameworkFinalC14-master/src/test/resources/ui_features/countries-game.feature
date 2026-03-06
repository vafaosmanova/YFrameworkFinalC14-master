@regression
Feature: Capitals and Countries Match

  @drag_and_drop
  Scenario: Drag and Drop capitals to its Countries
    Given user navigates to dhtmlgoodies countries page
    When user moves capitals to corresponding countries
      | Capital    | Country       |
      | Rome       | Italy         |
      | Seoul      | South Korea   |
      | Madrid     | Spain         |
      | Stockholm  | Sweden        |
      | Copenhagen | Denmark       |
      | Oslo       | Norway        |
      | Washington | United States |
    Then user can see capital box is green