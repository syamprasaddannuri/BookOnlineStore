package com.online.bookstore.dto.response;

import com.online.bookstore.enums.BookStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDto {
    private String ISBN;
    private String title;
    private String author;
    private String description;
    private double price;
    private BookStatus bookStatus;
}
