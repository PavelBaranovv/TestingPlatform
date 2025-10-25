package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.util.entities.Credentials;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CredentialsExtractor {

    public Credentials extract(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role_str = req.getParameter("role");
        Role role = role_str == null ? null : Role.valueOf(role_str.toUpperCase());

        return new Credentials(login, password, role);
    }
}
