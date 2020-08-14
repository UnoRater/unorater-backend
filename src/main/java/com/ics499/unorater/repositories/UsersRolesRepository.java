package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Integer> {


    @Modifying
    @Query("delete from UsersRoles u where u.userID = :userID")
    void deleteUserRoles(@Param("userID") Integer userID);

    void deleteUsersRolesByUserID(int userID);
}
