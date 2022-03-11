package resources;

import java.util.ArrayList;
import java.util.List;
import pojo.requests.Place;
import pojo.requests.Location;

public class TestDataBuilder {

	public Place createPlaceObject() {
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
