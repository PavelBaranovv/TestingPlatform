package com.javarush.baranov.testingplatform.util.validator;

import java.util.ArrayList;
import java.util.List;

public class CredentialsValidator {
    public List<String> getLoginViolations(String login) {
        List<String> violations = new ArrayList<>();

        if (login == null) {
            violations.add("Логин не задан");
            return violations;
        }

        if (login.length() < 4) {
            violations.add("Длина логина не менее 4 символов");
        }

        if (!login.matches("^[a-zA-Zа-яА-Я0-9_]+$")) {
            violations.add("Логин может содержать только буквы, цифры и символ нижнего подчеркивания");
        }

        return violations;
    }

    public List<String> getPasswordViolations(String password) {
        List<String> violations = new ArrayList<>();

        if (password == null) {
            violations.add("Пароль не задан");
            return violations;
        }

        if (password.length() < 8) {
            violations.add("Длина пароля не менее 8 символов");
        }

        if (!password.matches(".*[A-ZА-Я].*")) {
            violations.add("Пароль должен содержать хотя бы одну заглавную букву");
        }

        if (!password.matches(".*\\d.*")) {
            violations.add("Пароль должен содержать хотя бы одну цифру");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            violations.add("Пароль должен содержать хотя бы один специальный символ");
        }

        return violations;
    }
}
