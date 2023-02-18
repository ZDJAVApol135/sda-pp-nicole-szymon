package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
