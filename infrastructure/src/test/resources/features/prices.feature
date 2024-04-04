Feature: Price Inquiry

  Scenario: Successful price inquiry
    Given I have a valid product identifier
    When I request price information for the product
    Then I should receive the price information

  Scenario: Price inquiry with invalid product identifier
    Given I have an invalid product identifier
    When I request price information for the product
    Then I should receive an error response