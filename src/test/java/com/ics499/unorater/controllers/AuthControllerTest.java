package com.ics499.unorater.controllers;

import com.ics499.unorater.models.Role;
import com.ics499.unorater.models.User;
import com.ics499.unorater.models.enums.RoleName;
import com.ics499.unorater.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that the UserRepository is fetching the
 * right data for authentication, and storing
 * the correct data for registration.
 */
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void authenticationTest() {

        // Arrange and Act
        User user = userRepository.findByuserName("Seyi");

        // Assert
        assertThat(user.getEmail()).isEqualTo("oluwaseyi.ola@my.metrostate.edu");
        for (Role role:
             user.getRoles()) {
            assertThat(role.getName().equals(RoleName.ROLE_REGULAR_USER)
                    || role.getName().equals(RoleName.ROLE_SYSTEM_ADMIN) || role.getName().equals(RoleName.ROLE_DEPARTMENT_ADMIN)).isTrue();
        }
    }

    @Test
    void registrationTest() {

        User testUser = userRepository.findByuserName("TestUser");

        if (testUser != null) {
            userRepository.delete(testUser);
        }

        // Arrange
        User user =  new User();

        // Act
        user.setUserName("TestUser");
        user.setEmail("TestUser@gmail.com");
        user.setPassword(encoder.encode("123pass"));
        //user.setRoles(Collections.singleton(userRole));
        user.setDateCreated(new Date());

        // Assert
        User result = userRepository.save(user);

        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo("TestUser");
        assertThat(result.getEmail()).isEqualTo("TestUser@gmail.com");
        assertThat(encoder.matches("123pass", result.getPassword()));

    }

}
