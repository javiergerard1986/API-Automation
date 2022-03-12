package libraryAPITests;

import java.util.HashMap;

public class Payload {

	public static String addBookPayload(String isbn, int aisle) {
		String body = "{\r\n" + 
				"\r\n" + 
				"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
				"\"isbn\":\"" + isbn + "\",\r\n" + 
				"\"aisle\":\"" + aisle + "\",\r\n" + 
				"\"author\":\"John foe\"\r\n" + 
				"}\r\n" + 
				"";
		return body;
	}
	
	public static HashMap<String, Object> addBookHashMapPayload(String isbn, int aisle) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", "APIAutomation");
		map.put("isbn", isbn);
		map.put("aisle", aisle);
		map.put("author", "Javier Gerard");
		
		return map;
	}
	
	public static String deleteBook(String createdBookId) {
		String body = "{\r\n" + 
				"\"ID\" : \""+ createdBookId + "\"\r\n" + 
				"} \r\n" + 
				"";
		return body;
	}
	
}
