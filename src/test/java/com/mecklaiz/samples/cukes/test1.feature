Feature: Basic Cucumber Test Template
"""
     A simple test using Cucumber and Selenium
"""
  Background:
    Given The following url:
      |URL|PORT|Search Term|data1|data2|data3|
      |https://www.google.com|80|cucumber|data1_1|data1_2|data1_3|
      |www.google.com|443|/images|data2_1|data2_2|data2_3|

  Scenario: Perform a Google Search
    Given There are 22 concurrent users using node
    Then The response time is less than 1000ms

