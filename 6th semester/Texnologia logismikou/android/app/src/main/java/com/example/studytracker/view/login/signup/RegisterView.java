package com.example.studytracker.view.login.signup;

public interface RegisterView {
    void onSuccessfulRegister();

    String getEmail();

    void setEmail(String email);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);


    void showEmptyFieldsDetected();

    void showEmailAlreadyExists();

    void showInvalidEmail();
}
