Feature: Validating Place API's

Scenario: Verify if Place is being succesfully added using the AddPlaceAPI
Given a valid AddPlace payload
When user calls the AddPlace endpoint with post http request
Then the API call got success with status code 200
And "status" in response is "OK"
And scope in response body is "APP"