package com.codecool.oidascriptplatform.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService {
    private static final char DELIMITER = '-';

    public String random() {
        return UUID.randomUUID().toString();
    }

    public String randomWithPrefix(String prefix) {
        return prefix + DELIMITER + random();
    }

    public String randomWithSuffix(String suffix) {
        return random() + DELIMITER + suffix;
    }

    public String randomWithPrefixAndSuffix(String prefix, String suffix) {
        return prefix + DELIMITER + random() + DELIMITER + suffix;
    }
}
