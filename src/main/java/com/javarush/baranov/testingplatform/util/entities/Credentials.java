package com.javarush.baranov.testingplatform.util.entities;

import com.javarush.baranov.testingplatform.enums.Role;

public record Credentials(
        String login,
        String password,
        Role role
) {}
