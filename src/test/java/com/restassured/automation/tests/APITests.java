package com.restassured.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.restassured.automation.base.BaseTest;
import io.restassured.response.Response;

@Listeners(com.restassured.automation.listeners.TestNGListener.class)
public class APITests extends BaseTest {
    
    @Test(description = "Get all posts and verify status code")
    public void testGetAllPosts() {
        Response response = sendGetRequest("/posts");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }
    
    @Test(description = "Get a specific post by ID")
    public void testGetPostById() {
        Response response = sendGetRequest("/posts/1");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().get("id"), "Post ID should not be null");
    }
    
    @Test(description = "Create a new post")
    public void testCreatePost() {
        String requestBody = "{ \"title\": \"Test Post\", \"body\": \"This is a test post\", \"userId\": 1 }";
        Response response = sendPostRequest("/posts", requestBody);
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 for creation");
    }
    
    @Test(description = "Update an existing post")
    public void testUpdatePost() {
        String requestBody = "{ \"title\": \"Updated Post\", \"body\": \"Updated content\", \"userId\": 1 }";
        Response response = sendPutRequest("/posts/1", requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 for update");
    }
    
    @Test(description = "Delete a post")
    public void testDeletePost() {
        Response response = sendDeleteRequest("/posts/1");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 for deletion");
    }
}
