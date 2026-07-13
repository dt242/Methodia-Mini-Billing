package com.example.billing.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Reading(
        String reference,
        ProductType product,
        ZonedDateTime date,
        BigDecimal value
) {}