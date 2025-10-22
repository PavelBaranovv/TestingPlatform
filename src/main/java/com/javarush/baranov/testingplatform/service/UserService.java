package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.dao.UserDao;
import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public void save(Credentials credentials) {
        userDao.save(credentials);
    }

    public boolean isExist(String login) {
        return userDao.isExists(login);
    }

    public User get(Credentials credentials) {
        return userDao.get(credentials);
    }
}
