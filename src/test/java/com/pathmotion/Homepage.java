package com.pathmotion;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;




public class Homepage {


	String baseURI ="https://search.pathmotion.io";

    @Test
    public void getDiscussionHitCounts(){
        RequestSpecification request = given();
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
       		
        		int hitsArray = response.jsonPath().getList("discussions.hits").size();
        		String category = response.jsonPath().getString("discussions.hits.category[0]");
        		String id = response.jsonPath().getString("discussions.hits.id[0]");
        		int statusCode = response.getStatusCode();
        				
        		Assert.assertEquals(statusCode /*actual status code*/, 200/*expected statusCode*/, "Correct status code returned");
        		response.then().assertThat().body(matchesJsonSchemaInClasspath("jsonSchema/homepageSearchValidData.json"));
        	
        		System.out.println("number of hits: " + hitsArray);
        		System.out.println("first hit category: " + category);
        		System.out.println("first hit id: " + id);
        				
        		//1. The number of results in 'hits' array
        		Assert.assertEquals(hitsArray /* actual number of results */, 15 /* expected number */);
        		//2. Verify the 'category' of the first 'hit'
        		Assert.assertEquals(category /* actual first hit category */, "internal career mobility" /* expected first hit category */);
        		//3. Verify the 'id' of the first 'hit'
        		Assert.assertEquals(id /* actual first hit id */, "91008" /* expected first hit id */);
        				
    }
}
