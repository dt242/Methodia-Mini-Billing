package com.example.billing.dto;

import com.example.billing.model.ProductType;

public record BillingRequest(
        String reference,
        int year,
        int month,
        ProductType product
) {}