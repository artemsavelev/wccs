package com.smart.wccs.repo;

import com.smart.wccs.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoles(String roles);
}
