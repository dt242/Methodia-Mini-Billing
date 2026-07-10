package com.example.billing.service;

import com.example.billing.dto.CalculationResult;
import com.example.billing.model.Invoice;
import com.example.billing.model.InvoiceLine;
import com.example.billing.model.Price;
import com.example.billing.model.Reading;
import com.example.billing.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

@Service
public class InvoiceService {

    private final UserService userService;
    private final ReadingService readingService;
    private final PriceService priceService;
    private final BillingCalculatorService calculatorService;
    private final InvoiceNumberGenerator numberGenerator;

    public InvoiceService(UserService userService,
                          ReadingService readingService,
                          PriceService priceService,
                          BillingCalculatorService calculatorService,
                          InvoiceNumberGenerator numberGenerator) {
        this.userService = userService;
        this.readingService = readingService;
        this.priceService = priceService;
        this.calculatorService = calculatorService;
        this.numberGenerator = numberGenerator;
    }

    public Invoice generateInvoice(String reference, YearMonth targetMonth, String product) {
        User user = userService.getUserByReference(reference)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + reference));

        List<Reading> userReadings = readingService.getReadingsByReference(reference);
        List<Price> userPrices = priceService.getPricesByPriceListId(user.priceListId());

        CalculationResult calcResult = calculatorService.calculateForPeriod(
                userReadings, userPrices, targetMonth, product);

        InvoiceLine line = new InvoiceLine(
                1,
                calcResult.quantity(),
                calcResult.startReading().date().toInstant(),
                calcResult.endReading().date().toInstant(),
                product,
                calcResult.price(),
                user.priceListId(),
                calcResult.finalAmount()
        );

        return new Invoice(
                Instant.now(),
                numberGenerator.getNextNumber(),
                user.name(),
                user.reference(),
                targetMonth,
                calcResult.finalAmount(),
                List.of(line)
        );
    }
}