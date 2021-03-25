package com.nnamdi.account.controller;

import com.nnamdi.account.model.Role;
import com.nnamdi.account.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(UserRole name);
}
