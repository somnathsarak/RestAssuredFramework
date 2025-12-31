package com.restassured.automation.client;

import com.restassured.automation.endpoints.APIEndpoints;
import com.restassured.automation.payloads.PayloadBuilder;
import com.restassured.automation.requests.RequestBuilder;
import com.restassured.automation.responses.ResponseValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class APIClient {
    
    private RequestBuilder requestBuilder;
    private String baseURI;
    
    public APIClient() {
        this.baseURI = APIEndpoints.BASE_URL;
        this.requestBuilder = new RequestBuilder().setBaseURI(baseURI);
    }
    
    public APIClient(String customBaseURI) {
        this.baseURI = customBaseURI;
        this.requestBuilder = new RequestBuilder().setBaseURI(customBaseURI);
    }
    
    public ResponseValidator getRequest(String endpoint) {
        Response response = requestBuilder.performGet(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator postRequest(String endpoint, Object payload) {
        requestBuilder.setBody(payload);
        Response response = requestBuilder.performPost(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator postRequest(String endpoint, JSONObject payload) {
        requestBuilder.setBody(payload.toString());
        requestBuilder.setContentType(ContentType.JSON);
        Response response = requestBuilder.performPost(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator postRequest(String endpoint, PayloadBuilder payloadBuilder) {
        return postRequest(endpoint, payloadBuilder.build());
    }
    
    public ResponseValidator putRequest(String endpoint, Object payload) {
        requestBuilder.setBody(payload);
        Response response = requestBuilder.performPut(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator putRequest(String endpoint, JSONObject payload) {
        requestBuilder.setBody(payload.toString());
        requestBuilder.setContentType(ContentType.JSON);
        Response response = requestBuilder.performPut(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator putRequest(String endpoint, PayloadBuilder payloadBuilder) {
        return putRequest(endpoint, payloadBuilder.build());
    }
    
    public ResponseValidator patchRequest(String endpoint, Object payload) {
        requestBuilder.setBody(payload);
        Response response = requestBuilder.performPatch(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator deleteRequest(String endpoint) {
        Response response = requestBuilder.performDelete(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator headRequest(String endpoint) {
        Response response = requestBuilder.performHead(endpoint);
        return new ResponseValidator(response);
    }
    
    public ResponseValidator optionsRequest(String endpoint) {
        Response response = requestBuilder.performOptions(endpoint);
        return new ResponseValidator(response);
    }
    
    public APIClient addHeader(String key, String value) {
        requestBuilder.addHeader(key, value);
        return this;
    }
    
    public APIClient setContentType(ContentType contentType) {
        requestBuilder.setContentType(contentType);
        return this;
    }
    
    public APIClient addQueryParam(String key, Object value) {
        requestBuilder.addQueryParam(key, value);
        return this;
    }
    
    public String getBaseURI() {
        return baseURI;
    }
    
    public void setBaseURI(String newBaseURI) {
        this.baseURI = newBaseURI;
        this.requestBuilder = new RequestBuilder().setBaseURI(newBaseURI);
    }
}
