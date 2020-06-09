//package com.example.studytracker.util;
//
//import com.example.studytracker.dao.memorydao.UserDAOMemory;
//import com.example.studytracker.domain.ManagementUser;
//import com.example.studytracker.domain.Student;
//import com.example.studytracker.domain.User;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
//public class UserAccessManagerTest {
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Mock
//    private UserDAOMemory userDAO = new UserDAOMemory();
//
//    @Mock
//    Student student;
//
//    @Mock
//    ManagementUser managementUser;
//
//
//
//    @Before
//    public void init(){
//        when(student.getNoSemesters()).thenReturn(8);
//        when(student.getName()).thenReturn("jenny");
//        when(student.getMail()).thenReturn("jenny@gmail.com");
//        when(student.getEncryptedPassword()).thenReturn(Cryptography.md5("123"));
//        when(student.getBio()).thenReturn("aueb student");
//
//        when(managementUser.getName()).thenReturn("Kostas");
//        when(managementUser.getMail()).thenReturn("kostas@gmail.com");
//        when(managementUser.getEncryptedPassword()).thenReturn(Cryptography.md5("1234"));
//
//        List<User> users = new ArrayList<>();
//        users.add(student);
//        users.add(managementUser);
//        when(userDAO.getUsers()).thenReturn(users);
//    }
//
//
//    @Test
//    public void check_successful_signUp(){
//        String name = "nick";
//        String mail = "nick@gmail.com";
//        String bio = "aueb student";
//        String password = "12345";
//        String userCategory = "student";
//        int noSemesters = 8;
//        when(userDAO.addUser(name, mail, bio, password, userCategory, noSemesters)).
//                thenReturn(new Student(name, mail, bio, noSemesters, password));
//
//        UserAccessManager userAccessManager = new UserAccessManager(userDAO);
//        User user = userAccessManager.signUp(name, mail, bio, password, userCategory, noSemesters);
//
//        Assert.assertNotEquals(user, null);
//
//    }
//
//    @Test
//    public void check_unsuccessful_signUp(){
//        String name = "jen";
//        String mail = "jenny@gmail.com";
//        String bio = "aueb student";
//        String password = "123456";
//        String userCategory = "student";
//        int noSemesters = 8;
//        when(userDAO.addUser(name, mail, bio, password, userCategory, noSemesters)).
//                thenReturn(new Student(name, mail, bio, noSemesters, password));
//
//        UserAccessManager userAccessManager = new UserAccessManager(userDAO);
//        User user = userAccessManager.signUp(name, mail, bio, password, userCategory, noSemesters);
//
//        Assert.assertEquals(user, null);
//    }
//
//    @Test
//    public void check_successful_logIn(){
//        String password = "123";
//        String mail = "jenny@gmail.com";
//
//        UserAccessManager userAccessManager = new UserAccessManager(userDAO);
//        User user = userAccessManager.signIn(mail, password);
//
//        Assert.assertNotEquals(user, null);
//        Student s = new Student();
//        Assert.assertEquals(user.getClass(), s.getClass());
//    }
//
//    @Test
//    public void check_unsuccessful_logIn(){
//        String password = "123";
//        String mail = "kostas@gmail.com";
//
//        UserAccessManager userAccessManager = new UserAccessManager(userDAO);
//        User user = userAccessManager.signIn(mail, password);
//
//        Assert.assertEquals(user, null);
//    }
//
//}