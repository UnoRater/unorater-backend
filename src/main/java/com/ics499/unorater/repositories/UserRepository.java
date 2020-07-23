package com.ics499.unorater.repositories;

import com.ics499.unorater.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the User entity.
 *
 * @author UNO TEAM
 */
@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    User findByEmail(String email);

    User findByuserNameOrEmail(String userName, String email);

    List<User> findByuserIDIn(List<Integer> userID);

    User findByuserName(String userName);

    Boolean existsByuserName(String userName);

    Boolean existsByEmail(String email);

    @Query(value="select * from Users u where u.userName like :userName%", nativeQuery = true)
    List<User> searchUsers (@Param("userName") String userName);
}
