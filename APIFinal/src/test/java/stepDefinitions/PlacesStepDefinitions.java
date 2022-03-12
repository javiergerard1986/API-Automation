package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import config.APIResources;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import pojo.responses.AddPlaceResponse;
import resources.TestDataBuilder;
import utils.Utils;

public class PlacesStepDefinitions extends BaseStepDefinitions{

	// API variables
	TestDataBuilder testDataBuilder = new TestDataBuilder();
	Utils utils = new Utils();
	
	 @Given("^a valid AddPlace payload$")
	 public void a_valid_addplace_payload() {
		// Set request and response builders
		this.setRequestAndResponseBuilder();
		
		// Define request to execute
		this.requestSpec = given()
				.spec(this.requestSpec)
				.body(testDataBuilder.createHardcodedPlaceObject());
	 }
	 
	 @Given("a valid AddPlace payload with {string} {string} {double} {double}")
	 public void a_valid_addplace_payload_with(String address, String name, double latitude, double longitude) {
		// Set request and response builders
		this.setRequestAndResponseBuilder();
		
		// Define request to execute
		this.requestSpec = given()
				.spec(this.requestSpec)
				.body(testDataBuilder.createPlaceObject(address, name, latitude, longitude));
	 }
	 
	 @Given("DeletePlace Payload")
	 public void deletePlace_Payload() {
		// Set request and response builders
		this.setRequestAndResponseBuilder();
		 
		// Define request to execute
		this.requestSpec = given()
				.spec(this.requestSpec)
				.body(testDataBuilder.getDeletePlacePayload(this.utils.getJsonPath(this.response, "place_id")));
	 }
	 
	 @When("user calls the {string} with {string} http request")
	 public void user_calls_the_with_post_http_request(String resource, String httpMethod) {
	     APIResources apiResource = APIResources.valueOf(resource);
		 this.setAndExecuteRequest(apiResource, httpMethod);
	 }
	 
	 @Then("the API call got success with status code {int}")
	 public void the_api_call_got_success_with_status_code(int statusCode) {
		 assertEquals(this.response.getStatusCode(), statusCode);
	 }
	 
	 @And("\"([^\"]*)\" in response is \"([^\"]*)\"$")
	 public void in_response_body_is_something(String param, String value) {
	   JsonPath js = new JsonPath(this.response.asString());
	   assertEquals(js.get(param), value);
	 }
	 
	 @And("scope in response body is {string}")
	 public void scope_in_response_body_is_something(String scope) {
		 AddPlaceResponse addPlaceResponse = this.response.as(AddPlaceResponse.class);
		 assertEquals(addPlaceResponse.getScope(), scope);
	 }
	 
	 @And("verify that {string} created maps to {string} using {string}")
     public void verify_that_created_maps_to_using(String placeIdQP, String placeName, String resource){
		// Set request
		 this.requestSpec = given().spec(this.requestSpec)
				 			.queryParam(placeIdQP, this.utils.getJsonPath(this.response, placeIdQP));
		 
		 // Execute request
		 this.user_calls_the_with_post_http_request(resource, "get");
		 
		 // Verify that the response is success with status code 200
		 this.the_api_call_got_success_with_status_code(200);
		 
		 // Verify that the response to the get api contains the name of the created place
		 this.in_response_body_is_something("name", placeName);
		 
		 
	 }
	 
	 public void setRequestAndResponseBuilder(){
		// Set the request builder
		this.buildRequestSpecification();
		// Set the response builder 
		this.buildResponseSpecification(200);
	 }
	
}
