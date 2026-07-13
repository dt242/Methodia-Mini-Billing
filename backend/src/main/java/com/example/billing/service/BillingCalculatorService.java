package com.example.billing.service;

import com.example.billing.config.AppConstants;
import com.example.billing.dto.CalculationResult;
import com.example.billing.exception.InvalidDataException;
import com.example.billing.exception.ResourceNotFoundException;
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

        Reading startReading = findStartReading(userReadings, targetMonth, product);
        Reading endReading = findEndReading(userReadings, targetMonth, product);

        validateReadings(startReading, endReading);

        BigDecimal quantity = calculateQuantity(startReading, endReading);
        Price activePrice = findActivePrice(userPrices, targetMonth, product);
        BigDecimal finalAmount = calculateAmount(quantity, activePrice);

        return new CalculationResult(quantity, startReading, endReading, activePrice.value(), finalAmount);
    }

    private Reading findStartReading(List<Reading> readings, YearMonth targetMonth, ProductType product) {
        return readings.stream()
                .filter(r -> r.product() == product)
                .filter(r -> r.date().toLocalDate().isBefore(targetMonth.atDay(1)))
                .max(Comparator.comparing(Reading::date))
                .orElseThrow(() -> new InvalidDataException("No start reading found before: " + targetMonth));
    }

    private Reading findEndReading(List<Reading> readings, YearMonth targetMonth, ProductType product) {
        return readings.stream()
                .filter(r -> r.product() == product)
                .filter(r -> !r.date().toLocalDate().isAfter(targetMonth.atEndOfMonth()))
                .max(Comparator.comparing(Reading::date))
                .orElseThrow(() -> new InvalidDataException("No end reading found in or before: " + targetMonth));
    }

    private void validateReadings(Reading startReading, Reading endReading) {
        if (startReading.date().isAfter(endReading.date())) {
            throw new InvalidDataException("Start date cannot be after end date.");
        }
        if (endReading.value().compareTo(startReading.value()) < 0) {
            throw new InvalidDataException("End reading value cannot be less than start reading value.");
        }
    }

    private BigDecimal calculateQuantity(Reading startReading, Reading endReading) {
        return endReading.value()
                .subtract(startReading.value())
                .setScale(AppConstants.QUANTITY_SCALE, RoundingMode.UP);
    }

    private Price findActivePrice(List<Price> prices, YearMonth targetMonth, ProductType product) {
        LocalDate targetMonthStart = targetMonth.atDay(1);
        LocalDate targetMonthEnd = targetMonth.atEndOfMonth();

        return prices.stream()
                .filter(p -> p.product() == product)
                .filter(p -> !p.startDate().isAfter(targetMonthStart) && !p.endDate().isBefore(targetMonthEnd))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No active price found for the target month: " + targetMonth));
    }

    private BigDecimal calculateAmount(BigDecimal quantity, Price activePrice) {
        BigDecimal intermediateAmount = quantity.multiply(activePrice.value())
                .setScale(AppConstants.QUANTITY_SCALE, RoundingMode.UP);
        return intermediateAmount.setScale(AppConstants.AMOUNT_SCALE, RoundingMode.UP);
    }
}