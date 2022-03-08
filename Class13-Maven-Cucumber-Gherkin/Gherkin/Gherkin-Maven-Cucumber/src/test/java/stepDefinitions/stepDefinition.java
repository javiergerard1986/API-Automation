package stepDefinitions;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepDefinition {

	@Given("^User is on NetBanking landing page$")
	public void user_is_on_NetBanking_landing_page() {
		System.out.println("Writting: User is on NetBanking Landing page");
	}
	
	@When("^User login into application with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_login_into_application_with_username_and_password(String username, String password){	
		System.out.println("Writting: User login into appliaction with " + username + " and " + password + " password");
	}
	
	@Then("^Home page is populated$")
	public void home_page_is_populated() {
		System.out.println("Writting: Home page is populated");
	}
	
	@And("^Cards displayed are \"([^\"]*)\"$")
	public void cards_are_displayed(String areDisplayed) {
		System.out.println("Writting: Cards are Displayed: " + areDisplayed);
	}
	
}
