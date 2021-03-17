Feature: Functional testing
	@test
  Scenario: User retrieves accounts from Justo Bank
    Given user gets authentication token for Neonomics platform
    Then user retrieves bank ID for "Justo Bank"
    Then user "create" session for "Justo Bank"
    And user validates session status for "Justo Bank"
    Then user checks for "Justo Bank" consent and "Accept" if required provided redirect URL as ""
    And user fetches all accounts from "Justo Bank"
    Then user "delete" session for "Justo Bank"

  Scenario: User initiates payment and transfer money to another user in another bank
    Given user gets authentication token for Neonomics platform
    Then user retrieves bank ID for "Justo Bank"
    Then user "create" session for "Justo Bank"
    And user validates session status for "Justo Bank"
    Then user retrieves bank ID for "Hizonti Bank"
    Then user "create" session for "Hizonti Bank"
    And user validates session status for "Hizonti Bank"
    Then user checks for "Justo Bank" consent and "Accept" if required provided redirect URL as ""
    Then user checks for "Hizonti Bank" consent and "Accept" if required provided redirect URL as ""
    Then user transfer amount of "5" "EUR" from "Justo Bank" bank account "IABN" "NO6404948998640" to "Hizonti Bank" bank account "NO4313081115656" with "sepa-credit" payment method
    And user validates payment request is initiated
    Then user authorizes the the payment and obtains authorization URL
    Then user authorizes process using authorization URL
    Then user "delete" session for "Justo Bank"
    Then user "delete" session for "Hizonti Bank"
