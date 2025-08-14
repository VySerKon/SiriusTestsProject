package api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.containsString;

public class AuthSpec {

   public static ResponseSpecification responseAuthSpec = new ResponseSpecBuilder()
           .expectHeader("Location", containsString("/login/"))
           .build();
}
