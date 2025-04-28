package com.org.blog.controllertest;


import com.org.blog.controller.BlogController;
import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @InjectMocks
    private BlogController blogController;

    private BlogResponse blogResponse;

    private BlogRequest blogRequest;


    @BeforeEach
    void setup() {
        blogResponse = new BlogResponse();
        blogResponse.setId(1L);
        blogResponse.setTitle("Test Title");
        blogResponse.setContent("Test Content");
        blogResponse.setAuthor("Test Author");
        blogResponse.setCreatedAt(LocalDateTime.now());

        blogRequest = new BlogRequest();
        blogRequest.setTitle("Test Title");
        blogRequest.setContent("Test Content");
        blogRequest.setAuthor("Test Author");

    }

    @Test
    public void whenCreateBlog_thenReturnCreatedResponse() {
        // given
        when(blogService.createBlog(any(BlogRequest.class))).thenReturn(blogResponse);

        // when
        ResponseEntity<BlogResponse> response = blogController.createdBlog(blogRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(blogService, times(1)).createBlog(any(BlogRequest.class));
    }

    @Test
    public void whenGetAllBlogs_thenReturnOkResponse() {
        // given
        BlogResponse blogResponse2 = new BlogResponse();
        blogResponse2.setId(2L);
        blogResponse2.setTitle("Title 2");
        blogResponse2.setContent("Content 2");
        blogResponse2.setAuthor("Author 2");
        blogResponse2.setCreatedAt(LocalDateTime.now());

        List<BlogResponse> responses = Arrays.asList(blogResponse, blogResponse2);
        when(blogService.findAllBlogs()).thenReturn(responses);

        // when
        ResponseEntity<List<BlogResponse>> response = blogController.findAllBlogs();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        verify(blogService, times(1)).findAllBlogs();
    }

    @Test
    public void whenGetBlogById_thenReturnOkResponse() {
        // given
        when(blogService.getBlogById(1L)).thenReturn(blogResponse);

        // when
        ResponseEntity<BlogResponse> response = blogController.findBlogById(1L);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(blogService, times(1)).getBlogById(1L);
    }

    @Test
    public void whenUpdateBlog_thenReturnOkResponse() {
        // given
        when(blogService.updateBlog(anyLong(), any(BlogRequest.class))).thenReturn(blogResponse);

        // when
        ResponseEntity<BlogResponse> response = blogController.updateBlog(1L, blogRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(blogService, times(1)).updateBlog(anyLong(), any(BlogRequest.class));
    }

    @Test
    public void whenDeleteBlog_thenReturnNoContentResponse() {
        // given
        doNothing().when(blogService).deleteBlog(1L);

        // when
        ResponseEntity<Void> response = blogController.deleteBlog(1L);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(blogService, times(1)).deleteBlog(1L);
    }

}