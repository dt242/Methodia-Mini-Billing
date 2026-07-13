package com.example.billing.service;

import com.example.billing.config.AppConstants;
import com.example.billing.model.Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InvoiceStorageService {

    private final ObjectMapper objectMapper;

    public InvoiceStorageService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Path outputDirPath = Paths.get(AppConstants.OUTPUT_DIRECTORY);

        if (!Files.exists(outputDirPath)) {
            return invoices;
        }

        try (Stream<Path> paths = Files.walk(outputDirPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(filePath -> {
                        try {
                            Invoice invoice = objectMapper.readValue(filePath.toFile(), Invoice.class);
                            invoices.add(invoice);
                        } catch (IOException e) {
                            System.err.println("Error reading invoice: " + filePath);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException("Error accessing invoice directory", e);
        }

        return invoices;
    }
}