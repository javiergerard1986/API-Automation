package jiraAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JIRATests {

	// General variables
	String baseUrl = "http://localhost:8081";
	String resource = "/rest/api/2/";
	String contentType = "Content-Type";
	String contentTypeAppJsonValue = "application/json";
	String contentTypeFormDataValue = "multipart/form-data";
	// JIRA Login
	String login = "rest/auth/1/session";
	String username = "javiergerard1986";
	String password = "Yb5Nmrho!";
	String cookieHeader = "cookie";
	String cookieValue;
	SessionFilter session;
	// Create issue Endpoint
	String createIssue = "issue";
	String key = "RSA";
	String summary = "Automated Visa card issue!!!";
	String description = "This issue was created by an automated Test";
	String issueType = "Bug";
	String issueId;
	// Add comment to issue Endpoint
	String addCommentToIssue = "issue/{issueId}/comment";
	String comment = "Comment added by an automated Test";
	String commentId;
	// Add attachment to issue Endpoint
	String addAttachmentToIssue = "issue/{issueId}/attachments";
	String atlassianTokenHeader = "X-Atlassian-Token";
	String atlassianTokenValue = "no-check";
	// Get issue Endpoint
	String getIssue = "issue/{issueId}";
	String expectedComment = "Comment added by an automated Test";
	
		
	
	
	@Test(priority=1)	
	public void jiraLogin() {
		// Login into JIRA
		session = new SessionFilter();
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeAppJsonValue)
				.body(Payload.login(username, password))
				.filter(session)
			    .when().post(login)
			    .then().assertThat().statusCode(200).extract().response().asString();
		
		// Parse json response in order to get the place_id that comes in the response
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		cookieValue = jsonResponse.getString("session.name") + "=" + jsonResponse.getString("session.value");
	}
	
	@Test(priority=2)	
	public void createIssue() {
		// Create issue
		// Using session filter instead of set the cookie session Id by a header attribute
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeAppJsonValue)
				//.header(cookieHeader, cookieValue)
				.body(Payload.createIssue(key, summary, description, issueType))
				.filter(session)
			    .when().post(resource + createIssue)
			    .then().assertThat().statusCode(201).extract().response().asString();
		
		// Parse json response in order to get the place_id that comes in the response
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		issueId = jsonResponse.getString("id");;
	}
	
	@Test(priority=3)
	public void addCommentToIssue() {
		// Add comment to issue
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType, contentTypeAppJsonValue)
				.pathParam("issueId", issueId)
				.header(cookieHeader, cookieValue)
				.body(Payload.addCommentToIssue(comment))
			    .when().post(resource + addCommentToIssue)
			    .then().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		commentId = jsonResponse.getString("id");
		
	}
	
	@Test(priority=4)
	public void addAttachmentToIssue() {
		// Add comment to issue
		RestAssured.baseURI = baseUrl;
		given().log().all().header(atlassianTokenHeader, atlassianTokenValue)
				.filter(session)
				.pathParam("issueId", issueId)
				.header(contentType,contentTypeFormDataValue)
			    .multiPart("file", new File("screenshot.JPG"))
				.when().post(resource + addAttachmentToIssue)
			    .then().assertThat().statusCode(200);
	}
	
	@Test(priority=5)
	public void getIssue() {
		// Sometimes you need to use HTTPS certifications, in order to work with it, you will need to use the relaxedHTTPSValidation functionality in the given scope. Example:
		// String response = given().log().all().relaxedHTTPSValidation().header(contentType,contentTypeAppJsonValue)
		
		// Get Issue
		// Filtering the list of attributes to obtain from the whole response
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().header(contentType,contentTypeAppJsonValue)
				.filter(session)
				.pathParam("issueId", issueId)
				.queryParam("fields,id")
				.when().get(resource + getIssue)
			    .then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		
		// Print issue Id and title
		System.out.println(jsonResponse.getString("id") + " - " + jsonResponse.getString("fields.summary"));
		
		// Print all the issue comments
		int commentsAmount = jsonResponse.get("fields.comment.comments.size()");
		System.out.println(commentsAmount);
		for (int x = 0; x < commentsAmount; x++) {
			System.out.println(jsonResponse.getInt("fields.comment.comments[" + x + "].id") + " - " + jsonResponse.getString("fields.comment.comments[" + x + "].body"));
			Assert.assertEquals(jsonResponse.getString("fields.comment.comments[" + x + "].body"), expectedComment);
		}
	}
	
}
