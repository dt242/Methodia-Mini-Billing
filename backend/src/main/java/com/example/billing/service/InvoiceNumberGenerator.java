package com.example.billing.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceNumberGenerator {

    private final AtomicInteger currentNumber = new AtomicInteger(10000);

    public String getNextNumber() {
        return String.valueOf(currentNumber.getAndIncrement());
    }
}