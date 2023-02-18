package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.model.User;
import com.sda.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class UsersController {

//    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    public void findAll() {
        List<UserDTO> users = usersService.findAll();

        if (users.isEmpty()) {
            System.out.println("Users list empty!");
        } else {
            System.out.println("Users list:");
            users.forEach(user -> System.out.println(user));
//            users.forEach(System.out::println);
        }
    }

    public void findByUsername(String username) {
        try {
            UserDTO userDTO = usersService.findByUsername(username);
            System.out.printf("User found: %s%n", userDTO);
        } catch (NotFoundException ex) {
            log.error("NotFoundException: {}", ex.getMessage());
        }
    }

    public void deleteByUsername(String username) {
        try {
            usersService.deleteByUsername(username);
            System.out.printf("User with username '%s' deleted!%n", username);
        } catch (NotFoundException ex) {
            log.error("NotFoundException: {}", ex.getMessage());
        }
    }

    public void create(User user) {
        try {
            usersService.create(user);
            System.out.printf("User with username '%s' created!\n", user.getUsername());
        } catch (UsernameConflictException ex) {
            log.error("UsernameConflictException: {}", ex.getMessage());
        }
    }

    public void update(User user, String username) {
        try {
            UserDTO updatedUser = usersService.update(user, username);
            System.out.printf("User with username '%s' updated!\n", username);
            System.out.printf("User after update: %s.\n", updatedUser);
        } catch (UsernameConflictException ex) {
            log.error("UsernameConflictException: {}", ex.getMessage());
        } catch (NotFoundException ex) {
            log.error("NotFoundException: {}", ex.getMessage());
        }
    }
}
