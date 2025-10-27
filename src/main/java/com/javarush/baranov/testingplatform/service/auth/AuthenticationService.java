package com.javarush.baranov.testingplatform.service.auth;

import com.javarush.baranov.testingplatform.util.entities.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.service.UserService;
import com.javarush.baranov.testingplatform.util.CredentialsExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationAttemptsService authenticationAttemptsService;
    private final CredentialsExtractor credentialsExtractor;
    private final PasswordEncoder encoder;

    public boolean register(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);

        if (userService.isExist(credentials.login())) {
            return false;
        }

        Credentials encodedCredentials = getEncodedCredentials(credentials);

        userService.save(encodedCredentials);
        return true;
    }

    public Role login(HttpServletRequest req) {
        Credentials credentials = credentialsExtractor.extract(req);

        if (authenticationAttemptsService.isBlocked(credentials.login())) {
            req.getSession().setAttribute("login_error", "Слишком много попыток входа. Повторите позже.");
            return null;
        }

        Optional<User> optionalUser = userService.getByLogin(credentials.login());

        if (optionalUser.isEmpty() ||
                !isPasswordCorrect(credentials, optionalUser.get())) {
            req.getSession().setAttribute("login_error", "Неверное имя пользователя или пароль.");
            return null;
        }

        authenticationAttemptsService.resetAttempts(credentials.login());
        req.getSession().setAttribute("user", optionalUser.get());
        return optionalUser.get().getRole();
    }


    private Credentials getEncodedCredentials(Credentials credentials) {
        String encodedPassword = encoder.encode(credentials.password());
        return new Credentials(credentials.login(), encodedPassword, credentials.role());
    }

    private boolean isPasswordCorrect(Credentials credentials, User user) {
        return encoder.matches(credentials.password(), user.getPassword());
    }
}
