package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(28);
        expectedUser.setName("Karen");
        expectedUser.setSurname("Smith");
        expectedUser.setPassword("Super secret pass");
        expectedUser.setEmail("karen123@gmail.com");

        // when
        usersDAO.create(expectedUser);

        // then
        Session session = HibernateUtils.openSession();
        User actualUser = session.find(User.class, username);
        session.close();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void testDeleteUserNotExist() {
//      given
        String notExistedUser = "User not exists";

//      when
        boolean delete = usersDAO.delete(notExistedUser);

//      then
        Assertions.assertFalse(delete);
    }
    @Test
    void UserExist() {
//      given
        String notExistedUser = "User not exists";

//      when
        boolean delete = usersDAO.delete(notExistedUser);

//      then
        Assertions.assertFalse(delete);
    }
    @Test
    void testDeleteUserExist() {
//        given
        String username = UUID.randomUUID().toString();
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setAge(28);
        expectedUser.setName("Karen");
        expectedUser.setSurname("Smith");
        expectedUser.setEmail("karen123@gmail.com");

        usersDAO.create(expectedUser);

//        when
        boolean delete = usersDAO.delete(username);

//        then
        Assertions.assertTrue(delete);

    }

}
