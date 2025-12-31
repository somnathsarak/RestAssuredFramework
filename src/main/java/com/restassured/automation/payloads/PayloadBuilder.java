package com.restassured.automation.payloads;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;

public class PayloadBuilder {
    
    private JSONObject payload;
    private ObjectMapper objectMapper;
    
    public PayloadBuilder() {
        this.payload = new JSONObject();
        this.objectMapper = new ObjectMapper();
    }
    
    public PayloadBuilder addProperty(String key, Object value) {
        payload.put(key, value);
        return this;
    }
    
    public PayloadBuilder addString(String key, String value) {
        payload.put(key, value);
        return this;
    }
    
    public PayloadBuilder addNumber(String key, Number value) {
        payload.put(key, value);
        return this;
    }
    
    public PayloadBuilder addBoolean(String key, Boolean value) {
        payload.put(key, value);
        return this;
    }
    
    public PayloadBuilder addNull(String key) {
        payload.put(key, JSONObject.NULL);
        return this;
    }
    
    public PayloadBuilder addArray(String key, Object... values) {
        payload.put(key, values);
        return this;
    }
    
    public PayloadBuilder addNestedObject(String key, JSONObject nestedObject) {
        payload.put(key, nestedObject);
        return this;
    }
    
    public PayloadBuilder merge(JSONObject otherPayload) {
        for (String key : otherPayload.keySet()) {
            payload.put(key, otherPayload.get(key));
        }
        return this;
    }
    
    public PayloadBuilder clear() {
        payload = new JSONObject();
        return this;
    }
    
    public JSONObject build() {
        return payload;
    }
    
    public String buildAsString() {
        return payload.toString();
    }
    
    public String buildAsFormattedString() {
        return payload.toString(4);
    }
    
    public <T> T buildAsObject(Class<T> clazz) {
        try {
            return objectMapper.readValue(payload.toString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting payload to object: " + e.getMessage());
        }
    }
    
    public JSONObject getPayload() {
        return payload;
    }
    
    public static PayloadBuilder createPostPayload(String title, String body, int userId) {
        return new PayloadBuilder()
                .addString("title", title)
                .addString("body", body)
                .addNumber("userId", userId);
    }
    
    public static PayloadBuilder createUserPayload(String name, String email, String phone) {
        return new PayloadBuilder()
                .addString("name", name)
                .addString("email", email)
                .addString("phone", phone);
    }
}
