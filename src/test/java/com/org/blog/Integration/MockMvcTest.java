package com.org.blog.Integration;

import com.org.blog.controller.BlogController;
import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import com.org.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BlogController.class)
class MockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BlogService blogService;

    @MockBean
    private CacheManager cacheManager; // Mock cache manager

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetBlogById() throws Exception {
        // Given
        BlogResponse mockResponse = new BlogResponse();
        mockResponse.setId(1L);
        mockResponse.setTitle("Test Title");
        mockResponse.setContent("Test Content");
        mockResponse.setAuthor("Test Author");

        given(blogService.getBlogById(anyLong())).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/blog/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.author").value("Test Author"));

        // Verify cache interaction if needed
        Mockito.verify(blogService).getBlogById(1L);
    }

    @Test
    void testCreateBlog() throws Exception {
        // Given
        BlogRequest request = new BlogRequest();
        request.setTitle("New Blog");
        request.setContent("New Content");
        request.setAuthor("New Author");

        BlogResponse mockResponse = new BlogResponse();
        mockResponse.setId(1L);
        mockResponse.setTitle("New Blog");
        mockResponse.setContent("New Content");
        mockResponse.setAuthor("New Author");

        given(blogService.createBlog(any(BlogRequest.class))).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/blog/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("New Blog"))
                .andExpect(jsonPath("$.content").value("New Content"))
                .andExpect(jsonPath("$.author").value("New Author"));

        // Verify cache eviction if needed (for example if creating invalidates some cache)
    }

    @Test
    void testUpdateBlog() throws Exception {
        // Given
        BlogRequest request = new BlogRequest();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");
        request.setAuthor("Updated Author");

        BlogResponse mockResponse = new BlogResponse();
        mockResponse.setId(1L);
        mockResponse.setTitle("Updated Title");
        mockResponse.setContent("Updated Content");
        mockResponse.setAuthor("Updated Author");

        given(blogService.updateBlog(anyLong(), any(BlogRequest.class))).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(put("/api/blog/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"))
                .andExpect(jsonPath("$.author").value("Updated Author"));

        // Verify cache eviction for the updated blog
        Mockito.verify(blogService).updateBlog(1L, request);
    }

    @Test
    void testDeleteBlog() throws Exception {
        // Given
        Mockito.doNothing().when(blogService).deleteBlog(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/blog/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify cache eviction for the deleted blog
        Mockito.verify(blogService).deleteBlog(1L);
    }
}