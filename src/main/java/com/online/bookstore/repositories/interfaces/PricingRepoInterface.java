package com.online.bookstore.repositories.interfaces;

import com.online.bookstore.model.Pricing;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepoInterface {

    void updatePriceForGivenBook(Pricing pricing);

    Pricing findByIsbn(String isbn);
}
