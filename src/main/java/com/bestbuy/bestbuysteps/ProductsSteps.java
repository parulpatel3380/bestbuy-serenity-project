package com.bestbuy.bestbuysteps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import java.util.HashMap;

public class ProductsSteps {
    @Step("Create product with name : {0} ,type :{1} , price : {2},upc : {3} , shipping : {4} , description : {5} , manufacturer : {6} , model : {7} , url : {8} , image : {9} ")
    public ValidatableResponse createProduct(String name , String type, double price , String upc, double shipping , String description , String manufacturer , String model , String url, String image   )
    {
        ProductPojo productPojo = ProductPojo.getProductPojo( name ,  type, price , upc, shipping ,  description ,  manufacturer ,  model ,  url,  image);


        return   SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting the product with name : {0}")
    public HashMap<String,Object> getProductInfoByName(String name)
    {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);

    }
    @Step("Updating product with productId : {0},name : {1} ,type :{2} , price : {3},upc : {4} , shipping : {5} , description : {6} , manufacturer : {7} , model : {8} , url : {9} , image : {10} ")
    public ValidatableResponse updateProduct(int productId,String name , String type, double price , String upc, double shipping , String description , String manufacturer , String model , String url, String image   )
    {
        ProductPojo productPojo = ProductPojo.getProductPojo( name ,  type, price , upc, shipping ,  description ,  manufacturer ,  model ,  url,  image);


        return   SerenityRest.given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .pathParam("productID",productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Deleting product information with productID : {0}")
    public ValidatableResponse deleteProduct(int productId)
    {
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productID",productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();

    }
    @Step("Getting the product with product ID = {0}")
    public HashMap<String,Object> getProductById(int productId)
    {
        HashMap<String, Object> productMap =  SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productID",productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("");
        return  productMap;

    }

    @Step("Getting all the user info ")
    public ValidatableResponse getUsertInfo()
    {
        return  SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 61aafcb694ad2a184e92adcee5aae71f2288cf87d88930daf6a8b86ae21da215")
                .when().get(EndPoints.GET_ALL_PRODUCTS)
                .then();
    }

}
