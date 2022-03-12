Feature: Validating Place API's

Scenario: Verify that after register a place then the place can be deleted
Given a valid AddPlace payload
When user calls the "AddPlaceAPI" with "post" http request
Then the API call got success with status code 200
And "status" in response is "OK"
And scope in response body is "APP"
Given DeletePlace Payload
When user calls the "DeletePlaceAPI" with "post" http request
Then the API call got success with status code 200
And "status" in response is "OK"

Scenario Outline: Verify if Place is being succesfully added using the AddPlaceAPI
Given a valid AddPlace payload with "<address>" "<name>" <latitude> <longitude>
When user calls the "AddPlaceAPI" with "post" http request
Then the API call got success with status code 200
And "status" in response is "OK"
And scope in response body is "APP"
And verify that "place_id" created maps to "<name>" using "GetPlaceAPI"
Examples:
| address 				    		| name 			  			| latitude 		| longitude |
| Maldonado 1238					| La Torteria				| -38.383432	| 33.427524 |
| Avenida Centenario 3313 | Confiteria Roma		| -38.381234	| 33.428564 |
| Larravide 1212				  | Bar La Esperanza	| -38.3835987	| 33.425246 |
