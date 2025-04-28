package com.org.blog.exception;

public class BlogNotFoundException extends RuntimeException {

    public BlogNotFoundException (String message) {
        super(message);
    }
}
