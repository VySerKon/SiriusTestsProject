package api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;


public class BaseSpec {

    public static RequestSpecification requestSpec = with()
            .log().uri()
            .log().headers()
            .log().body()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8");

    public static ResponseSpecification redirectSpec = new ResponseSpecBuilder()
            .expectStatusCode(302)
            .log(ALL)
            .build();
}
