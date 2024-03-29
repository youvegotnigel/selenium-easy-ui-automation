Feature: Template

    ##Waits
  Scenario: Template for Waits Scenarios
    And I wait for 2 seconds

    ## Set text
  Scenario: Template for Set Text Scenarios
    And I set value "Ben" for "First Name"
    And I set value "24" for "Age[2]"

   ## Inputs
  Scenario: Template for Tables Scenarios
    And I enter values as below:
      | Last Name                     | <lname>       |
      | E-Mail                        | <email>       |
      | Project Description[textarea] | <description> |

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
    And I select visible text from dropdown "New York" for label "State"
    And I select value from dropdown "Texas" for label "State"
    And I select index from dropdown 3 for label 'State'

    ## Radio button
  Scenario: Template for Radio button Scenarios
    And I set radio value "yes" for label "Do you have hosting?"

    ## Check box
  Scenario: Template for Check box Scenarios

    ## Tables
  Scenario: Template for Table Scenarios
    Then Table should be displayed as below:
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |
    Then "Simple Table (item prices)" table should be displayed as below:
      | Item    | Price |
      | Oranges | $3.99 |
    When I click on header "Country" column of below table
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |
    And I click on "Pakistan" cell in "Sortable Table (countries by population)" Table
    Then "Sortable Table (countries by population)" Table should filer "Country" as below:
      | United States |
      | Pakistan      |
      | Italy         |
      | Tanzania      |

    ## Ascending/Descending Order
  Scenario: Template for Ascending/Descending Order Scenarios
    Then Get values for "Name" column in "Items" table and verify strings in descending order
    Then Get values for "ID" column in "Items" table and verify numbers in descending order
    Then Get values for "Name" column in "Items" table and verify strings in ascending order
    Then Get values for "ID" column in "Items" table and verify numbers in ascending order
