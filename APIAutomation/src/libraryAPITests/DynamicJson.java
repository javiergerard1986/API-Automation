package libraryAPITests;

import java.util.ArrayList;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJson {

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
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, int aisle) {
		// Add book
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeValue).body(Payload.addBookPayload(isbn, aisle))
		.when().post(resource + addBook)
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);

		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		createdBooksIds.add(jsonResponse.get("ID"));
	}
	
	@Test(dataProvider="BooksData")
	public void addBookUsingHashMapAsPayload(String isbn, int aisle) {
		// Add book
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeValue).body(Payload.addBookHashMapPayload(isbn, aisle))
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
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		return new Object[][] {{MethodsUtils.generateRandomString(), MethodsUtils.generateRandomInt()}, {MethodsUtils.generateRandomString(), MethodsUtils.generateRandomInt()}, {MethodsUtils.generateRandomString(), MethodsUtils.generateRandomInt()}};
	}
	
}
