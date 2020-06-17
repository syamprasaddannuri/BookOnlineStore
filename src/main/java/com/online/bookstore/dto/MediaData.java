package com.online.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaData {

    @JsonProperty(value = "userId")
    private Integer userId;

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "body")
    private String body;
}
