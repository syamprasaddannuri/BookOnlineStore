package com.online.bookstore.dto;

import com.online.bookstore.model.Book;
import com.online.bookstore.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedBooks {

    private List<Book> bookList;

    private Long count;

    private Pagination pagination;
}
