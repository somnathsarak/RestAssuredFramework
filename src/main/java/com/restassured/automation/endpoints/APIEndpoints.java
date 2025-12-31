package com.restassured.automation.endpoints;

public class APIEndpoints {
    
    // Base URLs
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    
    // Posts Endpoints
    public static final String GET_ALL_POSTS = "/posts";
    public static final String GET_POST_BY_ID = "/posts/{id}";
    public static final String CREATE_POST = "/posts";
    public static final String UPDATE_POST = "/posts/{id}";
    public static final String DELETE_POST = "/posts/{id}";
    public static final String GET_POST_COMMENTS = "/posts/{id}/comments";
    
    // Users Endpoints
    public static final String GET_ALL_USERS = "/users";
    public static final String GET_USER_BY_ID = "/users/{id}";
    public static final String CREATE_USER = "/users";
    public static final String UPDATE_USER = "/users/{id}";
    public static final String DELETE_USER = "/users/{id}";
    public static final String GET_USER_POSTS = "/users/{id}/posts";
    public static final String GET_USER_ALBUMS = "/users/{id}/albums";
    
    // Comments Endpoints
    public static final String GET_ALL_COMMENTS = "/comments";
    public static final String GET_COMMENT_BY_ID = "/comments/{id}";
    public static final String CREATE_COMMENT = "/comments";
    public static final String UPDATE_COMMENT = "/comments/{id}";
    public static final String DELETE_COMMENT = "/comments/{id}";
    public static final String GET_POST_COMMENTS_BY_ID = "/comments?postId={postId}";
    
    // Albums Endpoints
    public static final String GET_ALL_ALBUMS = "/albums";
    public static final String GET_ALBUM_BY_ID = "/albums/{id}";
    public static final String CREATE_ALBUM = "/albums";
    public static final String UPDATE_ALBUM = "/albums/{id}";
    public static final String DELETE_ALBUM = "/albums/{id}";
    public static final String GET_ALBUM_PHOTOS = "/albums/{id}/photos";
    
    // Photos Endpoints
    public static final String GET_ALL_PHOTOS = "/photos";
    public static final String GET_PHOTO_BY_ID = "/photos/{id}";
    public static final String CREATE_PHOTO = "/photos";
    public static final String UPDATE_PHOTO = "/photos/{id}";
    public static final String DELETE_PHOTO = "/photos/{id}";
    
    // Todos Endpoints
    public static final String GET_ALL_TODOS = "/todos";
    public static final String GET_TODO_BY_ID = "/todos/{id}";
    public static final String CREATE_TODO = "/todos";
    public static final String UPDATE_TODO = "/todos/{id}";
    public static final String DELETE_TODO = "/todos/{id}";
    public static final String GET_USER_TODOS = "/users/{userId}/todos";
}
