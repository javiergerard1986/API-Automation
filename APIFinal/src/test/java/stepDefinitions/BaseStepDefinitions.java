package stepDefinitions;

import config.APIResources;
import config.PropertiesLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import logger.Logger;

public abstract class BaseStepDefinitions {

	protected RequestSpecification requestSpec;
	protected ResponseSpecification responseSpec;
	protected Response response;
	private Logger logger = Logger.getInstance();
	private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
	
	protected void buildRequestSpecification() {
		this.requestSpec = new RequestSpecBuilder()
				.setBaseUri(this.propertiesLoader.getGlobalValue("baseUrl"))
				.addQueryParam(this.propertiesLoader.getGlobalValue("keyQP"), this.propertiesLoader.getGlobalValue("keyValue"))
				.addFilter(RequestLoggingFilter.logRequestTo(this.logger.getPrintStream()))
				.addFilter(ResponseLoggingFilter.logResponseTo(this.logger.getPrintStream()))
				.setContentType(ContentType.JSON).build();	
	}
	
	protected void buildResponseSpecification(int statusCode) {
		this.responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
	}
	
	protected void setResponse(APIResources resource, String httpMethod) {
		if(httpMethod.equals("post")) {
			this.response = this.requestSpec
					 .when().post(resource.getResource())
					 .then().spec(this.responseSpec).extract().response();
		} else if(httpMethod.equals("get")) {
			this.response = this.requestSpec
					 .when().get(resource.getResource())
					 .then().spec(this.responseSpec).extract().response();
		} else {
			this.response = this.requestSpec
					 .when().delete(resource.getResource())
					 .then().spec(this.responseSpec).extract().response();
		}
			
	}

}
