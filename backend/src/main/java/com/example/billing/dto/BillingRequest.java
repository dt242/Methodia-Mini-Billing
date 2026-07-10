package com.example.billing.dto;

public record BillingRequest(
        String reference,
        int year,
        int month,
        String product
) {}