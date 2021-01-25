package com.smart.wccs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smart.wccs.model.Role;
import com.smart.wccs.model.Status;
import com.smart.wccs.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private List<Role> roles;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));

        return user;
    }

    public static AdminDto fromUser(User user) {
        AdminDto adminDto = new AdminDto();
        adminDto.setId(user.getId());
        adminDto.setUsername(user.getUsername());
        adminDto.setFirstName(user.getFirstName());
        adminDto.setLastName(user.getLastName());
        adminDto.setEmail(user.getEmail());
        adminDto.setStatus(user.getStatus().name());

        return adminDto;
    }

    public static List<AdminDto> userDtoList(List<User> users) {
        List<AdminDto> userList = new ArrayList<>();
        users.forEach(user -> userList.add(fromUser(user)));
        return userList;
    }

}
