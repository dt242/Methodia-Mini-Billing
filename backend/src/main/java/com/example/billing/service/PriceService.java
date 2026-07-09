package com.example.billing.service;

import com.example.billing.model.Price;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private final CsvParserService csvParserService;

    public PriceService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    public List<Price> getPricesByPriceListId(int priceListId) {
        return csvParserService.loadPrices(priceListId);
    }
}