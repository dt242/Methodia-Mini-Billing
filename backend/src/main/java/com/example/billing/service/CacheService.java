//package com.example.billing.service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class CacheService {
//
//    private final UserService userService;
//    private final ReadingService readingService;
//    private final PriceService priceService;
//
//    public CacheService(UserService userService, ReadingService readingService, PriceService priceService) {
//        this.userService = userService;
//        this.readingService = readingService;
//        this.priceService = priceService;
//    }
//
//    public void reloadAll() {
//        userService.reloadCache();
//        readingService.reloadCache();
//        priceService.reloadCache();
//    }
//}