import files.MockedResponses;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		JsonPath jsonResponse = new JsonPath(MockedResponses.coursePrice());
		int coursesPriceAmount = 0;
		
		for(int x = 0; x < jsonResponse.getInt("courses.size()"); x ++) {
			coursesPriceAmount += jsonResponse.getInt("courses[" + x + "].price") * jsonResponse.getInt("courses[" + x + "].copies");
		}

		Assert.assertEquals(coursesPriceAmount, jsonResponse.getInt("dashboard.purchaseAmount"));
	}
	
}
