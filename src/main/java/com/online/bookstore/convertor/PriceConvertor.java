package com.online.bookstore.convertor;

import com.online.bookstore.dto.request.PricingRequestDto;
import com.online.bookstore.dto.response.PriceResponseDto;
import com.online.bookstore.model.Pricing;
import org.springframework.stereotype.Component;

@Component
public class PriceConvertor {

    public Pricing convertToPricing (PricingRequestDto pricingRequestDto) {
        Pricing pricing = new Pricing();
        pricing.setISBN(pricingRequestDto.getIsbn());
        pricing.setPrice(pricingRequestDto.getPrice());
        pricing.setDate(pricingRequestDto.getDate());
        return pricing;
    }

    public PriceResponseDto convertToPriceResponseDto (Pricing pricing) {
        PriceResponseDto priceResponseDto = new PriceResponseDto();
        priceResponseDto.setIsbn(pricing.getISBN());
        priceResponseDto.setDate(pricing.getDate());
        priceResponseDto.setPrice(pricing.getPrice());
        return priceResponseDto;
    }
}
