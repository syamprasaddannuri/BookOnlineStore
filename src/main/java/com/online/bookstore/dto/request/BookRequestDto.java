package com.online.bookstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    @JsonProperty(value = "ISBN")
    @NotBlank(message = "ISBN cannot be null")
    private String ISBN;

    @JsonProperty(value = "title")
    @NotBlank(message = "title cannot be null")
    private String title;

    @JsonProperty(value = "author")
    @NotBlank(message = "author cannot be null")
    private String author;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "price")
    @NotBlank(message = "price cannot be null")
    private Double price;

}
