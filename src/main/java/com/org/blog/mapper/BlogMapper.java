package com.org.blog.mapper;

import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.entity.Blog;

public class BlogMapper {

    public static Blog dtoToBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setAuthor(blogRequest.getAuthor());
        return blog;
    }

    public static BlogResponse blogToDto(Blog blog) {
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setTitle(blog.getTitle());
        blogResponse.setContent(blog.getContent());
        blogResponse.setAuthor(blog.getAuthor());
        blogResponse.setId(blog.getId());
        blogResponse.setCreatedAt(blog.getCreatedAt());
        return blogResponse;
    }
}
