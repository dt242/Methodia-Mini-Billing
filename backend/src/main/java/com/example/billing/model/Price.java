package com.example.billing.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Price(
        ProductType product,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal value
) {}