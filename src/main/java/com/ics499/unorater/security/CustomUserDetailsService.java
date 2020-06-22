package com.ics499.unorater.security;


import com.ics499.unorater.exceptions.ResourceNotFoundException;
import com.ics499.unorater.models.User;
import com.ics499.unorater.models.UserPrincipal;
import com.ics499.unorater.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticates a User and
 * perform various role-based checks.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByuserNameOrEmail(usernameOrEmail, usernameOrEmail);

        if (user != null) {
            return UserPrincipal.create(user);
        }else {
            throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
        }

    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}