package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.service.UsersService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    public void findAll() {
        List<UserDTO> users = usersService.findAll();

        if(users.isEmpty()) {
            System.out.println("Users list empty!");
        } else {
            System.out.println("Users list:");
            users.forEach(user-> System.out.println(user));

        }
    }

    public void findByUsername(String username) {

    }

}
