package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.util.CredentialsExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationAttemptsService authenticationAttemptsService;
    private final CredentialsExtractor credentialsExtractor;

    public boolean register(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);
        if (userService.isExist(credentials.getLogin())) {
            return false;
        }
        userService.save(credentials);
        return true;
    }

    public Role login(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);

        if (authenticationAttemptsService.isBlocked(credentials.getLogin())) {
            req.setAttribute("error_message", "Слишком много попыток входа. Повторите позже.");
            return null;
        }

        User user = userService.get(credentials);

        if (user == null) {
            req.setAttribute("error_message", "Не верные логин или пароль.");
            return null;
        }

        req.getSession().setAttribute("user", user);
        return user.getRole();
    }
}
