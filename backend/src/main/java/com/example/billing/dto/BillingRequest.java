//package com.example.billing.dto;
//
//import com.example.billing.model.ProductType;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//public record BillingRequest(
//        @NotBlank(message = "Референтният номер е задължителен")
//        String reference,
//
//        @Min(value = 2000, message = "Годината не може да е преди 2000")
//        @Max(value = 2100, message = "Годината не може да е след 2100")
//        int year,
//
//        @Min(value = 1, message = "Месецът трябва да е между 1 и 12")
//        @Max(value = 12, message = "Месецът трябва да е между 1 и 12")
//        int month,
//
//        @NotNull(message = "Продуктът е задължителен")
//        ProductType product
//) {}