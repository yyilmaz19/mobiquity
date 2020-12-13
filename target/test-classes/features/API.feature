Feature: API Test

  @regression @api
  Scenario: User should be able to get details when search any valid user with username
    When User sends GET request to endpoint "/users"
    Then Verifies that response status code is 200
    Then Verifies that response content type is "application/json; charset=utf-8"
    And  Verifies the a record with username "Delphine"

  @api
  Scenario: User should not be able to get details when search any invalid user with username
    When User sends GET request to endpoint "/users"
    Then Verifies that response status code is 200
    Then Verifies that response content type is "application/json; charset=utf-8"
    And  Verifies there is no record with username "Yasin"

  @api
  Scenario: User should be able to search for the posts written by any user
    When User is able to GET endpoint "/posts" written by username "Delphine"
    Then Verifies that response status code is 200
    Then Verifies that response content type is "application/json; charset=utf-8"
    And  Verifies the 10 posts written by the user

  @api
  Scenario: User should not be able to search for the posts written by invalid user
    When User is able to GET endpoint "/posts" written by username "Yilmaz"
    Then Verifies that response status code is 200
    Then Verifies that response content type is "application/json; charset=utf-8"
    And  Verifies the 0 posts written by the user

   @api
  Scenario Outline: User should be able to fetch the comments and validate if the emails in the comment section are in the proper format
    When User is able to GET all comments for each posts written by username "<username>"
    Then Verifies that response status code is 200
    Then Verifies that response content type is "application/json; charset=utf-8"
    And  Verifies comments e-mails of "<username>" posts are proper format

  Examples:
     |username |
     | Delphine|
     | Bret    |
     | Kamren  |

