package libraryAPITests;

import java.util.Random;

import io.restassured.path.json.JsonPath;

public class MethodsUtils {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
	
	public static String generateRandomString() {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    return random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	}
	
	public static int generateRandomInt() {
	    Random random = new Random();
	    int upperBound = 9999;
	    
	    return random.nextInt(upperBound);
	}
	
}
