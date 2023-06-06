package com.bestbuy.bestbuysinfo;

import com.bestbuy.bestbuysteps.StoresSteps;
import com.bestbuy.testbase.StoreTestBase;
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
public class StoreCRUD extends StoreTestBase {
    static int storeID;

    static String name = "Sally" + TestUtils.getRandomValue();
    static String type = "BigBang" + TestUtils.getRandomValue();
    static String address =  TestUtils.getRandomValue() + " ,Random Street" ;
    static String address2 = "Grove Street";
    static String City = "London" ;
    static String state = "England" ;
    static String zip = "445665";
    static double lat = 60.5;
    static double lng = -50.56;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    @Steps
    StoresSteps storeSteps;

    @Title("This will create a new store")
    @Test
    public  void test001(){
        ValidatableResponse response=storeSteps.createNewStore(name,type,address,address2,City,state,zip,lat,lng,hours);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("Verify If the store was Added to the application")
    @Test
    public void test002(){
        HashMap <String,Object> storeMap= storeSteps.getStoreInfoById(storeID);
        Assert.assertThat(storeMap, hasValue(storeID));


    }


    @Title("Update the Store information and Verify the Updated information")
    @Test
    public  void test003(){
        name=name + TestUtils.getRandomValue();
        storeSteps.updateStore(storeID,name,type,address,address2,City,state,zip,lat,lng,hours)
                .statusCode(200);
        HashMap<String,Object> storeMap= storeSteps.getStoreInfoById(storeID);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        storeSteps.deleteStore(storeID)
                .statusCode(200);

    }
}
