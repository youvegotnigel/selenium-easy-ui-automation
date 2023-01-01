@regression1
Feature: Test HTML Table

  Background: User navigates to Application URL
    Given The Application has been launched
    And User has navigated "tables" sandbox

  Scenario: Verify Table with Table Header
    Given I wait for 5 seconds
    Then "Simple Table (item prices)" table should be displayed as below:
      | Item    | Price    |
      | Oranges | $3.99    |
      | Laptop  | $1200.00 |
      | Marbles | $1.25    |

  Scenario: Verify Table with Column Headers
    Given I wait for 5 seconds
    Then Table should be displayed as below:
      | Rank | Country       | Population (million) |
      | 1    | China         | 1,439.3              |
      | 2    | India         | 1,380                |
      | 3    | United States | 331                  |

  Scenario: Verify table filter after click on table header cell
    Given I wait for 5 seconds
    When I click on header "Country" column of below table
      | Rank | Country       | Population (million) |
      | 1    | China         | 1,439.3              |
      | 2    | India         | 1,380                |
      | 3    | United States | 331                  |
    Then Table should be displayed as below:
      | Rank | Country    | Population (million) |
      | 8    | Bangladesh | 164.7                |
      | 6    | Brazil     | 212.6                |
      | 1    | China      | 1,439.3              |
    But Table should not be displayed as below:
      | Rank | Country       | Population (million) |
      | 3    | United States | 331                  |

  Scenario: Verify table Search functionality
    Given Table is displayed as below:
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |
    When I type "France" in table search
    Then Table should be displayed as below:
      | Rank | Country | Population (million) |
      | 22   | France  | 65.3                 |
    But Table should not be displayed as below:
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |

  Scenario: Verify table Search Filter functionality
    Given Table is displayed as below:
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |
    When I type "Ta" in table search
    Then "Sortable Table (countries by population)" Table should filer "Country" as below:
      | United States |
      | Pakistan      |
      | Italy         |
      | Tanzania      |
    
  Scenario: Verify able to click on a table cell
    Given Table is displayed as below:
      | Rank | Country | Population (million) |
      | 1    | China   | 1,439.3              |
    When I type "Ta" in table search
    And I click on "Pakistan" cell in "Sortable Table (countries by population)" Table
    And I click on "Italy" cell in "Sortable Table (countries by population)" Table