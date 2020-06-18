package com.online.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaData)) return false;
        MediaData mediaData = (MediaData) o;
        return Objects.equals(getUserId(), mediaData.getUserId()) &&
                Objects.equals(getId(), mediaData.getId()) &&
                Objects.equals(getTitle(), mediaData.getTitle()) &&
                Objects.equals(getBody(), mediaData.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getId(), getTitle(), getBody());
    }
}
