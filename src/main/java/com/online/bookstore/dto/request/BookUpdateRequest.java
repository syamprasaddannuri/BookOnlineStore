package com.online.bookstore.dto.request;

import com.online.bookstore.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {
    @NotNull
    private String isbn;
    @NotNull
    private String title;
    @NotNull
    private String author;
    private String description;
    @NotNull
    private double price;
    @NotNull
    private BookStatus bookStatus;
}
