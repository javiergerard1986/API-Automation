package serializationAndDeserialization;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class SerializationTests {

	// General variables
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
		RestAssured.baseURI = baseUrl;
		AddPlaceResponse addPlaceResponse = given().log().all().header(contentTypeHeader, contentTypeValue).queryParam(keyQP, keyValue).body(createPlaceObject())
		.when().post(resource + addPlace).as(AddPlaceResponse.class);
		
		System.out.println("Created ID Location: " + addPlaceResponse.getId());
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
	
}
