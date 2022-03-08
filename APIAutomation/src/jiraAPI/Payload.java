package jiraAPI;

public class Payload {

	public static String createIssue(String key, String summary, String description, String issueType) {
		String body = "{\r\n" + 
				"        \"fields\": {\r\n" + 
				"            \"project\": {\r\n" + 
				"                \"key\": \"" + key + "\"\r\n" + 
				"            },\r\n" + 
				"            \"summary\": \""+ summary + "\",\r\n" + 
				"            \"description\": \""+ description + "\",\r\n" + 
				"            \"issuetype\": {\r\n" + 
				"                \"name\": \"" + issueType + "\"\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"}";
		return body;
	}
	
	public static String addCommentToIssue(String comment) {
		String body = "{\r\n" + 
				"    \"body\": \"" + comment + "\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}";
		return body;
	}
	
	public static String login(String username, String password) {
		String body = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";
		return body;
	}
}