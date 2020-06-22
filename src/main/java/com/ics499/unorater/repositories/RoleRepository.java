package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Role;
import com.ics499.unorater.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Roles entity.
 *
 * @author UNO TEAM
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}
