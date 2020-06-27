package com.online.bookstore.model;

import com.online.bookstore.enums.BookStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Book")
public class Book {
    @Id
    @Indexed(unique = true)
    private String ISBN;
    private String title;
    private String authorId;
    private String description;
    private double price;
    private BookStatus bookStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getISBN(), book.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISBN(), getTitle(), getAuthorId());
    }
}
