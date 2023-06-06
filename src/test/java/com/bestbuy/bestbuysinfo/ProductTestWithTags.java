package com.bestbuy.bestbuysinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.testbase.ProductTestBase;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Test;

public class ProductTestWithTags extends ProductTestBase {
    @WithTags({@WithTag("productfeature:SMOKE"),
            @WithTag("productfeature:POSITIVE")})
    @Title("This test will verify if a status code of 200 is returned for GET request")
    @Test
    public void verifyIfTheStatusCodeIs200() {
        SerenityRest.rest()
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .log().all();
    }

    @WithTag("productfeature:NEGATIVE")

    @Title("Provide a 500 status code when incorrect HTTP method is used to access resource")
    @Test
    public void invalidMethod() {
        SerenityRest.rest()
                .given()
                .when()
                .post(EndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(500)
                .log().all();
    }

    @WithTags({@WithTag("productfeature:SMOKE"),
            @WithTag("productfeature:NEGATIVE")})
    @Title("This test will provide an error code of 404 when user tries to access an invalid resource")
    @Test
    public void inCorrectResource() {
        SerenityRest.rest()
                .given()
                .when()
                .get("/products123")
                .then()
                .statusCode(404)
                .log().all();
    }
}
