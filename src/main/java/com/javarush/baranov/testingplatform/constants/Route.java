package com.javarush.baranov.testingplatform.constants;

public interface Route {
    String LOGIN = "/login";
    String REGISTRATION = "/registration";

    String STUDENT = "/student";
    String STUDENT_HOME = STUDENT + "/home";
    String STUDENT_TEST = STUDENT + "/test";
    String STUDENT_SOLVED = STUDENT + "/solved";

    String TEACHER = "/teacher";
    String TEACHER_HOME = TEACHER + "/home";

    String TEACHER_CREATE_TEST = TEACHER + "/create-test";
    String TEACHER_CREATE_TEST_NEW = TEACHER_CREATE_TEST + "/new";
    String TEACHER_CREATE_TEST_QUESTIONS = TEACHER_CREATE_TEST + "/questions";
    String TEACHER_CREATE_TEST_SETTINGS = TEACHER_CREATE_TEST +"/settings";
    String TEACHER_CREATE_TEST_SUCCESS = TEACHER_CREATE_TEST + "/success";

    String TEACHER_TEST_RESULTS = TEACHER + "/results";
    String DELETE_TEST = TEACHER + "/delete-test";

}
