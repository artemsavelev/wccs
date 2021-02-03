package com.smart.wccs.dto;

import com.smart.wccs.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {
    private String roles;

    public Role toRole() {
        Role role = new Role();
        role.setRoles(roles);
        return role;
    }

    public static RoleDto fromRole(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoles(role.getRoles());
        return roleDto;
    }

    public static List<RoleDto> roleDtoList(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        roles.forEach(role -> roleDtoList.add(fromRole(role)));
        return roleDtoList;
    }
}
