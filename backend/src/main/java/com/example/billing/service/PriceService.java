//package com.example.billing.service;
//
//import com.example.billing.model.Price;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class PriceService {
//
//    private final CsvParserService csvParserService;
//    private final Map<Integer, List<Price>> priceCache = new ConcurrentHashMap<>();
//
//    public PriceService(CsvParserService csvParserService) {
//        this.csvParserService = csvParserService;
//    }
//
//    public List<Price> getPricesByPriceListId(int priceListId) {
//        return priceCache.computeIfAbsent(priceListId, csvParserService::loadPrices);
//    }
//
//    public void reloadCache() {
//        priceCache.clear();
//    }
//}