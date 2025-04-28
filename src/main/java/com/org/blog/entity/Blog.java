package com.org.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime edited;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "authorId", nullable = false, updatable = false)
//    private Author authorr;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate() {
        edited = LocalDateTime.now();

    }


}
