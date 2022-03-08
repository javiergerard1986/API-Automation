package libraryAPITests;

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
	
	public static String deleteBook(String createdBookId) {
		String body = "{\r\n" + 
				"\"ID\" : \""+ createdBookId + "\"\r\n" + 
				"} \r\n" + 
				"";
		return body;
	}
	
}
