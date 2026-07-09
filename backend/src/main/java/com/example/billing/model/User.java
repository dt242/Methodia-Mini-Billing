package com.example.billing.model;

public record User(
        String name,
        String reference,
        int priceListId
) {}