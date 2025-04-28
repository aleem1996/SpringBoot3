package com.org.blog.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

    @NotNull(message = "Null title not allowed")
    private String title;

    @NotNull(message = "Null content not allowed")
    private String content;

    @NotNull(message = "Null message not allowed")
    private String author;
}
