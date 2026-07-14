package com.cognizant.spring_learn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.spring_learn.model.Country;
import com.cognizant.spring_learn.service.CountryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // allows an Angular dev server to call this API
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // GET http://localhost:8083/countries/{code}
    // e.g. http://localhost:8083/countries/in -> { "code": "IN", "name": "India" }
    @GetMapping("/countries/{code}")
    public ResponseEntity<Country> getCountry(@PathVariable String code) {
        logger.info("getCountry() - start, code={}", code);

        Country country = countryService.getCountry(code);

        logger.info("getCountry() - end, found={}", country != null);

        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }
}
