package com.nnamdi.account.repository;

import com.nnamdi.account.model.Roles;
import com.nnamdi.account.model.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Roles,Long> {

    Optional<Roles> findByName(RolesEnum name);
    Optional<Roles>findRolesByName(RolesEnum rolesEnum);
}
