@regression
Feature: Test HTML Table

  Background: User navigates to Application URL
    Given The Application has navigated tables sandbox

  Scenario: Verify Table with Table Header
    Given I wait for "5" seconds
    Then "Simple Table (item prices)" table should be displayed as below:
      | Item    | Price    |
      | Oranges | $3.99    |
      | Laptop  | $1200.00 |
      | Marbles | $1.25    |


  Scenario: Verify Table with Column Headers
    Given I wait for "5" seconds
    Then Table should be displayed as below:
      | Item    | Price    |
      | Oranges | $3.99    |
      | Laptop  | $1200.00 |
      | Marbles | $1.25    |