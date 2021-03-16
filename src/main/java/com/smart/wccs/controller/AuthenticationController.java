package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.dto.*;
import com.smart.wccs.model.Department;
import com.smart.wccs.model.User;
import com.smart.wccs.model.Views;
import com.smart.wccs.security.jwt.JwtTokenProvider;
import com.smart.wccs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;



    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    @JsonView(Views.UserView.class)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {

        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();


            // передаем данные на фронтенд в профиль
            response.put("username", username);
            response.put("lastName", user.getLastName());
            response.put("firstName", user.getFirstName());
            response.put("email", user.getEmail());
            response.put("department", user.getDepartment());
            response.put("position", user.getPositions());
            response.put("role", user.getRoles());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("registration")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> registration(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("logout")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> logout(@RequestBody User user) {
        userService.logout(user);
        return ResponseEntity.ok("OK");
    }


}
