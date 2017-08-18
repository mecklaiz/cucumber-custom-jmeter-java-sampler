Feature: Basic Cucumber Test Template
"""
     A simple test using Cucumber and Selenium
"""
  Background:
    Given The following url:
      |ID|URL|PORT|PATH|data1|data2|data3|
      |TST1|localhost|8085|/hello|data1_1|data1_2|data1_3|
      |TST2|localhost|8085|/|data1_1|data1_2|data1_3|
      |TST3|localhost|8085|/dump|data1_1|data1_2|data1_3|
      |TST4|localhost|8085|/helloserv|data1_1|data1_2|data1_3|

  Scenario: Load the page and check the time taken
    Given The ID TST1
    When I load the page
    Then The response time is less than 1000ms

  Scenario: Load the page and check the time taken
    Given The ID TST2
    When I load the page
    Then The response time is less than 1000ms

  Scenario: Load the page and check the time taken
    Given The ID TST3
    When I load the page
    Then The response time is less than 1000ms

  Scenario: Load the page and check the time taken
    Given The ID TST4
    When I load the page
    Then The response time is less than 1000ms
