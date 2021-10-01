package com.pathmotion;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
import jdk.internal.net.http.common.Log;

import io.restassured.path.json.JsonPath;


public class Homepage {

  // RestAssured.baseURI = "https://search.pathmotion.io";
	String baseURI ="https://search.pathmotion.io";

    @Test
    public void getHitsCount(){
        RequestSpecification request =given();
        Response response = request
  
                        .header("Accept","application/json")
                        .header("Content-Type","application/json")
                        .param("indexes[]","discussions")
                        .param("q","team")
                        .param("limit",15)
                        .param("page", 1)
                        .log().all()
                        .when()
                        .get(baseURI+"/206/search")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();
        
        				//do assertion for data schema
				
        				int hitsArray = response.jsonPath().getList("discussions.hits").size();
        				String category = response.jsonPath().getString("discussions.hits.category[0]");
        				String id = response.jsonPath().getString("discussions.hits.id[0]");
        
        				
        				System.out.println("number of hits: " + hitsArray);
        				System.out.println("first hit category: " + category);
        				System.out.println("first hit id: " + id);
        				
        				//  1. The number of results in 'hits' array
        				Assert.assertEquals(hitsArray /* actual number of results */, 15 /* expected number */);
        				//  2. Verify the 'category' of the first 'hit'
        				Assert.assertEquals(category /* actual first hit category */, "internal career mobility" /* expected first hit category */);
        				//3. Verify the 'id' of the first 'hit'
        				Assert.assertEquals(id /* actual first hit id */, "91008" /* expected first hit id */);
        				
    }
}
