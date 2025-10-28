package com.javarush.baranov.testingplatform.util;

public class TestIdExtractor {
    public String extract(String uri) {
        String[] parts = uri.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }
}
