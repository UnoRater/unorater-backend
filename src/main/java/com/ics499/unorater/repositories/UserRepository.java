package com.ics499.unorater.repositories;

import com.ics499.unorater.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the User entity.
 *
 * @author UNO TEAM
 */
@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    User findByEmail(String email);
}
