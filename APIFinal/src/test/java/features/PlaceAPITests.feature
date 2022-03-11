Feature: Validating Place API's

Scenario: Verify if Place is being succesfully added using the AddPlaceAPI
Given a valid AddPlace payload
When user calls the AddPlace endpoint with post http request
Then the API call got success with status code 200
And "status" in response is "OK"
And scope in response body is "APP"

Scenario Outline: Verify if Place is being succesfully added using the AddPlaceAPI
Given a valid AddPlace payload with "<address>" "<name>" <latitude> <longitude>
When user calls the AddPlace endpoint with post http request
Then the API call got success with status code 200
And "status" in response is "OK"
And scope in response body is "APP"
Examples:
| address 				    		| name 			  			| latitude 		| longitude |
| Maldonado 1238					| La Torteria				| -38.383432	| 33.427524 |
| Avenida Centenario 3313 | Confiteria Roma		| -38.381234	| 33.428564 |
| Larravide 1212				  | Bar La Esperanza	| -38.3835987	| 33.425246 |
