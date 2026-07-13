package com.example.billing.controller;

import com.example.billing.config.AppConstants;
import com.example.billing.dto.BillingRequest;
import com.example.billing.model.Invoice;
import com.example.billing.service.InvoiceJsonExporter;
import com.example.billing.service.InvoiceService;
import com.example.billing.service.InvoiceStorageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "http://localhost:5173")
public class BillingController {

    private final InvoiceService invoiceService;
    private final InvoiceJsonExporter jsonExporter;
    private final InvoiceStorageService storageService;

    public BillingController(InvoiceService invoiceService,
                             InvoiceJsonExporter jsonExporter,
                             InvoiceStorageService storageService) {
        this.invoiceService = invoiceService;
        this.jsonExporter = jsonExporter;
        this.storageService = storageService;
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> generateInvoice(@Valid @RequestBody BillingRequest request) {
        YearMonth targetMonth = YearMonth.of(request.year(), request.month());
        Invoice invoice = invoiceService.generateInvoice(request.reference(), targetMonth, request.product());
        jsonExporter.export(invoice, AppConstants.OUTPUT_DIRECTORY);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = storageService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
}