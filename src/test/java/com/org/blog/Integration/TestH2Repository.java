package com.org.blog.Integration;

import com.org.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Blog, Long> {
}
