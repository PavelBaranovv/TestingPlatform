package com.javarush.baranov.testingplatform.constants;

public interface WebResource {
    String LOGIN_JSP = "/WEB-INF/auth/login.jsp";
    String REGISTRATION_JSP = "/WEB-INF/auth/registration.jsp";

    String STUDENT_HOME_PAGE_JSP = "/WEB-INF/student/home.jsp";
    String STUDENT_SOLVED_JSP = "/WEB-INF/student/solved.jsp";
    String TEST_WELCOME_JSP = "/WEB-INF/student/test_welcome.jsp";
    String TEST_SOLVING_JSP = "/WEB-INF/student/test_solving.jsp";


    String TEACHER_HOME_JSP = "/WEB-INF/teacher/home.jsp";
    String TEACHER_TEST_ATTEMPTS_JSP = "/WEB-INF/teacher/test_attempts.jsp";

    String CREATE_NEW_TEST_JSP = "/WEB-INF/teacher/create/create_new_test.jsp";
    String TEACHER_QUESTION_FILLING_JSP = "/WEB-INF/teacher/create/question_fill.jsp";
    String TEST_SETTINGS_JSP = "/WEB-INF/teacher/create/test_settings.jsp";
    String TEST_CREATION_SUCCESS_JSP = "/WEB-INF/teacher/create/test_creation_success.jsp";


    String TEST_DELETE_CONFIRM_JSP = "/WEB-INF/teacher/test_delete_confirm.jsp";

    String RESULTS_THANKS_PAGE_JSP = "/WEB-INF/results/thanks_page.jsp";
    String RESULTS_ONLY_SCORE_JSP = "/WEB-INF/results/only_score.jsp";
    String RESULTS_ONLY_MISTAKES_JSP = "/WEB-INF/results/only_mistakes.jsp";
    String RESULTS_FULL_JSP = "/WEB-INF/results/full.jsp";
}
