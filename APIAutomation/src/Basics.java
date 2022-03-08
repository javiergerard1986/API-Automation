import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import files.MethodsUtils;
import files.Payload;

public class Basics {
	
	// General variables
	static String baseUrl = "https://rahulshettyacademy.com";
	static String resource = "maps/api/place/";
	static String key = "key";
	static String keyValue = "qaclick123";
	static String contentType = "Content-Type";
	static String contentTypeValue = "application/json";
	// Add Place Endpoint
	static String addPlace = "add/json";
	static String bodyScope = "scope";
	static String scopeValue = "APP";
	static String serverHeader = "server";
	static String serverValue = "Apache/2.4.18 (Ubuntu)";
	// Update Place Endpoint
	static String updatePlace = "update/json";
	static String newAddress = "70 Summer walk, USA";
	static String bodyMsg = "msg";
	static String msgValue = "Address successfully updated";
	// Get Place Endpoint
	static String getPlace = "get/json";
	static String placeIdParam = "place_id";
	static String placeIdValue;
	static String addressParam;
	
	public static void main(String[] args) {
		// Create PLACE thru Google Maps API
		RestAssured.baseURI = baseUrl;
		// Given: contains all input details (you can use log().all() to log in the output the request)
		// When: contains all submit API details, example: resource, HTTP or HTTPS method
		// Then: validate response (you can use log().all() to log in the output the response)
		System.out.println("----------------------");
		System.out.println("----------------------");
		
		String response = given().log().all().queryParam(key, keyValue).header(contentType, contentTypeValue).body(Payload.addPlacePayload())
	    .when().post(resource + addPlace)
	    .then().assertThat().statusCode(200).body(bodyScope, equalTo(scopeValue))
	    .header(serverHeader, serverValue).extract().response().asString();
		
		// Parse json response in order to get the place_id that comes in the response
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		placeIdValue = jsonResponse.getString(placeIdParam);
		
		System.out.println("----------------------");
		System.out.println("----------------------");

		// Update the created place
		given().log().all().queryParam(key, keyValue).header(contentType, contentTypeValue).body(Payload.updatePlace(placeIdValue, newAddress, keyValue))
		.when().put(resource + updatePlace)
		.then().assertThat().statusCode(200).body(bodyMsg, equalTo(msgValue)).extract().response().asString();
		
		System.out.println("----------------------");
		System.out.println("----------------------");
		
		// Get the modified place and verify that the address was updated
		response = given().log().all().queryParam(key, keyValue).queryParam(placeIdParam, placeIdValue).header(contentType, contentTypeValue)
		.when().get(resource + getPlace)
		.then().assertThat().statusCode(200).extract().asString();
		
		jsonResponse = MethodsUtils.rawToJson(response);
		Assert.assertEquals(jsonResponse.getString("address"), newAddress);
		
		System.out.println("----------------------");
		System.out.println("----------------------");
	}

}
