package com.smart.wccs.service.impl;

import com.smart.wccs.model.Role;
import com.smart.wccs.model.Status;
import com.smart.wccs.model.User;
import com.smart.wccs.repo.RoleRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("userServiceImpl")
//@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepo.findByRoles("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setDepartment(user.getDepartment());

        User registeredUser = userRepo.saveAndFlush(user);

        log.info("IN register - user with username: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        List<User> result = userRepo.findAll()
                .stream()
                .filter(o -> o.getDepartment() == userRepo.findByUsername(name).getDepartment())
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepo.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        user.setStatus(Status.DELETED);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
