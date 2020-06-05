package com.ics499.unorater.services;

import com.ics499.unorater.interfaces.IUserService;
import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Role;
import com.ics499.unorater.models.User;
import com.ics499.unorater.repositories.UserRepository;
import com.ics499.unorater.security.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

/**
 * Implementation for UserService.
 * Allows for CRUD operations on Users entity.
 *
 * @author UNO TEAM
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getOneUser (Integer userID) {
        return userRepository.getOne(userID);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Review> getUserReviews(Integer userID) {
        return userRepository.getOne(userID).getReviews();
    }

    @Override
    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public List<User> listAllUsers () {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
