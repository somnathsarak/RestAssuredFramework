package com.restassured.automation.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import com.restassured.automation.config.ConfigFileReader;

public class BaseTest {
    protected static ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();
    protected ConfigFileReader configReader;
    
    @BeforeMethod
    public void setUp() {
        configReader = new ConfigFileReader();
        String baseURI = configReader.getBaseURI();
        RestAssured.baseURI = baseURI;
        
        requestSpec.set(RestAssured.given());
    }
    
    @AfterMethod
    public void tearDown() {
        requestSpec.remove();
    }
    
    protected RequestSpecification getRequestSpec() {
        return requestSpec.get();
    }
    
    protected Response sendGetRequest(String endpoint) {
        return getRequestSpec().get(endpoint).then().extract().response();
    }
    
    protected Response sendPostRequest(String endpoint, Object body) {
        return getRequestSpec().body(body).post(endpoint).then().extract().response();
    }
    
    protected Response sendPutRequest(String endpoint, Object body) {
        return getRequestSpec().body(body).put(endpoint).then().extract().response();
    }
    
    protected Response sendDeleteRequest(String endpoint) {
        return getRequestSpec().delete(endpoint).then().extract().response();
    }
}
