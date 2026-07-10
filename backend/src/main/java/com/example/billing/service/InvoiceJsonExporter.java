package com.example.billing.service;

import com.example.billing.model.Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class InvoiceJsonExporter {

    private final ObjectMapper objectMapper;

    public InvoiceJsonExporter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public Path export(Invoice invoice, String outputDirectory) {
        try {
            String folderName = invoice.consumer() + "-" + invoice.reference();
            Path targetFolder = Paths.get(outputDirectory, folderName);
            Files.createDirectories(targetFolder);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("LLLL-yy", Locale.of("bg", "BG"));
            String datePart = invoice.targetMonth().format(dateFormatter);
            String fileName = invoice.documentNumber() + "-" + datePart + ".json";

            Path filePath = targetFolder.resolve(fileName);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), invoice);

            return filePath;

        } catch (IOException e) {
            throw new java.io.UncheckedIOException("Error writing invoice JSON file to disk", e);
        }
    }
}