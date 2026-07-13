package com.example.billing.service;

import com.example.billing.dto.CalculationResult;
import com.example.billing.model.Price;
import com.example.billing.model.ProductType;
import com.example.billing.model.Reading;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

@Service
public class BillingCalculatorService {

    public CalculationResult calculateForPeriod(
            List<Reading> userReadings,
            List<Price> userPrices,
            YearMonth targetMonth,
            ProductType product) {

        Reading startReading = userReadings.stream()
                .filter(r -> r.product() == product)
                .filter(r -> r.date().toLocalDate().isBefore(targetMonth.atDay(1)))
                .max(Comparator.comparing(Reading::date))
                .orElseThrow(() -> new IllegalArgumentException("No start reading found before: " + targetMonth));

        Reading endReading = userReadings.stream()
                .filter(r -> r.product() == product)
                .filter(r -> !r.date().toLocalDate().isAfter(targetMonth.atEndOfMonth()))
                .max(Comparator.comparing(Reading::date))
                .orElseThrow(() -> new IllegalArgumentException("No end reading found in or before: " + targetMonth));

        if (startReading.equals(endReading)) {
            throw new IllegalArgumentException("Start and end readings are the same.");
        }

        BigDecimal quantity = endReading.value()
                .subtract(startReading.value())
                .setScale(3, RoundingMode.UP);

        LocalDate targetMonthStart = targetMonth.atDay(1);
        LocalDate targetMonthEnd = targetMonth.atEndOfMonth();

        Price activePrice = userPrices.stream()
                .filter(p -> p.product().equals(product))
                .filter(p -> !p.startDate().isAfter(targetMonthStart)
                        && !p.endDate().isBefore(targetMonthEnd))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active price found for the target month: " + targetMonth));

        BigDecimal intermediateAmount = quantity.multiply(activePrice.value())
                .setScale(3, RoundingMode.UP);

        BigDecimal finalAmount = intermediateAmount.setScale(2, RoundingMode.UP);

        return new CalculationResult(
                quantity,
                startReading,
                endReading,
                activePrice.value(),
                finalAmount
        );
    }
}