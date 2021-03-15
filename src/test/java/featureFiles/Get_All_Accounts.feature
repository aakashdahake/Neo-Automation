Feature: Functional testing

  Scenario: User retrieves accounts from Justo Bank
    Given user gets authetication token for Neonomics platform
    Then user retrieves bank ID for "Justo Bank" provided x-device-id as "example_id_for_quickstart"
    Then user "create" session for "Justo Bank" with x-device-id as "example_id_for_quickstart"
    And user validates session status for "Justo Bank"
    Then user checks for "Justo Bank" consent and "Accept" if required provided redirect URL as "" and x-device-id as "example_id_for_quickstart"
    And user fetches all accounts from "Justo Bank"
    Then user "delete" session for "Justo Bank" with x-device-id as "example_id_for_quickstart"
    