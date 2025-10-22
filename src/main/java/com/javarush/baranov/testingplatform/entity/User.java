package com.javarush.baranov.testingplatform.entity;

import com.javarush.baranov.testingplatform.enums.Role;
import lombok.Data;

@Data
public class User {
    private final Role role;
}
