package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CredentialsExtractor {

    private final BCryptPasswordEncoder encoder;

    public Credentials extract(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role_str = req.getParameter("role");

        String encodedPassword = password == null ? null : encoder.encode(password);
        Role role = role_str == null ? null : Role.valueOf(role_str.toUpperCase());

        return new Credentials(login, password, role);
    }
}
