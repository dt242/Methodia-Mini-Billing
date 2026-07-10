package com.example.billing.controller;

import com.example.billing.dto.BillingRequest;
import com.example.billing.model.Invoice;
import com.example.billing.service.InvoiceJsonExporter;
import com.example.billing.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "http://localhost:5173")
public class BillingController {

    private final InvoiceService invoiceService;
    private final InvoiceJsonExporter jsonExporter;

    public BillingController(InvoiceService invoiceService, InvoiceJsonExporter jsonExporter) {
        this.invoiceService = invoiceService;
        this.jsonExporter = jsonExporter;
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> generateInvoice(@RequestBody BillingRequest request) {
        YearMonth targetMonth = YearMonth.of(request.year(), request.month());
        Invoice invoice = invoiceService.generateInvoice(request.reference(), targetMonth, request.product());
        jsonExporter.export(invoice, "output");
        return ResponseEntity.ok(invoice);
    }
}