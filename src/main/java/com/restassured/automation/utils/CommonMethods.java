package com.restassured.automation.utils;

import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;

public class CommonMethods {
    
    public static void printResponse(Response response) {
        System.out.println("=== API Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Content Type: " + response.getContentType());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("=====================");
    }
    
    public static String extractJsonValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }
    
    public static Object extractJsonObject(Response response, String jsonPath) {
        return response.jsonPath().get(jsonPath);
    }
    
    public static boolean isStatusCodeSuccess(Response response) {
        return response.getStatusCode() >= 200 && response.getStatusCode() < 300;
    }
    
    public static boolean isStatusCodeError(Response response) {
        return response.getStatusCode() >= 400;
    }
    
    public static boolean isResponseBodyEmpty(Response response) {
        return response.getBody().asString().isEmpty();
    }
    
    public static long getResponseTimeInSeconds(Response response) {
        return response.getTime(TimeUnit.SECONDS);
    }
    
    public static String getHeaderValue(Response response, String headerName) {
        return response.getHeader(headerName);
    }
    
    public static boolean headerExists(Response response, String headerName) {
        return response.getHeader(headerName) != null;
    }
    
    public static boolean jsonPathExists(Response response, String jsonPath) {
        try {
            Object value = response.jsonPath().get(jsonPath);
            return value != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void logStepInfo(String stepName, String description) {
        System.out.println("[STEP] " + stepName + " : " + description);
    }
    
    public static void logTestInfo(String testName, String status) {
        System.out.println("[TEST] " + testName + " - " + status);
    }
    
    public static void logError(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
    }
    
    public static String replacePathParameter(String endpoint, String paramName, Object value) {
        return endpoint.replace("{" + paramName + "}", String.valueOf(value));
    }
    
    public static String buildQueryString(String baseURL, String... params) {
        StringBuilder queryString = new StringBuilder(baseURL + "?");
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) {
                queryString.append("&");
            }
            queryString.append(params[i]).append("=").append(params[i + 1]);
        }
        return queryString.toString();
    }
    
    public static long measureExecutionTime(Runnable testCode) {
        long startTime = System.currentTimeMillis();
        testCode.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
