package com.ics499.unorater.interfaces;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.User;
import com.ics499.unorater.security.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 *  Specifies operations for review service.
 *
 * @author UNO TEAM
 */
public interface IUserService extends UserDetailsService {
    List<User> listAllUsers();
    User getOneUser(Integer userID);
    User findByEmail(String email);
    List<Review> getUserReviews(Integer userID);
    User save(UserRegistrationDto registration);
}
