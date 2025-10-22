package com.javarush.baranov.testingplatform.entity;
import com.javarush.baranov.testingplatform.enums.Role;
import lombok.Data;

@Data
public class Credentials {
    private final String login;
    private final String password;
    private final Role role;
}
