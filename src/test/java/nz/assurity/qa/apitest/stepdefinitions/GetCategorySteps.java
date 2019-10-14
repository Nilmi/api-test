package nz.assurity.qa.apitest.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import nz.assurity.qa.apitest.util.ConfigurationManager;

import static org.hamcrest.Matchers.*;


/**
 * Defines step definitions for GET categories feature.
 *
 * @author Nilmi
 */
public class GetCategorySteps {

    private Response response;
    private String url = "https://api.myjson.com/bins/1bo2em";


    @When("^A get request sent to the API with parameters \"([^\"]*)\" and \"([^\"]*)\"$")
    public void a_get_request_sent_to_the_API_with_parameters_and(String category, String catalogue) {
        response = RestAssured.when().get(ConfigurationManager.BASE_URL, category, catalogue);
    }

    @Then("^Response status code is (\\d+)$")
    public void response_status_code_is(int statusCode) {
        response.then().statusCode(200);
    }

    @Then("^Name is \"([^\"]*)\"$")
    public void name_is(String categoryName) {
        response.then().body("Name", is(categoryName));
    }

    @Then("^CanRelist is \"([^\"]*)\"$")
    public void canrelist_is(Boolean canRelist) {
        response.then().body("CanRelist", is(canRelist));
    }

    @Then("^There is a Promotions element with Name \"([^\"]*)\"$")
    public void there_is_a_Promotions_element_with_Name(String elementName) {
        response.then().body("Promotions.Name", hasItem(elementName));
    }

    @Then("^Description of \"([^\"]*)\" promotions element contains the \"([^\"]*)\"$")
    public void description_of_promotions_element_contains_the(String elementName, String text) {
        String gPath = String.format("Promotions.find { it.Name == '%s'}.Description", elementName);
        response.then().body(gPath, containsStringIgnoringCase(text));
    }

}
