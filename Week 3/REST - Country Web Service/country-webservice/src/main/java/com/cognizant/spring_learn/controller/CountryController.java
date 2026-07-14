package com.cognizant.spring_learn.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Service exposing a static list of countries.
 *
 * Sample requests:
 *   GET http://localhost:8083/countries
 *   GET http://localhost:8083/countries/IN
 */
@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = "http://localhost:4200") // allows an Angular dev server to call this API
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    // Static in-memory data - hardcoded directly in the controller for this exercise
    private final List<Country> countryList = Arrays.asList(
            new Country("IN", "India", "New Delhi", "Indian Rupee"),
            new Country("US", "United States", "Washington, D.C.", "US Dollar"),
            new Country("GB", "United Kingdom", "London", "Pound Sterling"),
            new Country("JP", "Japan", "Tokyo", "Japanese Yen"),
            new Country("AU", "Australia", "Canberra", "Australian Dollar")
    );

    // GET http://localhost:8083/countries
    @GetMapping
    public List<Country> getAllCountries() {
        logger.info("getAllCountries() - start");

        logger.info("getAllCountries() - end, returned {} countries", countryList.size());
        return countryList;
    }

    // GET http://localhost:8083/countries/{code}
    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        logger.info("getCountryByCode() - start, code={}", code);

        Optional<Country> match = countryList.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(code))
                .findFirst();

        logger.info("getCountryByCode() - end, found={}", match.isPresent());
        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Country data model, kept in this same file (package-private) so the
     * whole service lives in a single .java file.
     */
    static class Country {

        private String code;
        private String name;
        private String capital;
        private String currency;

        public Country() {
        }

        public Country(String code, String name, String capital, String currency) {
            this.code = code;
            this.name = name;
            this.capital = capital;
            this.currency = currency;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
