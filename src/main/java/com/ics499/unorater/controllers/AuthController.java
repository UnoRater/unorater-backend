package com.ics499.unorater.controllers;

import com.ics499.unorater.exceptions.AppException;
import com.ics499.unorater.models.Role;
import com.ics499.unorater.models.User;
import com.ics499.unorater.models.enums.RoleName;
import com.ics499.unorater.payloads.ApiResponse;
import com.ics499.unorater.payloads.JwtAuthenticationResponse;
import com.ics499.unorater.payloads.LoginRequest;
import com.ics499.unorater.payloads.SignUpRequest;
import com.ics499.unorater.repositories.RoleRepository;
import com.ics499.unorater.repositories.UserRepository;
import com.ics499.unorater.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Date;

/**
 * Contains APIs for login(includes regular users, system admins, and department admins) and signup.
 * @author UNO-TEAM.
 */
@Component
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateRegularUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/adminsignin")
    public ResponseEntity<?> authenticateAdminUser(@Valid @RequestBody LoginRequest loginRequest) {

        User adminUser = userRepository.findByuserNameOrEmail(loginRequest.getUsernameOrEmail()
                , loginRequest.getUsernameOrEmail());

        for (Role role : adminUser.getRoles()) {
            if (role.getName().compareTo(RoleName.ROLE_SYSTEM_ADMIN) == 0) {

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsernameOrEmail(),
                                loginRequest.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

            }

        }
        return new ResponseEntity(new ApiResponse(false, "Incorrect Credentials for an Admin"),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/departmentadminsignin")
    public ResponseEntity<?> authenticateDepartmentAdmin(@Valid @RequestBody LoginRequest loginRequest) {

        User departmentAdminUser = userRepository.findByuserNameOrEmail(loginRequest.getUsernameOrEmail()
                , loginRequest.getUsernameOrEmail());

        for (Role role : departmentAdminUser.getRoles()) {
            if (role.getName().compareTo(RoleName.ROLE_DEPARTMENT_ADMIN) == 0) {

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsernameOrEmail(),
                                loginRequest.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

            }

        }
        return new ResponseEntity(new ApiResponse(false, "Incorrect Credentials for an Department Admin"),
                HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByuserName(signUpRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUserName(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_REGULAR_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        user.setDateCreated(new Date());

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
