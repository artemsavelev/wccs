package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.exceptions.BadRequestException;
import com.smart.wccs.model.*;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.*;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("registration")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> registration(@RequestBody User user) {

        boolean email = userService.getAllUsers()
                .stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(user.getEmail()));

        boolean username = userService.getAllUsers()
                .stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));

//        userService.getAllUsers()
//                .stream()
//                .map(User::getUsername)
//                .filter(x -> x.equalsIgnoreCase(user.getUsername()))
//                .findAny()
//                .ifPresent(i -> {
//                    new ResponseEntity<>("Пользователь с login \"" + user.getUsername()
//                            + "\" уже существует. Придумайте другой логин", HttpStatus.BAD_REQUEST);
//                });

        if (user == null) {
            return new ResponseEntity<>("Вы не передали никаких данных", HttpStatus.BAD_REQUEST);
        }

        if (user.getPositions().isEmpty()) {
            return new ResponseEntity<>("Поле должности не должно быть пустым", HttpStatus.BAD_REQUEST);
        } else if (user.getPositions().size() > 1) {
            return new ResponseEntity<>("Поле должности не может иметь несколько значений", HttpStatus.BAD_REQUEST);
        }

        if (email) {
            return new ResponseEntity<>("Пользователь с email \"" + user.getEmail()
                    + "\" уже существует. Введите другой email", HttpStatus.BAD_REQUEST);
        }

        if (username) {
            return new ResponseEntity<>("Пользователь с login \"" + user.getUsername()
                    + "\" уже существует. Придумайте другой логин", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userService.register(user));
    }
}
