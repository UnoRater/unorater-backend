package com.ics499.unorater.services;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.User;
import com.ics499.unorater.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAllUsers () {
        return userRepository.findAll();
    }

    public User getOne (Integer userId) {
        return userRepository.getOne(userId);
    }

    public List <Review> getUserReview (Integer userID) {
        return userRepository.getOne(userID).getReviews();
    }
}
