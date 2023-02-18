package com.sda.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

public class UsersServiceTest {

    private final UsersDAO usersDAO = new UsersDAO();
    private final UserMapper userMapper = new UserMapper();
    private final UsersService usersService = new UsersService(usersDAO, userMapper);


    @Test
    void testFindByUsernameUserNotFound() {
        // given
        String notExistingUsername = "notExistingUsername";

        // when
        Executable executable = () -> usersService.findByUsername(notExistingUsername);

        // then
        Assertions.assertThrows(NotFoundException.class, executable);
    }

    @Test
    void testFindByUsernameUserFoundDoesNotThrowsException() {
        // given
        String username = UUID.randomUUID().toString();
        User userToSave = createUser(username);
        usersDAO.create(userToSave);

        // when
        Executable executable = () -> usersService.findByUsername(username);

        // then
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    void testFindByUsernameUserFound() {
        // given
        String username = UUID.randomUUID().toString();
        User userToSave = createUser(username);
        usersDAO.create(userToSave);

        UserDTO expectedUserDTO = userMapper.map(userToSave);

        // when
        UserDTO actualUserDTO = usersService.findByUsername(username);

        // then
        Assertions.assertEquals(expectedUserDTO, actualUserDTO);
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
