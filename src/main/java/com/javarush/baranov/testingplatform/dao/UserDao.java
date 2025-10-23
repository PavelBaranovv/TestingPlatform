package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;

public class UserDao {
    public void save(Credentials credentials) {
        //TODO: UserDao::save
        System.out.println("Save " + credentials.getRole() + " with login: " + credentials.getLogin() + " and password: " + credentials.getPassword());
    }

    public boolean isExists(String login) {
        //TODO: UserDao::isExists
        System.out.println("Check if " + login + " exists");
        return false;
    }

    public User get(Credentials credentials) {
        //TODO: UserDao::get
        System.out.println("Get " + credentials.getLogin() + " " + credentials.getPassword());
        return new User("123", "123", Role.TEACHER);
    }
}
