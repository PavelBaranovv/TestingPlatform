package com.javarush.baranov.testingplatform.util.entities;


import com.javarush.baranov.testingplatform.entity.User;

public record BaseTestAttributes(
        String name,
        String description,
        User createdBy
) {}
