package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.dao.UserDao;
import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void save(Credentials credentials) {
        userDao.save(credentials);
    }

    public Optional<User> getByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public boolean isExist(String login) {
        Optional<User> user = userDao.findByLogin(login);
        return user.isPresent();
    }
}
