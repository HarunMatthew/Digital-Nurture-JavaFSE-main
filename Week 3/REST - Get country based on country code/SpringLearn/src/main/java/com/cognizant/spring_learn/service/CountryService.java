package com.cognizant.spring_learn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cognizant.spring_learn.model.Country;

@Service
public class CountryService {

    // The "countryList" bean is defined in country.xml (loaded via
    // @ImportResource on the main application class) and injected here.
    private final List<Country> countryList;

    public CountryService(@Qualifier("countryList") List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * Returns the country matching the given code, ignoring case.
     * Uses a lambda expression (Stream API) instead of a manual loop.
     */
    public Country getCountry(String code) {
        Optional<Country> match = countryList.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(code))
                .findFirst();

        return match.orElse(null);
    }
}
