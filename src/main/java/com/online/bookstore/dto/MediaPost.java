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
public class MediaPost {

    @JsonProperty(value = "userId")
    private Integer authorId;

    @JsonProperty(value = "id")
    private Integer postId;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "body")
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaPost)) return false;
        MediaPost mediaPost = (MediaPost) o;
        return Objects.equals(getAuthorId(), mediaPost.getAuthorId()) &&
                Objects.equals(getPostId(), mediaPost.getPostId()) &&
                Objects.equals(getTitle(), mediaPost.getTitle()) &&
                Objects.equals(getContent(), mediaPost.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorId(), getPostId(), getTitle(), getContent());
    }
}
