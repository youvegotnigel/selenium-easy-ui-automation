@regression
Feature: Input Form Demo

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: I should be able to fil all fields of the form
    And I enter values as below:
      | Name            | E-mail  | Telephone | Country   | Company   | Message       |
      | <fname> <lname> | <email> | <phone>   | <country> | <company> | <description> |
    And I wait for "10" seconds


    Examples:
      | fname  | lname   | email                    | phone        | country | company | description                        |
      | Eliseo | Reinger | eliseo.reinger@gmail.com | 409-615-8636 | USA     | Zell    | This is my test automation project |
