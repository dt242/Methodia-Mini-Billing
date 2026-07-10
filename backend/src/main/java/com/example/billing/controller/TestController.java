package com.example.billing.controller;

import com.example.billing.dto.CalculationResult;
import com.example.billing.model.Price;
import com.example.billing.model.Reading;
import com.example.billing.model.User;
import com.example.billing.service.BillingCalculatorService;
import com.example.billing.service.PriceService;
import com.example.billing.service.ReadingService;
import com.example.billing.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserService userService;
    private final ReadingService readingService;
    private final PriceService priceService;
    private final BillingCalculatorService calculatorService;

    public TestController(UserService userService, ReadingService readingService, PriceService priceService, BillingCalculatorService calculatorService) {
        this.userService = userService;
        this.readingService = readingService;
        this.priceService = priceService;
        this.calculatorService = calculatorService;
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

    @GetMapping("/calculate")
    public CalculationResult testCalculation() {
        List<Reading> userReadings = readingService.getReadingsByReference("123456789");
        List<Price> userPrices = priceService.getPricesByPriceListId(1);
        return calculatorService.calculateForPeriod(userReadings, userPrices, YearMonth.of(2025, 3), "gas");
    }
}