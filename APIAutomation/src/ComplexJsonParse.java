import files.MockedResponses;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath jsonResponse = new JsonPath(MockedResponses.coursePrice());
		
		// Obtain courses count
		System.out.println("--------------------");
		System.out.println("Courses count: " + jsonResponse.getInt("courses.size()"));
		System.out.println("--------------------");
		
		// Obtain Purchases amount
		System.out.println("--------------------");
		System.out.println("Purchases amount: " + jsonResponse.getInt("dashboard.purchaseAmount"));
		System.out.println("--------------------");
		
		// Obtain course titles
		System.out.println("--------------------");
		System.out.println("Course Titles:");
		for (int x = 0; x < jsonResponse.getInt("courses.size()"); x++) {
			System.out.println("Course " + jsonResponse.getString("courses[" + x + "].title"));
		}
		System.out.println("--------------------");
		
		// Obtain course and prices
		System.out.println("--------------------");
		System.out.println("Course Titles:");
		for (int x = 0; x < jsonResponse.getInt("courses.size()"); x++) {
			System.out.println("Course " + jsonResponse.getString("courses[" + x + "].title") + " - Price: " + jsonResponse.getString("courses[" + x + "].price"));
		}
		System.out.println("--------------------");
		
		// Print copies by RPA course
		for (int x = 0; x < jsonResponse.getInt("courses.size()"); x++) {
			if(jsonResponse.getString("courses.title[" + x + "]").equals("RPA")) {
				System.out.println("--------------------");
				System.out.println("RPA course copies: " + jsonResponse.getInt("courses[" + x + "].copies"));
				System.out.println("--------------------");
			}
		}
		
	}

}
