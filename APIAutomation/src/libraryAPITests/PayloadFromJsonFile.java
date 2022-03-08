package libraryAPITests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PayloadFromJsonFile {

	// General variables
	String baseUrl = "https://rahulshettyacademy.com";
	String resource = "Library/";
	String contentType = "Content-Type";
	String contentTypeValue = "application/json";
	ArrayList<String> createdBooksIds = new ArrayList<String>();
	// Add book Endpoint
	String addBook = "Addbook.php";
	// Delete book Endpoint
	String deleteBook = "DeleteBook.php";
	String expectedBookDeletionMsg = "book is successfully deleted";
	
	@Test
	public void addBook() throws IOException {
		// Add book
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeValue).body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Usuario\\Documents\\Workshops\\Rest API Testing\\APIAutomation\\src\\libraryAPITests\\addBookPayloadFile.json"))))
		.when().post(resource + addBook)
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);

		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		createdBooksIds.add(jsonResponse.get("ID"));
	}
	
	@Test
	public void deleteBook() {
		for(int x = 0; x < createdBooksIds.size(); x++) {
			String response = given().log().all().header(contentType, contentTypeValue).body(Payload.deleteBook(createdBooksIds.get(x)))
					.when().post(resource + deleteBook)
					.then().assertThat().statusCode(200).extract().response().asString();
			JsonPath jsonResponse = MethodsUtils.rawToJson(response);
			Assert.assertEquals(jsonResponse.get("msg"), expectedBookDeletionMsg);
		}
		
	}
}
