package requestResponseBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class SpecBuilderTests {

	// General variables
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	String baseUrl = "https://rahulshettyacademy.com";
	String resource = "maps/api/place/";
	String keyQP = "key";
	String keyValue = "qaclick123";
	String contentTypeHeader = "Content-Type";
	String contentTypeValue = "application/json";
	// Add place endpoint
	String addPlace = "add/json";
	
	
	@Test(priority = 1)
	public void addPlace(){
		// Build the request
		buildRequestSpecification(baseUrl, keyQP, keyValue);
		buildResponseSpecification(200);
		
		requestSpec = given().log().all().spec(requestSpec).body(createPlaceObject());
		
		// Verify status code in the response object
		Response response = requestSpec.when().post(resource + addPlace)
				.then().log().all().assertThat().statusCode(200).spec(responseSpec).extract().response();
		
		// Deserialize the response into a POJO
		AddPlaceResponse addPlaceResponse = response.as(AddPlaceResponse.class);
		
		System.out.println("Added Place Id: " + addPlaceResponse.getPlace_id());
		System.out.println(response.asString());
	}
	
	private Place createPlaceObject() {
		Place place = new Place();
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		place.setLocation(location);
		place.setAccuracy(50);
		place.setName("Frontline house");
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress("29, side layout, cohen 09");
		List<String> typesList = new ArrayList<String>();
		typesList.add("shoe park");
		typesList.add("shop");
		place.setTypes(typesList);
		place.setWebsite("https://rahulshettyacademy.com");
		place.setLanguage("Frontline house");
		
		return place;
	}
	
	private void buildRequestSpecification(String baseUrl, String keyQP, String keyValue) {
		requestSpec = new RequestSpecBuilder()
				.setBaseUri(baseUrl)
				.addQueryParam(keyQP, keyValue)
				.setContentType(ContentType.JSON).build();	
	}
	
	private void buildResponseSpecification(int statusCode) {
		responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
	}
	
}
