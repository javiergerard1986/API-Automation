package stepDefinitions;

import config.PropertiesLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import logger.Logger;

public abstract class BaseStepDefinitions {

	protected RequestSpecification requestSpec;
	protected ResponseSpecification responseSpec;
	private Logger logger = Logger.getInstance();
	protected PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
	
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

}
