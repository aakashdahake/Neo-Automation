Feature: API testing

  @authAPI
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
      | ContentType                       | GrantType          | ClientID                             | ClientSecretID                       | ExpectedStatusCode | ShouldWork |
      | application/x-www-form-urlencoded | client_credentials | b2b640b5-c68c-4cb0-863b-20f1aec8af21 | 0b83fb21-56f8-45fd-bad8-7a59d6bd978e |                200 | true       |
      | application/json                  | client_credentials | b2b640b5-c68c-4cb0-863b-20f1aec8af21 | 0b83fb21-56f8-45fd-bad8-7a59d6bd978e |                510 | false      |
      | application/x-www-form-urlencoded | authorization_code | b2b640b5-c68c-4cb0-863b-20f1aec8af21 | 0b83fb21-56f8-45fd-bad8-7a59d6bd978e |                510 | false      |
      | application/x-www-form-urlencoded | client_credentials | b2b640b5-c68c-4cb0-863b-20f1ae       | 0b83fb21-56f8-45fd-bad8-7a59d6bd978e |                510 | false      |
      | application/json                  | client_credentials | b2b640b5-c68c-4cb0-863b-20f1aec8af21 | 0b83fb21-56f8-45fd-bad8              |                510 | false      |

  @bankAPI
  Scenario Outline: Verify that Banks API works for various combinations
    Given user sets base URI and sets endpoint path as "<Endpoint>"
    Then user sets "Accept" as "<ContentType>" as header
    And user sets "Authorization" as "<Bearer Token>" as header
    And user sets "x-device-id" as "<XDeviceID>" as header
    When user makes "GET" request to endpoint
    Then user validates status code as <ExpectedStatusCode>
    Then user validates that API should work for "Banks" as "<ShouldWork>"

    Examples: 
      | Endpoint        | ContentType                       | Bearer Token | XDeviceID                 | ExpectedStatusCode | ShouldWork |
      | /ics/v3/banks   | application/json                  | Bearer Token | example_id_for_quickstart |                200 | true       |
      | /ics/v3/banksss | application/json                  | Bearer Token | example_id_for_quickstart |                510 | false      |
      | /ics/v3/banks   | application/json                  |              |                           |                510 | false      |
      | /ics/v3/banks   | application/x-www-form-urlencoded | Bearer Token | anything-different        |                406 | false      |
      | /ics/v3/banks   |                                   |              |                           |                510 | false      |

  @sessionAPI
  Scenario Outline: Verify that session API works to create new session for various combinations
    Given user sets base URI and sets endpoint path as "<Endpoint>"
    Then user sets "Content-Type" as "<ContentType>" as header
    And user sets "Authorization" as "<Bearer Token>" as header
    And user sets "x-device-id" as "<XDeviceID>" as header
    And user puts body content as "bankId" as "anVzdG9iYW5rLnYxSlVTVE5PS0s="
    When user makes "POST" request to endpoint
    Then user validates status code as <ExpectedStatusCode>
    Then user validates that API should work for "Session" as "<ShouldWork>"

    Examples: 
      | Endpoint          | ContentType            | Bearer Token | XDeviceID                 | ExpectedStatusCode | ShouldWork |
      | /ics/v3/session   | application/json       | Bearer Token | example_id_for_quickstart |                201 | true       |
      | /ics/v3/sessioned | application/json       | Bearer Token | example_id_for_quickstart |                510 | false      |
      | /ics/v3/session   | application/javascript | Bearer Token | example_id_for_quickstart |                520 | false      |
      | /ics/v3/session   | application/json       |              | example_id_for_quickstart |                401 | false      |
      | /ics/v3/session   | application/json       | Bearer Token |                           |                510 | false      |

  @sessionAPI
  Scenario Outline: Verify that session API for getting current status works for various combinations
    Given user sets base URI and sets endpoint path as "<Endpoint>"
    Then user sets "Accept" as "<ContentType>" as header
    And user sets "Authorization" as "<Bearer Token>" as header
    And user sets "x-device-id" as "<XDeviceID>" as header
    Then user creates session ID with device id as "<XDeviceID>" and bankId as "anVzdG9iYW5rLnYxSlVTVE5PS0s=" and puts it in endpoint URI
    When user makes "GET" request to endpoint
    Then user validates status code as <ExpectedStatusCode>
    Then user validates that API should work for "SessionStatus" as "<ShouldWork>

    Examples: 
      | Endpoint                    | ContentType                       | Bearer Token | XDeviceID                 | ExpectedStatusCode | ShouldWork |
      | /ics/v3/session/{sessionId} | application/json                  | Bearer Token | example_id_for_quickstart |                200 | true       |
      | /ics/v3/sn/asa{sessionId}   | application/json                  | Bearer Token | example_id_for_quickstart |                510 | false      |
      | /ics/v3/session/{sessionId} | application/json                  |              |                           |                510 | false      |
      | /ics/v3/session/{sessionId} | application/x-www-form-urlencoded | Bearer Token | anything-different        |                406 | false      |
      | /ics/v3/session/{sessionId} |                                   |              |                           |                510 | false      |
