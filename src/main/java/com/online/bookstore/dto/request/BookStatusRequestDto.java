package com.online.bookstore.dto.request;

import com.online.bookstore.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusRequestDto {
    private String isbn;
    private BookStatus bookStatus;
}
