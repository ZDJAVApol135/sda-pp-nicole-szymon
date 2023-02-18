package com.sda;

import com.sda.controller.UsersController;
import com.sda.dao.UsersDAO;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import com.sda.service.UsersService;

public class Main {

    public static void main(String[] args) {
        UsersDAO usersDAO = new UsersDAO();
        UserMapper userMapper = new UserMapper();
        UsersService usersService = new UsersService(usersDAO, userMapper);
        UsersController usersController = new UsersController(usersService);

        User user = new User();
        user.setUsername("root1");
//        usersService.create(user);

        usersController.findByUsername("root2");

    }

}
