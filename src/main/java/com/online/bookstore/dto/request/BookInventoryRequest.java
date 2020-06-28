package com.online.bookstore.dto.request;

import com.online.bookstore.enums.BookInventoryRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookInventoryRequest {
    private String isbn;
    private int quantity;
    private BookInventoryRequestStatus bookInventoryRequestStatus;
}
