package com.example.studytracker.view.login.signin;

import com.example.studytracker.dao.daoStub.UserDAO;
import com.example.studytracker.domain.ManagementUser;
import com.example.studytracker.domain.Student;
import com.example.studytracker.domain.User;
import com.example.studytracker.util.Cryptography;
import com.example.studytracker.util.RegexUtil;
import com.example.studytracker.util.SharedPrefUtil;

public class LoginPresenter {
    LoginView view;
    UserDAO userDAO;
    SharedPrefUtil sharedPref;

    public LoginPresenter(LoginView view, UserDAO userDAO, SharedPrefUtil sharedPref) {
        this.view = view;
        this.userDAO = userDAO;
        this.sharedPref = sharedPref;
    }

    void onLogin() {
        if (view.getEmail().isEmpty() || view.getPassword().isEmpty()) {
            view.showEmptyFieldsDetected();
            return;
        }

        if (!RegexUtil.isEmail(view.getEmail())) {
            view.showInvalidEmail();
            return;
        }

        String encryptedPassword = Cryptography.md5(view.getPassword());
        User user = userDAO.getUserFromCredentials(view.getEmail(), encryptedPassword);

        if (user == null) {
            view.showFailedLogin();
        } else if (user instanceof Student) {
            view.showSuccessStudentLogin();
        } else if (user instanceof ManagementUser) {
            view.showSuccessAdminLogin();
        }

        if (user != null) {
            sharedPref.setInt(SharedPrefUtil.USER_ID, user.getId());
        }
    }

    void onCreateAnAccount() {
        view.onCreateAnAccount();
    }

    void detach() {
        view = null;
    }

}
