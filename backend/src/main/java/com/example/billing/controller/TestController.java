package com.example.billing.controller;

import com.example.billing.model.Price;
import com.example.billing.model.Reading;
import com.example.billing.model.User;
import com.example.billing.service.PriceService;
import com.example.billing.service.ReadingService;
import com.example.billing.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserService userService;
    private final ReadingService readingService;
    private final PriceService priceService;

    public TestController(UserService userService, ReadingService readingService, PriceService priceService) {
        this.userService = userService;
        this.readingService = readingService;
        this.priceService = priceService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/readings")
    public List<Reading> getReadings() {
        return readingService.getReadingsByReference("123456789");
    }

    @GetMapping("/prices")
    public List<Price> getPrices() {
        return priceService.getPricesByPriceListId(1);
    }
}