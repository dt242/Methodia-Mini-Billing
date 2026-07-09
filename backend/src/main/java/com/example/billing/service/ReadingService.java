package com.example.billing.service;

import com.example.billing.model.Reading;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingService {

    private final CsvParserService csvParserService;

    public ReadingService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    public List<Reading> getAllReadings() {
        return csvParserService.loadReadings();
    }

    public List<Reading> getReadingsByReference(String reference) {
        return getAllReadings().stream()
                .filter(reading -> reading.reference().equals(reference))
                .toList();
    }
}