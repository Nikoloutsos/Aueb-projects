package com.example.studytracker.view.login.signin;

interface LoginView {
    void onSuccessfulLogin();

    void onCreateAnAccount();

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    void setToolbarTitle(String title);

    void showEmptyFieldsDetected();

    void showFailedLogin();

    void showSuccessStudentLogin();

    void showSuccessAdminLogin();

    void showInvalidEmail();
}
