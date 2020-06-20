package com.online.bookstore.controller;

import com.online.bookstore.constants.URIEndpoints;
import com.online.bookstore.dto.request.PricingRequestDto;
import com.online.bookstore.exception.BookNotFoundException;
import com.online.bookstore.services.PricingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIEndpoints.PRICING_API)
public class PricingController {

    private PricingServiceInterface pricingServiceInterface;

    @Autowired
    public PricingController(PricingServiceInterface pricingServiceInterface) {
        this.pricingServiceInterface = pricingServiceInterface;
    }

    @PostMapping
    public ResponseEntity updatePrice(@RequestBody PricingRequestDto pricingRequestDto) {
        return ResponseEntity.ok(pricingServiceInterface.updatePriceForGivenBook(pricingRequestDto));
    }

    @GetMapping
    public ResponseEntity getPriceForBook(@RequestParam ("ISBN") String isbn) throws BookNotFoundException {
        return ResponseEntity.ok(pricingServiceInterface.getPriceForBook(isbn));
    }
}
