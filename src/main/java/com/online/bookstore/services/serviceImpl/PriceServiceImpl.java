package com.online.bookstore.services.serviceImpl;

import com.online.bookstore.convertor.PriceConvertor;
import com.online.bookstore.dto.request.PricingRequestDto;
import com.online.bookstore.dto.response.PriceResponseDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.model.Pricing;
import com.online.bookstore.repositories.PricingRepo;
import com.online.bookstore.repositories.interfaces.PricingRepoInterface;
import com.online.bookstore.services.PricingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PricingServiceInterface {

    private PricingRepoInterface pricingRepoInterface;
    private PriceConvertor priceConvertor;

    @Autowired
    public PriceServiceImpl(PricingRepoInterface pricingRepoInterface, PriceConvertor priceConvertor) {
        this.pricingRepoInterface = pricingRepoInterface;
        this.priceConvertor = priceConvertor;
    }

    @Override
    public PriceResponseDto updatePriceForGivenBook(PricingRequestDto pricingRequestDto) {
        Pricing pricing = priceConvertor.convertToPricing(pricingRequestDto);
        pricingRepoInterface.updatePriceForGivenBook(pricing);
        return priceConvertor.convertToPriceResponseDto(pricing);
    }

    @Override
    public PriceResponseDto getPriceForBook(String isbn) throws BookNotFoundException {
        Pricing pricing = pricingRepoInterface.findByIsbn(isbn);
        if(pricing == null) {
            throw new BookNotFoundException("Book Not Found For Given Isbn");
        }
        return priceConvertor.convertToPriceResponseDto(pricing);
    }
}
