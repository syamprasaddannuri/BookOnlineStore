package com.online.bookstore.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private String ISBN;
    private String title;
    private String author;
    private String description;
}
