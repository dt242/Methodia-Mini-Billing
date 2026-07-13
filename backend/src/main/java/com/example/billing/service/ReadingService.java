package com.example.billing.service;

import com.example.billing.model.Reading;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingService {

    private final CsvParserService csvParserService;
    private List<Reading> readingsCache;

    public ReadingService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @PostConstruct
    public void reloadCache() {
        this.readingsCache = csvParserService.loadReadings();
    }

    public List<Reading> getReadingsByReference(String reference) {
        return readingsCache.stream()
                .filter(reading -> reading.reference().equals(reference))
                .toList();
    }
}