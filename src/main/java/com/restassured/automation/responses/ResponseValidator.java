package com.restassured.automation.responses;

import io.restassured.response.Response;
import org.testng.Assert;

public class ResponseValidator {
    
    private Response response;
    
    public ResponseValidator(Response response) {
        this.response = response;
    }
    
    public ResponseValidator validateStatusCode(int expectedCode) {
        Assert.assertEquals(response.getStatusCode(), expectedCode, 
            "Expected status code " + expectedCode + " but got " + response.getStatusCode());
        return this;
    }
    
    public ResponseValidator validateStatusCodeIsOk() {
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());
        return this;
    }
    
    public ResponseValidator validateStatusCodeIsCreated() {
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 but got " + response.getStatusCode());
        return this;
    }
    
    public ResponseValidator validateContentType(String expectedType) {
        String actualType = response.getContentType();
        Assert.assertTrue(actualType.contains(expectedType), 
            "Expected content type to contain " + expectedType + " but got " + actualType);
        return this;
    }
    
    public ResponseValidator validateResponseTimeIsBelowMs(long maxTimeMs) {
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < maxTimeMs, 
            "Response time " + responseTime + "ms is greater than " + maxTimeMs + "ms");
        return this;
    }
    
    public ResponseValidator validateJsonPath(String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        Assert.assertEquals(actualValue, expectedValue, 
            "Expected " + jsonPath + " to be " + expectedValue + " but got " + actualValue);
        return this;
    }
    
    public ResponseValidator validateJsonPathExists(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "JSON path " + jsonPath + " does not exist in response");
        return this;
    }
    
    public ResponseValidator validateJsonPathNotNull(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "JSON path " + jsonPath + " should not be null");
        return this;
    }
    
    public ResponseValidator validateXmlPath(String xmlPath, String expectedValue) {
        String actualValue = response.xmlPath().getString(xmlPath);
        Assert.assertEquals(actualValue, expectedValue, 
            "Expected " + xmlPath + " to be " + expectedValue + " but got " + actualValue);
        return this;
    }
    
    public ResponseValidator validateHeaderExists(String headerName) {
        String headerValue = response.getHeader(headerName);
        Assert.assertNotNull(headerValue, "Header " + headerName + " does not exist in response");
        return this;
    }
    
    public ResponseValidator validateHeader(String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assert.assertEquals(actualValue, expectedValue, 
            "Expected header " + headerName + " to be " + expectedValue + " but got " + actualValue);
        return this;
    }
    
    public ResponseValidator validateBodyNotEmpty() {
        String body = response.getBody().asString();
        Assert.assertFalse(body.isEmpty(), "Response body is empty");
        return this;
    }
    
    public ResponseValidator validateBodyContains(String text) {
        String body = response.getBody().asString();
        Assert.assertTrue(body.contains(text), "Response body does not contain " + text);
        return this;
    }
    
    public Response getResponse() {
        return response;
    }
}
