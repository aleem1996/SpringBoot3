package com.org.blog.controller;

import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity<BlogResponse> createdBlog(@RequestBody @Valid BlogRequest blogRequest) {
        BlogResponse blogResponse = blogService.createBlog(blogRequest);

        return new ResponseEntity<>(blogResponse, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> findBlogById(@PathVariable Long id) {
        BlogResponse blogResponse = blogService.getBlogById(id);

        return ResponseEntity.ok(blogResponse);

    }

    @GetMapping("/findall")
    public ResponseEntity<List<BlogResponse>> findAllBlogs() {
        List<BlogResponse> blogResponse = blogService.findAllBlogs();

        return ResponseEntity.ok(blogResponse);

    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequest blogRequest) {
        BlogResponse blogResponse = blogService.updateBlog(id, blogRequest);

        return ResponseEntity.ok(blogResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();

    }
}
