package com.online.bookstore.model;

import com.online.bookstore.enums.BookStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Book")
public class Book {
    @Id
    @NotNull
    private String ISBN;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private String description;

    @NotNull
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
        return Objects.hash(getISBN(), getTitle(), getAuthor());
    }
}
