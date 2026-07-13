package com.example.billing.model;

import java.math.BigDecimal;
import java.time.Instant;

public record InvoiceLine(
        int index,
        BigDecimal quantity,
        Instant lineStart,
        Instant lineEnd,
        ProductType product,
        BigDecimal price,
        int priceList,
        BigDecimal amount
) {}