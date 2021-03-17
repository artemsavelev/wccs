package com.smart.wccs.security;

import com.smart.wccs.model.User;
import com.smart.wccs.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {


    private final UserRepo userRepo;

    @Autowired
    public JwtUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

//    private final UserService userService;

//    @Autowired
//    public JwtUserDetailsService(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
