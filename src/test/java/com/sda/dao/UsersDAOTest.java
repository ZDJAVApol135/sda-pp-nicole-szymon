package com.sda.dao;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);

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
        // given
        String nonExistingUsername = "NON EXISTING USER";

        // when
        boolean deleted = usersDAO.delete(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteUserExist() {
        // given
        String username = "username";
        User expectedUser = createUser(username);

        usersDAO.create(expectedUser);

        // when
        boolean deleted = usersDAO.delete(username);

        // then
        Assertions.assertTrue(deleted);
    }


    @Test
    void testFindAll() {
        // given
        final List<User> expectedList = List.of(
                createUser(UUID.randomUUID().toString()),
                createUser(UUID.randomUUID().toString()),
                createUser(UUID.randomUUID().toString())
        );

        expectedList.forEach(usersDAO::create);

        // when
        List<User> usersList = usersDAO.findAll();

        // then
        Assertions.assertNotNull(usersList);
        Assertions.assertEquals(expectedList.size(), usersList.size());
        Assertions.assertIterableEquals(expectedList, usersList);
    }

    @Test
    void testFindByUsername() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);
        usersDAO.create(expectedUser);

        // when
        User actualUser = usersDAO.findByUsername(username);

        // then
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
    }

    private User createUser(String username) {
        Faker faker = new Faker();
        Name name = faker.name();
        Internet internet = faker.internet();

        User user = new User();
        user.setUsername(username);
        user.setName(name.firstName());
        user.setSurname(name.lastName());
        user.setPassword(internet.password());
        user.setEmail(internet.emailAddress());
        user.setAge(faker.number().numberBetween(1, 150));
        return user;
    }
}
