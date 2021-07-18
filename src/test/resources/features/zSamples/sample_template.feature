Feature: Template

    ##Waits
  Scenario: Template for Waits Scenarios
    And I wait for "2" seconds

    ## Set text
  Scenario: Template for Set Text Scenarios
    And I set value "Ben" for "First Name"
    And I set value "24" for "Age[2]"

    ## Click link/button
  Scenario: Template for Click link/button Scenarios
    And I click on '<button_name>' button
    And I click on '<link_name>' link
    And I click on the '<normalize_name>' button
    And I click on the '<normalize_name>' link
    And I click on the '<normalize_name>' text

    ## Is displayed text
  Scenario: Template for is text displayed Scenarios
    And I should see the text '<any_text>' displayed
    And I should not see the text '<any_text>' displayed

    ## Select from dropdown
  Scenario: Template for Select from dropdown Scenarios

    ## Radio button
  Scenario: Template for Radio button Scenarios

    ## Check box
  Scenario: Template for Check box Scenarios

    ## Tables
  Scenario: Template for Tables Scenarios
    Then I should see the items in my cart as below:
      | item_name                         | item_price |
      | Sauce Labs Backpack               | $29.99     |
      | Sauce Labs Bike Light             | $9.99      |
      | Sauce Labs Bolt T-Shirt           | $15.99     |
      | Sauce Labs Fleece Jacket          | $49.99     |
      | Sauce Labs Onesie                 | $7.99      |
      | Test.allTheThings() T-Shirt (Red) | $15.99     |

    ## Ascending/Descending Order
  Scenario: Template for Ascending/Descending Order Scenarios
    Then Get values for "Name" column in table and verify strings in descending order
    Then Get values for "ID" column in table and verify numbers in descending order
    Then Get values for "Name" column in table and verify strings in ascending order
    Then Get values for "ID" column in table and verify numbers in ascending order
