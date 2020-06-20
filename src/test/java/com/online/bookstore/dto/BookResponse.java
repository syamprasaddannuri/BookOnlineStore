package com.online.bookstore.dto;

import com.online.bookstore.dto.response.BookResponseDto;
import lombok.*;

@Data
public class BookResponse {

    public static BookResponseDto getBookResponseData() {
        com.online.bookstore.dto.response.BookResponseDto bookResponseDto = com.online.bookstore.dto.response.BookResponseDto.builder()
                .ISBN("10")
                .title("Physics")
                .author("Ramesh")
                .description("hjasgdhjaskjlasijdkajdh")
                .build();
        return bookResponseDto;
    }

}
