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
    @NotBlank(message = "ISBN cannot be blank")
    private String ISBN;

    @JsonProperty(value = "title")
    @NotBlank(message = "title cannot be blank")
    private String title;

    @JsonProperty(value = "author")
    @NotBlank(message = "author name cannot be blank")
    private String author;

    @JsonProperty(value = "description")
    private String description;

}
