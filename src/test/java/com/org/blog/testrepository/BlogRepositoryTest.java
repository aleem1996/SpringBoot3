package com.org.blog.testrepository;

import com.org.blog.entity.Blog;
import com.org.blog.repository.BlogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BlogRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    BlogRepository blogRepository;

    @Test
    public void testFindById() {
        Blog blog = new Blog();
        blog.setTitle("Test Title");
        blog.setContent("Test Content");
        blog.setAuthor("Test Author");
        blog.setCreatedAt(LocalDateTime.now());

        Blog savedBlog = testEntityManager.persistAndFlush(blog);
        Optional<Blog> capturedBlog = blogRepository.findById(savedBlog.getId());

        Assertions.assertThat(capturedBlog).isPresent();
        Assertions.assertThat(capturedBlog.get().getContent()).isEqualTo(blog.getContent());
    }

    @Test
    public void testFindAll() {
        Blog blog = new Blog();
        blog.setTitle("Test Title");
        blog.setContent("Test Content");
        blog.setAuthor("Test Author");
        blog.setCreatedAt(LocalDateTime.now());

        Blog blog2 = new Blog();
        blog2.setTitle("Test Title");
        blog2.setContent("Test Content");
        blog2.setAuthor("Test Author");
        blog2.setCreatedAt(LocalDateTime.now());

        Blog savedBlog = testEntityManager.persist(blog);
        Blog savedBlog2 = testEntityManager.persist(blog2);
        testEntityManager.flush();
        List<Blog> capturedBlog = blogRepository.findAll();

        Assertions.assertThat(capturedBlog).hasSize(2);
    }
}
