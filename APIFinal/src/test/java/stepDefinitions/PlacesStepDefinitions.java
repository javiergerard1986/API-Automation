package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.responses.AddPlaceResponse;
import resources.TestDataBuilder;

public class PlacesStepDefinitions extends BaseStepDefinitions{

	// API variables
	TestDataBuilder testDataBuilder = new TestDataBuilder();
	Response response;
	
	 @Given("^a valid AddPlace payload$")
	 public void a_valid_addplace_payload() {
		// Set request and response builder
		 this.setRequestAndResponseBuilder();
			 
		// Define request to execute
		this.requestSpec = given()
				.spec(this.requestSpec)
				.body(testDataBuilder.createHardcodedPlaceObject());
	 }
	 
	 @Given("a valid AddPlace payload with {string} {string} {double} {double}")
	 public void a_valid_addplace_payload_with(String address, String name, double latitude, double longitude) {
		// Set request and response builder
		 this.setRequestAndResponseBuilder();
			 
		// Define request to execute
		this.requestSpec = given()
				.spec(this.requestSpec)
				.body(testDataBuilder.createPlaceObject(address, name, latitude, longitude));
	 }
	 
	 @When("user calls the AddPlace endpoint with post http request")
	 public void user_calls_the_addplaceapi_with_post_http_request() {
	     response = this.requestSpec
	    		 .when().post(this.propertiesLoader.getGlobalValue("resource") + this.propertiesLoader.getGlobalValue("addPlace"))
	    		 .then().spec(this.responseSpec).extract().response();
	 }
	 
	 @Then("the API call got success with status code {int}")
	 public void the_api_call_got_success_with_status_code(int statusCode) {
		 assertEquals(this.response.getStatusCode(), statusCode);
	 }
	 
	 @And("\"([^\"]*)\" in response is \"([^\"]*)\"$")
	 public void status_in_response_body_is_something(String param, String value) {
	   JsonPath js = new JsonPath(this.response.asString());
	   assertEquals(js.get(param), value);
	 }
	 
	 @And("scope in response body is {string}")
	 public void scope_in_response_body_is_something(String scope) {
		 AddPlaceResponse addPlace = this.response.as(AddPlaceResponse.class);
		 assertEquals(addPlace.getScope(), scope);
	 }
	 
	 private void setRequestAndResponseBuilder(){
		// Set the request builder
		this.buildRequestSpecification();
		// Set the response builder 
		this.buildResponseSpecification(200);
	 }
	
}
