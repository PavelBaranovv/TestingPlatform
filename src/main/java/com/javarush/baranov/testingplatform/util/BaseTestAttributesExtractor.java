package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.util.entities.BaseTestAttributes;
import jakarta.servlet.http.HttpServletRequest;

public class BaseTestAttributesExtractor {

    public BaseTestAttributes extract(HttpServletRequest req) {
        String name = req.getParameter("test_name");
        String description = req.getParameter("test_description");
        User user = (User) req.getSession().getAttribute("user");

        if (name == null || user == null) {
            throw new NullPointerException("Cannot extract attributes from request");
        }

        return new BaseTestAttributes(name, description, user);
    }
}
