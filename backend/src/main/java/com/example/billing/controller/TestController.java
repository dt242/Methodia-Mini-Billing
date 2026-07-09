package com.example.billing.controller;

import com.example.billing.model.Price;
import com.example.billing.model.Reading;
import com.example.billing.model.User;
import com.example.billing.service.CsvParserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final CsvParserService csvParserService;

    public TestController(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return csvParserService.loadUsers();
    }

    @GetMapping("/readings")
    public List<Reading> getReadings() {
        return csvParserService.loadReadings();
    }

    @GetMapping("/prices")
    public List<Price> getPrices() {
        return csvParserService.loadPrices(1);
    }
}