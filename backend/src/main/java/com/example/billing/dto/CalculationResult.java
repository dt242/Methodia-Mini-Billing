package com.example.billing.dto;

import com.example.billing.model.Reading;
import java.math.BigDecimal;

public record CalculationResult(
        BigDecimal quantity,
        Reading startReading,
        Reading endReading,
        BigDecimal price,
        BigDecimal finalAmount
) {}