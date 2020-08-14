package com.ics499.unorater.controllers;

import com.ics499.unorater.models.Role;
import com.ics499.unorater.models.User;
import com.ics499.unorater.models.enums.RoleName;
import com.ics499.unorater.repositories.RoleRepository;
import com.ics499.unorater.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that the system admin controller use cases
 * through it's repositories.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class SystemAdminControllerTest {

    @Autowired
    SystemAdminController systemAdminController;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SystemAdminControllerTest.class);


    @Test
    @Order(1)
    public void grantSystemAdminRoleTest () {

        // Arrange
        User testUser = userRepository.findById(4).get();
        Role sysAdminRole = roleRepository.findByName(RoleName.ROLE_SYSTEM_ADMIN).get();

        // Act
        if (testUser.getRoles().contains(sysAdminRole)) {
            logger.info("SysAdmin role already assigned, now attempting to remove");
            testUser.getRoles().remove(sysAdminRole);
        }

        testUser.getRoles().add(sysAdminRole);

        // Assert
        assertThat(testUser.getRoles().contains(sysAdminRole)).isTrue();

    }

    @Test
    @Order(2)
    public void revokeSystemAdminRoleTest () {

        // Arrange
        User testUser = userRepository.findById(4).get();
        Role sysAdminRole = roleRepository.findByName(RoleName.ROLE_SYSTEM_ADMIN).get();

        // Act
        if (!testUser.getRoles().contains(sysAdminRole)) {
            logger.info("SysAdmin role not granted, now attempting to add");
            testUser.getRoles().add(sysAdminRole);
        }

        testUser.getRoles().remove(sysAdminRole);

        // Assert
        assertThat(testUser.getRoles().contains(sysAdminRole)).isFalse();
    }

    @Test
    @Order(3)
    public void grantDepAdminRole () {

        // Arrange
        User testUser = userRepository.findById(4).get();
        Role depAdminRole = roleRepository.findByName(RoleName.ROLE_DEPARTMENT_ADMIN).get();

        // Act
        if (testUser.getRoles().contains(depAdminRole)) {
            logger.info("DepAdmin role already assigned, now attempting to remove");
            testUser.getRoles().remove(depAdminRole);
        }

        testUser.getRoles().add(depAdminRole);

        // Assert
        assertThat(testUser.getRoles().contains(depAdminRole)).isTrue();
    }

    @Test
    @Order(4)
    public void revokeDepAdminRole () {

        // Arrange
        User testUser = userRepository.findById(4).get();
        Role depAdminRole = roleRepository.findByName(RoleName.ROLE_DEPARTMENT_ADMIN).get();

        // Act
        if (!testUser.getRoles().contains(depAdminRole)) {
            logger.info("DepAdmin already granted, now attempting to remove");
            testUser.getRoles().add(depAdminRole);
        }

        testUser.getRoles().remove(depAdminRole);

        // Assert
        assertThat(testUser.getRoles().contains(depAdminRole)).isFalse();
    }
}
