package com.example.billing.service;

import com.example.billing.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final CsvParserService csvParserService;
    private List<User> usersCache;

    public UserService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @PostConstruct
    public void reloadCache() {
        this.usersCache = csvParserService.loadUsers();
    }

    public Optional<User> getUserByReference(String reference) {
        return usersCache.stream()
                .filter(user -> user.reference().equals(reference))
                .findFirst();
    }
}