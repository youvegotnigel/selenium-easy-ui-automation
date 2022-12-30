[![Java CI with Maven](https://github.com/youvegotnigel/cucmber-java-testng-saucelabs/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/youvegotnigel/cucmber-java-testng-saucelabs/actions/workflows/maven.yml)

## How to run

type
```bash
mvn clean install -Ddataproviderthreadcount=1
```
in terminal

## Main Features
* Selenium
* Java
* TestNG
* Page Object Model
* [Log4j 2](https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html)
* Capture Screenshots
* Cucumber BDD Framework
* Cucumber HTML Report
* Allure Report

## Parallel Execution
To turn on parallel execution mode set ```@DataProvider(parallel = true)``` in the Test Runner class.
By default, it will be set to false.

type
```bash
mvn clean install -Ddataproviderthreadcount=3
```
in terminal


## Author
* **Nigel Mulholland** - [Linkedin](https://www.linkedin.com/in/nigel-mulholland/) 