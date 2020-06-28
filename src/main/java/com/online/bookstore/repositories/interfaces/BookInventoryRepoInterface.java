package com.online.bookstore.repositories.interfaces;

import com.online.bookstore.model.BookInventory;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInventoryRepoInterface {

    BookInventory save(BookInventory bookInventory);

    BookInventory findByISBN(String isbn);
}
