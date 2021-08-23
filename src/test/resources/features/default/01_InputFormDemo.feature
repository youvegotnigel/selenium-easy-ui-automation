@regression
Feature: Input Form Demo

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: I should be able to fil all fields of the form
    When I select main menu 'Input Forms' and select sub menu 'Input Form Submit'
    Then I should see the text 'Input form with validations' displayed
    Given I set value "<fname>" for "First Name"
    And I enter values as below:
      | Last Name                     | <lname>       |
      | E-Mail                        | <email>       |
      | Phone #                       | <phone>       |
      | Address                       | <address>     |
      | City                          | <city>        |
      | Zip Code                      | <zip>         |
      | Website or domain name        | <website>     |
      | Project Description[textarea] | <description> |
    And I set radio value "yes" for label "Do you have hosting?"
    And I select visible text from dropdown "New York" for label "State"
    And I click on 'Send' button


    Examples:
      | fname  | lname   | email                    | phone        | address     | city     | zip   | website            | description                        |
      | Eliseo | Reinger | eliseo.reinger@gmail.com | 409-615-8636 | Brooklyn NY | New York | 87542 | testautomation.com | This is my test automation project |
