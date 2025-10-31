package com.javarush.baranov.testingplatform.service.auth;

import com.javarush.baranov.testingplatform.util.entities.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.service.UserService;
import com.javarush.baranov.testingplatform.util.CredentialsExtractor;
import com.javarush.baranov.testingplatform.util.validator.CredentialsValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationAttemptsService authenticationAttemptsService;
    private final CredentialsExtractor credentialsExtractor;
    private final CredentialsValidator credentialsValidator;
    private final PasswordEncoder encoder;

    public boolean register(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);

        if (!validateCredentials(req, credentials)) {
            return false;
        }

        if (userService.isExist(credentials.login())) {
            req.setAttribute("error_message", "Такой аккаунт уже существует");
            return false;
        }

        Credentials encodedCredentials = getEncodedCredentials(credentials);

        userService.save(encodedCredentials);
        return true;
    }

    public Role login(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);

        if (authenticationAttemptsService.isBlocked(credentials.login())) {
            req.getSession().setAttribute("error_message", "Слишком много попыток входа. Повторите позже.");
            return null;
        }

        User user = userService.getByLogin(credentials.login());

        if (user == null || !isPasswordCorrect(credentials, user)) {
            req.getSession().setAttribute("error_message", "Неверное имя пользователя или пароль.");
            return null;
        }

        authenticationAttemptsService.resetAttempts(credentials.login());
        req.getSession().setAttribute("user", user);
        return user.getRole();
    }

    private boolean validateCredentials(HttpServletRequest req, Credentials credentials) {
        boolean valid = true;

        List<String> loginViolations = credentialsValidator.getLoginViolations(credentials.login());
        if (!loginViolations.isEmpty()) {
            req.setAttribute("login_violations", loginViolations);
            valid = false;
        }

        List<String> passwordViolations = credentialsValidator.getPasswordViolations(credentials.password());
        if (!passwordViolations.isEmpty()) {
            req.setAttribute("password_violations", passwordViolations);
            valid = false;
        }

        return valid;
    }

    private Credentials getEncodedCredentials(Credentials credentials) {
        String encodedPassword = encoder.encode(credentials.password());
        return new Credentials(credentials.login(), encodedPassword, credentials.role());
    }

    private boolean isPasswordCorrect(Credentials credentials, User user) {
        return encoder.matches(credentials.password(), user.getPassword());
    }
}
