@regression
Feature: Input Form Demo

  Background: User navigates to Application URL
    Given The Application has been launched
    And User has navigated "Form Fields" sandbox

  Scenario Outline: I should be able to fil all fields of the form
    And I enter values as below:
      | Name   | Do you have any siblings?[select] | Email   | Message[textarea] |
      | <name> | <siblings>                        | <email> | <message>         |
    And I set checkbox value "<drink>" for label "What is your favorite drink?"
    And I set radio value "<color>" for label "What is your favorite color?"
    When I click on Submit form button
    And I wait for 5 seconds
    Then Application should display success message "Your message has been sent"
    And Application should display form submission values as below:
      | Name:  | What is your favorite drink?: | What is your favorite color?: | Do you have any siblings?: | Email:  | Message:  |
      | <name> | <drink>                       | <color>                       | <siblings>                 | <email> | <message> |

    Examples:
      | name   | siblings | email                  | message                            | color | drink  |
      | Eliseo | No       | eliseo.ringer@mail.com | This is my test automation project | Blue  | Coffee |
