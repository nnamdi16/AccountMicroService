package com.nnamdi.account.repository;

import com.nnamdi.account.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles,Long> {
    Optional<UserRoles> findUserRolesByAccountId(Long accountId);
}
