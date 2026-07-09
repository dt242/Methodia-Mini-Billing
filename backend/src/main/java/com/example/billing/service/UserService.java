package com.example.billing.service;

import com.example.billing.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final CsvParserService csvParserService;

    public UserService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    public List<User> getAllUsers() {
        return csvParserService.loadUsers();
    }

    public Optional<User> getUserByReference(String reference) {
        return getAllUsers().stream()
                .filter(user -> user.reference().equals(reference))
                .findFirst();
    }
}