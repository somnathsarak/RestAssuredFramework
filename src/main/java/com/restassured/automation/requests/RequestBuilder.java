package com.restassured.automation.requests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    
    private RequestSpecification request;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    private Object body;
    
    public RequestBuilder() {
        this.request = RestAssured.given();
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
    }
    
    public RequestBuilder setBaseURI(String baseURI) {
        this.request = RestAssured.given().baseUri(baseURI);
        return this;
    }
    
    public RequestBuilder addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }
    
    public RequestBuilder addHeaders(Map<String, String> headerMap) {
        this.headers.putAll(headerMap);
        return this;
    }
    
    public RequestBuilder setContentType(ContentType contentType) {
        this.request = request.contentType(contentType);
        return this;
    }
    
    public RequestBuilder addQueryParam(String key, Object value) {
        this.queryParams.put(key, value);
        return this;
    }
    
    public RequestBuilder addQueryParams(Map<String, Object> queryParamMap) {
        this.queryParams.putAll(queryParamMap);
        return this;
    }
    
    public RequestBuilder setBody(Object body) {
        this.body = body;
        return this;
    }
    
    public RequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }
    
    private RequestSpecification buildRequest() {
        RequestSpecification spec = this.request.headers(this.headers);
        if (!this.queryParams.isEmpty()) {
            spec = spec.queryParams(this.queryParams);
        }
        if (this.body != null) {
            spec = spec.body(this.body);
        }
        return spec;
    }
    
    public Response performGet(String endpoint) {
        return buildRequest().get(endpoint);
    }
    
    public Response performPost(String endpoint) {
        return buildRequest().post(endpoint);
    }
    
    public Response performPut(String endpoint) {
        return buildRequest().put(endpoint);
    }
    
    public Response performPatch(String endpoint) {
        return buildRequest().patch(endpoint);
    }
    
    public Response performDelete(String endpoint) {
        return buildRequest().delete(endpoint);
    }
    
    public Response performHead(String endpoint) {
        return buildRequest().head(endpoint);
    }
    
    public Response performOptions(String endpoint) {
        return buildRequest().options(endpoint);
    }
}
