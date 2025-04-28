package com.org.blog.repository;

import com.org.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

//    // Native SQL version
//    @Query(value = "SELECT b.author, b.title, b.content, b.created_at, b.edited, a.name " +
//            "FROM blog b JOIN author a ON b.authorId = a.id " +
//            "WHERE a.name = :authorName",
//            nativeQuery = true)
//    List<Object[]> findBlogsByAuthorNameNative(@Param("authorName") String authorName);



//
//    // DTO projection version (recommended)
//    @Query("SELECT new com.org.blog.dto.BlogAuthorDTO(" +
//            "b.author, b.title, b.content, b.createdAt, b.edited, a.name) " +
//            "FROM Blog b JOIN b.authorr a " +
//            "WHERE a.name = :authorName")
//    List<?> findBlogsByAuthorNameDTO(@Param("authorName") String authorName);
}
