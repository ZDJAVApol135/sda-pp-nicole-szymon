package com.sda;

import com.sda.controller.InputController;
import com.sda.controller.UsersController;
import com.sda.dao.UsersDAO;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import com.sda.service.UsersService;

import java.util.Scanner;

public class Main {

    private static final String EXIT_OPTION = "6";

    private final static UsersDAO usersDAO = new UsersDAO();
    private final static UserMapper userMapper = new UserMapper();
    private final static UsersService userService = new UsersService(usersDAO, userMapper);
    private final static UsersController userController = new UsersController(userService);
    private final static Scanner scanner = new Scanner(System.in);
    private final static InputController inputController = new InputController(scanner);

    public static void main(String[] args) {
        String options = """
                Options:
                1 - List users
                2 - Find user
                3 - Create user
                4 - Delete user
                5 - Update users
                6 - Exit
                """;
        String userInput;
        do {
            System.out.println(options);
            userInput = inputController.getString("Choose option: ");

            switch (userInput) {
                case "1" -> userController.findAll();
                case "2" -> findByUsername();
                case "3" -> createUser();
                case "4" -> deleteUser();
                case "5" -> updateUser();
                case "6" -> System.out.println("Bye!");
            }

        } while (!EXIT_OPTION.equals(userInput));
    }

    private static void findByUsername() {
        String username = inputController.getUsername();
        userController.findByUsername(username);
    }

    private static void createUser() {
        User user = getUser();
        userController.create(user);
    }

    private static void deleteUser() {
        String username = inputController.getUsername();
        userController.deleteByUsername(username);
    }

    private static void updateUser() {
        String username = inputController.getUsername();
        System.out.println("User data to update: ");
        User updatedUser = getUser();
        userController.update(updatedUser, username);
    }

    private static User getUser() {
        String username = inputController.getUsername();
        String password = inputController.getPassword();
        String name = inputController.getName();
        String surname = inputController.getSurname();
        String email = inputController.getEmail();
        int age = inputController.getAge();

        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .build();
    }
}
