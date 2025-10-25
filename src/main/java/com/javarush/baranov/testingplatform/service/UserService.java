package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.dao.UserDao;
import com.javarush.baranov.testingplatform.util.entities.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void save(Credentials credentials) {
        String login = credentials.login();
        String password = credentials.password();
        Role role = credentials.role();
        User user = new User(login, password, role);
        userDao.save(user);
    }

    public Optional<User> getByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public boolean isExist(String login) {
        Optional<User> user = userDao.findByLogin(login);
        return user.isPresent();
    }
}
