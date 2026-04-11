package framework.api.tests;

import framework.api.config.Endpoints;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.greaterThan;

public class TestBase {

    protected Response response;
    protected ValidatableResponse validatableResponse;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        RestAssured.baseURI = Endpoints.BASE_URI;

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(greaterThan(0))
                .build();
    }
}
