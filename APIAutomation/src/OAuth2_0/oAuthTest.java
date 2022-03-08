package OAuth2_0;

import org.testng.annotations.Test;
import files.MethodsUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class oAuthTest {

	// General variables
	String baseUrl = "https://rahulshettyacademy.com";
	String accessTokenQParam = "access_token";
	String accessTokenValue;
	// Get Code
	String getCodeUrl = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
	// Get Token
	String postResource = "https://www.googleapis.com/oauth2/v4/token";
	String codeQParam = "code";
	String codeValue;
	String clienIdQParam = "client_id";
	String clientIdValue = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
	String clientSecretQParam = "client_secret";
	String clientSecretValue = "erZOWM9g3UtwNRj340YYaK_W";
	String redirectUriQParam = "redirect_uri";
	String redirectUriValue = "https://rahulshettyacademy.com/getCourse.php";
	String grantTypeQParam = "grant_type";
	String grantTypeValue = "authorization_code";
	// Get course
	String getCourse = "getCourse.php";
	
	@Test(priority = 1)
	public void getCode(){
		
		/*System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(getCodeUrl);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("jgerard1986@gmail.com");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Yb5Nmrho!");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
		String partialCode = url.split("code=")[1];
		codeValue = partialCode.split("&scope")[0];*/
		
		// Google is not allowing anymore the FE login to obtain the code, cause of it, we need to do this step manually
		// Suggested steps to follow: Talk with the developer and request to increase the scope limit time of the user code, to be valid for 3 or 4 days.
		// Then every 4 days, manually update the code on the tests.
		codeValue = "4%2F0AX4XfWjvuZCdXCW-jCvYsM2QWf-uvYQeR2xGwSJGoskXhScModsjNyWb6rg0UjccPW6Jtw";
	}
	
	@Test(priority = 2)
	public void getToken(){
		
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().urlEncodingEnabled(false)
				.queryParam(codeQParam, codeValue)
				.queryParam(clienIdQParam, clientIdValue)
				.queryParam(clientSecretQParam, clientSecretValue)
				.queryParam(redirectUriQParam, redirectUriValue)
				.queryParam(grantTypeQParam, grantTypeValue)
		.when().post(postResource)
		.then().assertThat().statusCode(200).extract().response().asString();
		
		// Parse json response in order to get the place_id that comes in the response
		JsonPath jsonResponse = MethodsUtils.rawToJson(response);
		accessTokenValue = jsonResponse.getString("access_token");
	}
	
	@Test(priority = 3)
	public void getCourses(){
		
		// Get courses
		RestAssured.baseURI = baseUrl;
		String response = given().log().all().queryParam(accessTokenQParam, accessTokenValue)
		.when().get(getCourse)
		.then().extract().response().asString();
		
		System.out.println("********************" + response);
	}
	
}
