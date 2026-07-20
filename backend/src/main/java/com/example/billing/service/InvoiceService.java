//package com.example.billing.service;
//
//import com.example.billing.dto.CalculationResult;
//import com.example.billing.exception.ResourceNotFoundException;
//import com.example.billing.model.Invoice;
//import com.example.billing.model.InvoiceLine;
//import com.example.billing.model.Price;
//import com.example.billing.model.ProductType;
//import com.example.billing.model.Reading;
//import com.example.billing.model.User;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//import java.time.YearMonth;
//import java.util.List;
//
//@Service
//public class InvoiceService {
//
//    private final UserService userService;
//    private final ReadingService readingService;
//    private final PriceService priceService;
//    private final BillingCalculatorService calculatorService;
//    private final InvoiceNumberGenerator numberGenerator;
//
//    public InvoiceService(UserService userService, ReadingService readingService,
//                          PriceService priceService, BillingCalculatorService calculatorService,
//                          InvoiceNumberGenerator numberGenerator) {
//        this.userService = userService;
//        this.readingService = readingService;
//        this.priceService = priceService;
//        this.calculatorService = calculatorService;
//        this.numberGenerator = numberGenerator;
//    }
//
//    public Invoice generateInvoice(String reference, YearMonth targetMonth, ProductType product) {
//        User user = getUser(reference);
//        List<Reading> userReadings = readingService.getReadingsByReference(reference);
//        List<Price> userPrices = priceService.getPricesByPriceListId(user.priceListId());
//
//        CalculationResult calcResult = calculatorService.calculateForPeriod(userReadings, userPrices, targetMonth, product);
//
//        InvoiceLine line = createInvoiceLine(calcResult, product, user.priceListId());
//
//        return buildInvoice(user, targetMonth, calcResult.finalAmount(), List.of(line));
//    }
//
//    private User getUser(String reference) {
//        return userService.getUserByReference(reference)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + reference));
//    }
//
//    private InvoiceLine createInvoiceLine(CalculationResult calcResult, ProductType product, int priceListId) {
//        return new InvoiceLine(
//                1,
//                calcResult.quantity(),
//                calcResult.startReading().date().toInstant(),
//                calcResult.endReading().date().toInstant(),
//                product,
//                calcResult.price(),
//                priceListId,
//                calcResult.finalAmount()
//        );
//    }
//
//    private Invoice buildInvoice(User user, YearMonth targetMonth, BigDecimal finalAmount, List<InvoiceLine> lines) {
//        return new Invoice(
//                Instant.now(),
//                numberGenerator.getNextNumber(),
//                user.name(),
//                user.reference(),
//                targetMonth,
//                finalAmount,
//                lines
//        );
//    }
//}