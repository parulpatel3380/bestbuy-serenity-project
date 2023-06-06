package com.bestbuy.bestbuysinfo;

import com.bestbuy.bestbuysteps.ProductsSteps;
import com.bestbuy.testbase.ProductTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUD extends ProductTestBase {
    static String name = "Duracell - AAA Batteries (4-Pack)" + TestUtils.getRandomValue();

    static String type = "HardGood" + TestUtils.getRandomValue();

    static Double price= 5.49;

    static String upc="041333424019";

    static double shipping = 0;

    static String description = "Compatible with select electronic devices";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";

    static String image= "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";

    static int productId;
    @Steps
    ProductsSteps productsSteps ;

    @Title("This will create a new product record")
    @Test
    public void test001()
    {
        ValidatableResponse response = productsSteps.createProduct( name ,  type, price , upc, shipping ,  description ,  manufacturer ,  model ,  url,  image).log().all().statusCode(201);
        productId=response.extract().path("id");


    }

    @Title("Verify if product is added or not")
    @Test
    public void test002()
    {
        HashMap<String,Object> productMap = productsSteps.getProductById(productId);
        Assert.assertThat(productMap,hasValue(productId));
        // productId=(int) productMap.get("id");
        System.out.println("Product Id " + productId);
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003()
    {
        name = name + TestUtils.getRandomValue();
        productsSteps.updateProduct(productId,name ,  type, price , upc, shipping ,  description ,  manufacturer ,  model ,  url,  image)
                .statusCode(200);


        HashMap<String , Object> productMap = productsSteps.getProductById(productId);
        Assert.assertThat(productMap,hasValue(name));
    }

    @Title("Delete the product and verify if the product is removed")
    @Test
    public void test004()
    {
        productsSteps.deleteProduct(productId)
                .statusCode(200);

        productsSteps.deleteProduct(productId)
                .statusCode(404);

    }
}
