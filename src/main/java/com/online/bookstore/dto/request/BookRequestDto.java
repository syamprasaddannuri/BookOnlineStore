package com.online.bookstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BookRequestDto {
    @JsonProperty(value = "ISBN")
    @NotNull(message = "ISBN cannot be null")
    private String ISBN;

    @JsonProperty(value = "title")
    @NotNull(message = "title cannot be null")
    private String title;

    @JsonProperty(value = "author")
    @NotNull(message = "author cannot be null")
    private String author;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "price")
    @NotNull(message = "price cannot be null")
    private Double price;

    @JsonProperty(value = "status")
    @NotNull(message = "status cannot be null")
    private BookStatus bookStatus;

}
