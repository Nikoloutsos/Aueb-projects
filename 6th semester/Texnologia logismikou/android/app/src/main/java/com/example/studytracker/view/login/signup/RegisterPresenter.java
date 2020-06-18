package com.example.studytracker.view.login.signup;

import com.example.studytracker.dao.daoStub.UserDAO;
import com.example.studytracker.domain.Student;
import com.example.studytracker.util.Cryptography;
import com.example.studytracker.util.RegexUtil;
import com.example.studytracker.util.SharedPrefUtil;

public class RegisterPresenter {
    RegisterView view;
    UserDAO userDAO;
    SharedPrefUtil sharedPref;

    public RegisterPresenter(RegisterView view, UserDAO userDAO, SharedPrefUtil sharedPref) {
        this.view = view;
        this.userDAO = userDAO;
        this.sharedPref = sharedPref;
    }

    public void onRegister() {
        if (view.getEmail().isEmpty() || view.getUsername().isEmpty() || view.getPassword().isEmpty()) {
            view.showEmptyFieldsDetected();
            return;
        }

        if (!RegexUtil.isEmail(view.getEmail())) {
            view.showInvalidEmail();
            return;
        }

        if (userDAO.isEmailTaken(view.getEmail())) {
            view.showEmailAlreadyExists();
            return;
        }

        String encryptedPassword = Cryptography.md5(view.getPassword());
        Student student = new Student(view.getUsername(), view.getEmail(), encryptedPassword);

        userDAO.register(student);
        view.onSuccessfulRegister();
    }

    void detach() {
        view = null;
    }

}
