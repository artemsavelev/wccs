package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.exceptions.BadRequestException;
import com.smart.wccs.model.User;
import com.smart.wccs.model.Views;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminController {

    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public AdminController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping(value = "users/{id}")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "users")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "registration/", method = RequestMethod.POST)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> registration(@RequestBody User user) {

        boolean isEmail = userService.getAllUsers()
                .stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(user.getEmail()));

        boolean isUsername = userService.getAllUsers()
                .stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));

        if (user == null) {
            throw new BadRequestException("Вы не передали никаких данных");
        }

        if (user.getPositions().isEmpty()) {
            throw new BadRequestException("Поле должности не должно быть пустым");
        } else if (user.getPositions().size() > 1) {
            throw new BadRequestException("Поле должности не может иметь несколько значений");
        }

        if (isEmail) {
            throw new BadRequestException("Пользователь с email \"" + user.getEmail()
                    + "\" уже существует. Введите другой email");
        }

        if (isUsername) {
            throw new BadRequestException("Пользователь с login \"" + user.getUsername()
                    + "\" уже существует. Придумайте другой логин");
        }
        return ResponseEntity.ok(userService.register(user));
    }

    @RequestMapping(value = "registration/{id}", method = RequestMethod.PUT)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id, @RequestBody User user) {

        if (user.getPositions().isEmpty()) {
            throw new BadRequestException("Поле должности не должно быть пустым");
        } else if (user.getPositions().size() > 1) {
            throw new BadRequestException("Поле должности не может иметь несколько значений");
        }

        userService.getAllUsers()
                .stream()
                .filter(x -> !x.getId().equals(id))
                .map(User::getEmail)
                .filter(x -> x.equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(i -> {
                    throw new BadRequestException("Пользователь с email \"" + user.getEmail()
                            + "\" уже существует. Введите другой email");
                });

        userService.getAllUsers()
                .stream()
                .filter(x -> !x.getId().equals(id))
                .map(User::getUsername)
                .filter(x -> x.equalsIgnoreCase(user.getUsername()))
                .findAny()
                .ifPresent(i -> {
                    throw new BadRequestException("Пользователь с login \"" + user.getUsername()
                            + "\" уже существует. Придумайте другой логин");
                });

        return ResponseEntity.ok(userService.update(id, user));
    }
}
