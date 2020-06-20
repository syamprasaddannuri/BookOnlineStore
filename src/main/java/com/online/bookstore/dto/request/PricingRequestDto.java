package com.online.bookstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PricingRequestDto {
    @JsonProperty(value = "isbn")
    @NotBlank(message = "isbn cannot be blank")
    private String isbn;
    @JsonProperty(value = "price")
    private double price;
    @JsonProperty("date")
    private Date date;
}
