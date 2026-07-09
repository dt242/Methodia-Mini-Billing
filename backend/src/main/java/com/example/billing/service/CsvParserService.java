package com.example.billing.service;

import com.example.billing.model.Price;
import com.example.billing.model.Reading;
import com.example.billing.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CsvParserService {

    public List<User> loadUsers() {
        return parseFile("data/users.csv", line -> {
            String[] parts = line.split(",");
            return new User(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()));
        });
    }

    public List<Reading> loadReadings() {
        return parseFile("data/readings.csv", line -> {
            String[] parts = line.split(",");
            return new Reading(
                    parts[0].trim(),
                    parts[1].trim(),
                    ZonedDateTime.parse(parts[2].trim()),
                    new BigDecimal(parts[3].trim())
            );
        });
    }

    public List<Price> loadPrices(int priceListId) {
        String fileName = "data/prices-" + priceListId + ".csv";
        return parseFile(fileName, line -> {
            String[] parts = line.split(",");
            return new Price(
                    parts[0].trim(),
                    LocalDate.parse(parts[1].trim()),
                    LocalDate.parse(parts[2].trim()),
                    new BigDecimal(parts[3].trim())
            );
        });
    }

    private <T> List<T> parseFile(String filePath, Function<String, T> mapper) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                return reader.lines()
                        .map(mapper)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}