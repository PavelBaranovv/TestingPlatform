package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.dao.UserDao;
import com.javarush.baranov.testingplatform.util.entities.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import lombok.RequiredArgsConstructor;

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

    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public boolean isExist(String login) {
        User user = userDao.getByLogin(login);
        return user != null;
    }
}
