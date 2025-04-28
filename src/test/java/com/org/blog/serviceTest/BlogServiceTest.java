package com.org.blog.serviceTest;


import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.entity.Blog;
import com.org.blog.exception.BlogNotFoundException;
import com.org.blog.repository.BlogRepository;
import com.org.blog.service.BlogServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    BlogRepository blogRepository;

    @InjectMocks
    BlogServiceImpl blogService;

    public Blog blog;

    public BlogRequest blogRequest;

    @BeforeEach
    void setup() {
        blog = new Blog();
        blog.setTitle("asa");
        blog.setContent("asa");
        blog.setAuthor("asa");
        blog.setId(1L);

        blogRequest = new BlogRequest();
        blogRequest.setTitle("asa");
        blogRequest.setContent("asa");
        blogRequest.setAuthor("asa");
    }

    @Test
    public void testCreateBlog() {

        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        BlogResponse blogResponse = blogService.createBlog(blogRequest);

        Assertions.assertWith(blogResponse.getId()).isEqualTo(blog.getId());

        Assertions.assertWith(blogResponse).isNotNull();

        verify(blogRepository, times(1)).save(any(Blog.class));


    }

    @Test
    public void testGetAllBlogs() {

        Blog blog1 = new Blog();
        blog.setTitle("asa");
        blog.setContent("asa");
        blog.setAuthor("asa");
        blog.setId(2L);

        when(blogRepository.findAll()).thenReturn(Arrays.asList(blog, blog1));

        List<BlogResponse> blogResponse = blogService.findAllBlogs();

        Assertions.assertWith(blogResponse).isNotNull();

        Assertions.assertThat(blogResponse).hasSize(2);

        verify(blogRepository, times(1)).findAll();

    }

    @Test
    public void testGetBlogById() {

        when(blogRepository.findById(1L)).thenReturn(Optional.ofNullable(blog));

        BlogResponse blogResponse = blogService.getBlogById(1L);

        Assertions.assertWith(blogResponse.getId()).isEqualTo(blog.getId());

        Assertions.assertWith(blogResponse).isNotNull();

        verify(blogRepository, times(1)).findById(1L);

    }

    @Test
    public void testDeleteBlog() {

        when(blogRepository.existsById(1L)).thenReturn(Boolean.TRUE);
        doNothing().when(blogRepository).deleteById(1L);
        blogService.deleteBlog(1L);

        verify(blogRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testDeleteNotExist() {

        when(blogRepository.existsById(99L)).thenReturn(Boolean.FALSE);

        Assertions.assertThatThrownBy(() -> blogService.deleteBlog(99L))
                .isInstanceOf(BlogNotFoundException.class)
                .hasMessage("Blog already not in db");


    }

    @Test
    public void testUpdateBlog() {

        when(blogRepository.findById(1L)).thenReturn(Optional.ofNullable(blog));

        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        BlogResponse blogResponse = blogService.updateBlog(1L, blogRequest);

        Assertions.assertThat(blogResponse).isNotNull();



    }

}
