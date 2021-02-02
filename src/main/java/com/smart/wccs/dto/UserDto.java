package com.smart.wccs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smart.wccs.model.Department;
import com.smart.wccs.model.Position;
import com.smart.wccs.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<DepartmentDto> departments;
    private List<PositionDto> positions;

    public User toUser() {
        User user = new User();
        List<Department> departments = new ArrayList<>();
        List<Position> positions = new ArrayList<>();

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDepartments(departments);
        user.setPositions(positions);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDepartments(DepartmentDto.departmentDtoList(user.getDepartments()));
//        userDto.setPositions();

        return userDto;
    }

    public static List<UserDto> userDtoList(List<User> users) {
        List<UserDto> userList = new ArrayList<>();
        users.forEach(user -> userList.add(fromUser(user)));
        return userList;
    }
}
