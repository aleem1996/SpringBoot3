package com.org.blog.Integration;

import com.org.blog.dtos.BlogRequest;
import com.org.blog.dtos.BlogResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogIT {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/blog";
    }

    @Test
    @Sql(statements = "INSERT INTO blog (id, title, content, author, created_at) VALUES (1, 'TestProject', 'Integration Content', 'Test Author', CURRENT_TIMESTAMP)",
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM blog WHERE id=1",
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    void testFindBlogById() {
        // When
        BlogResponse response = testRestTemplate.getForObject(baseUrl + "/{id}", BlogResponse.class, 1);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("TestProject");
        assertThat(response.getContent()).isEqualTo("Integration Content");
        assertThat(response.getAuthor()).isEqualTo("Test Author");
        assertThat(response.getCreatedAt()).isNotNull();
    }

    // ADDED: Test for creating a blog
    @Test
    @Sql(statements = "DELETE FROM blog WHERE title = 'New Blog Title'", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateBlog() {
        // Given
        BlogRequest request = new BlogRequest();
        request.setTitle("New Blog Title");
        request.setContent("New Blog Content");
        request.setAuthor("New Blog Author");

        // When
        ResponseEntity<BlogResponse> response = testRestTemplate.postForEntity(baseUrl + "/create", request, BlogResponse.class);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        BlogResponse blogResponse = response.getBody();
        assertThat(blogResponse).isNotNull();
        assertThat(blogResponse.getTitle()).isEqualTo("New Blog Title");
        assertThat(blogResponse.getContent()).isEqualTo("New Blog Content");
        assertThat(blogResponse.getAuthor()).isEqualTo("New Blog Author");
        assertThat(blogResponse.getId()).isNotNull();
    }

    // ADDED: Test for updating a blog
//    @Test
//    @Sql(statements = "INSERT INTO blog (id, title, content, author, created_at) VALUES (2, 'Old Title', 'Old Content', 'Old Author', CURRENT_TIMESTAMP)",
//            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM blog WHERE id=2",
//            executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
//    void testUpdateBlog() {
//        // Given
//        BlogRequest request = new BlogRequest();
//        request.setTitle("Updated Title");
//        request.setContent("Updated Content");
//        request.setAuthor("Updated Author");
//
//        HttpEntity<BlogRequest> entity = new HttpEntity<>(request);
//
//        // When
//        ResponseEntity<BlogResponse> response = testRestTemplate.exchange(
//                baseUrl + "/{id}",
//                HttpMethod.PUT,
//                entity,
//                BlogResponse.class,
//                2
//        );
//
//        // Then
//        assertThat(response.getStatusCodeValue()).isEqualTo(200);
//        BlogResponse blogResponse = response.getBody();
//        assertThat(blogResponse).isNotNull();
//        assertThat(blogResponse.getId()).isEqualTo(2L);
//        assertThat(blogResponse.getTitle()).isEqualTo("Updated Title");
//        assertThat(blogResponse.getContent()).isEqualTo("Updated Content");
//        assertThat(blogResponse.getAuthor()).isEqualTo("Updated Author");
//    }
}
