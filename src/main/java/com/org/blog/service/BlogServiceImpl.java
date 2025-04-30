package com.org.blog.service;

import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.entity.Blog;
import com.org.blog.exception.BlogNotFoundException;
import com.org.blog.mapper.BlogMapper;
import com.org.blog.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "blogs")
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    @Override
    public BlogResponse createBlog(BlogRequest blogRequest) {

        Blog blog = BlogMapper.dtoToBlog(blogRequest);
        Blog savedBlog = blogRepository.save(blog);
        return BlogMapper.blogToDto(savedBlog);

    }

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("not found"));
        return BlogMapper.blogToDto(blog);
    }

    @Override
    @Cacheable(key = "'all'", unless = "#result.isEmpty()")
    public List<BlogResponse> findAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(BlogMapper::blogToDto)
                .toList();
    }

    @Override
    @CachePut(key = "#id")
    @CacheEvict(key = "'all'") // Evict the all-blogs cache when updating a single blog
    public BlogResponse updateBlog(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() ->
                        new BlogNotFoundException("blog against this id not found"));

        blog.setTitle(blogRequest.getTitle());
        blog.setAuthor(blogRequest.getAuthor());
        blog.setContent(blogRequest.getContent());
        Blog updatedBlog = blogRepository.save(blog);

        return BlogMapper.blogToDto(updatedBlog);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "#id"),
            @CacheEvict(key = "'all'")
    })
    public void deleteBlog(Long id){
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            log.error("blog did not exist with that id");
            throw new BlogNotFoundException("Blog already not in db");
        }
    }
}
