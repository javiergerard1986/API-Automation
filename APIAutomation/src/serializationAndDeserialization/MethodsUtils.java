package serializationAndDeserialization;

import io.restassured.path.json.JsonPath;

public class MethodsUtils {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
	
}
