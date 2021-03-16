![alt text](https://www.neonomics.io/img/logo.svg)

## API Test and Functional test Framework
### Functional scenarios : 
```
Scenario 1 : Tests functional flow to retrieve all accounts from "Justo Bank"

Scenario: User retrieves accounts from Justo Bank
    Given user gets authetication token for Neonomics platform
    Then user retrieves bank ID for "Justo Bank"
    Then user "create" session for "Justo Bank"
    And user validates session status for "Justo Bank"
    Then user checks for "Justo Bank" consent and "Accept" if required provided redirect URL as ""
    And user fetches all accounts from "Justo Bank"
    Then user "delete" session for "Justo Bank"
```



```
Scenario 2 : Initiates payment from an account of Justo bank to Hizonti bank 

Scenario: User retrives account details for a customer and transfer money to another user
    Given user gets authetication token for Neonomics platform
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
```  
    
### API test scenarios : 
```
Scenario 1 : To test authentication API further for valid and invalid inputs

Scenario Outline: Verity that Auth token API works for various combinations
    Given user sets base URI and sets endpoint path as "/auth/realms/sandbox/protocol/openid-connect/token"
    Then user sets "Content-Type" as "<ContentType>" as header
    And user sets body params for "grant_type" as "<GrantType>"
    And user sets body params for "client_id" as "<ClientID>"
    And user sets body params for "client_secret" as "<ClientSecretID>"
    When user makes "POST" request to endpoint
    Then user validates status code as <ExpectedStatusCode>
    Then user validates that API should work for "Authorization" as "<ShouldWork>"

Examples: 
| ContentType                       | GrantType          | ClientID    | ClientSecretID | ExpectedStatusCode | ShouldWork |
| application/x-www-form-urlencoded | client_credentials | valid       | valid          |                200 | true       |
| application/json                  | client_credentials | valid       | valid          |                510 | false      |
| application/x-www-form-urlencoded | authorization_code | valid       | valid          |                510 | false      |
| application/x-www-form-urlencoded | client_credentials | invalid     | valid          |                510 | false      |
| application/json                  | client_credentials | valid       | invalid        |                510 | false      |
```
### Explanation
- **Functional Tests** focuses on funtional test of API for expected implementation usage, designed with Scenario
- **API Tests** focus on testing Neonomics API for various suuny and rainy days scenarios and its functionality on API level designed with Scenario outline

### Framework overview
- This is a hybrid maven based framework that uses Rest-Assured Java DSL library, Selenium (lilttle UI part), TestNG, JUnit and Cucumber (Gherkin)

### Prerequisites
- Java version 1.8.0_251
- Chrome version 89.0.4389.90
- Compatible chromedriver.exe

### Running the tests
**Given scenario is automated using selenium cucumber. Gherkin language is used in cucumber for these scenario.**
Funtional tests and API test are kept in two different feature files.
To run these files, we need to run test Runner class created (package = src/test/java/testRunner)
These runner clases provides glue between feature file and step definition file.
To tun specific feature file, **Right click > Run as > JUnit test** or **Right Click feature file > Run as > Cucumber Feature**

### Reports
##### 4 type of reports are being created under **/test-ouput** folder
  1 Accounts.html
  2 API_Tests.html
  3 Cucumber.xml
  4 Cucumber.json

### Future Implementation needed
- More data validation mechenism for accounts
- More API to be part of suite for complete test



## Author
##### Aakash Dahake
##### 16/03/2021






