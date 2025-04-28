package com.org.blog.service;

import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;

import java.util.List;

public interface BlogService {

    BlogResponse createBlog(BlogRequest blogRequest);
    BlogResponse getBlogById(Long id);
    List<BlogResponse> findAllBlogs();
    BlogResponse updateBlog(Long id, BlogRequest blogRequest);
    void deleteBlog(Long id);
}
