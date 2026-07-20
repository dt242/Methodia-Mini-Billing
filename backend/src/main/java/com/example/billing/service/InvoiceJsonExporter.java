//package com.example.billing.service;
//
//import com.example.billing.config.AppConstants;
//import com.example.billing.model.Invoice;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.UncheckedIOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class InvoiceJsonExporter {
//
//    private final ObjectMapper objectMapper;
//
//    public InvoiceJsonExporter(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    public void export(Invoice invoice, String outputDirectory) {
//        try {
//            String folderName = invoice.consumer() + "-" + invoice.reference();
//            Path targetFolder = Paths.get(outputDirectory, folderName);
//            Files.createDirectories(targetFolder);
//
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(AppConstants.FILE_DATE_PATTERN, AppConstants.BG_LOCALE);
//            String datePart = invoice.targetMonth().format(dateFormatter);
//            String fileName = invoice.documentNumber() + "-" + datePart + ".json";
//
//            Path filePath = targetFolder.resolve(fileName);
//            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), invoice);
//
//        } catch (IOException e) {
//            throw new UncheckedIOException("Error writing invoice JSON file to disk", e);
//        }
//    }
//}