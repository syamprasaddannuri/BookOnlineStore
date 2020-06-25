package com.online.bookstore.services;

import com.online.bookstore.dto.request.PricingRequestDto;
import com.online.bookstore.dto.response.PriceResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface PricingServiceInterface {
    PriceResponseDto updatePriceForGivenBook(PricingRequestDto pricingRequestDto);

    PriceResponseDto getPriceForBook(String isbn) throws BookNotFoundException;
}
