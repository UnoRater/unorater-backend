package com.ics499.unorater.controllers;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.User;
import com.ics499.unorater.repositories.ReviewRepository;
import com.ics499.unorater.repositories.ServiceRepository;
import com.ics499.unorater.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Tests that the repositories are fetching
 * and creating the right data for regular user controller.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RegularUserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegularUserControllerTest.class);

    @Test
    @Order(2)
    public void viewAllPublicServicesTest() {
        List<Service> publicServices = serviceRepository.findPublicServices();
        assertThat(publicServices.size() > 0).isTrue();
    }

    @Test
    @Order(1)
    public void viewAllServicesTest () {
        List<Service> allServices = serviceRepository.findAll();
        assertThat(allServices.size() > 0).isTrue();
    }

    @Test
    @Order(3)
    public void checkUsernameAvailabilityTest () {
        assertThat(!userRepository.existsByuserName("Seyi")).isFalse();
        assertThat(!userRepository.existsByuserName("Camel")).isTrue();
    }

    @Test
    @Order(4)
    public void checkEmailAvailabilityTest () {
        assertThat(!userRepository.existsByEmail("testing@gmail.com")).isFalse();
        assertThat(!userRepository.existsByuserName("Camel@gmail")).isTrue();
    }

    @Test
    @Order(5)
    public void makeReviewTest (){
        Review testReview = new Review();
        testReview.setUserID(4);
        testReview.setServiceID(1);
        testReview.setScore("4");
        testReview.setReviewText("This is just testing");
        testReview.setDateCreated(new Date());
        Review result = reviewRepository.save(testReview);

        assertThat(result).isNotNull();
    }

    @Test
    @Order(6)
    public void deleteReviewTest () {
        User testUser = userRepository.findById(4).get();
        Review reviewToDelete = null;

        List<Review> testUserReviews = reviewRepository.findByuser(testUser);
        reviewToDelete = testUserReviews.get(0);

        assert reviewToDelete != null;
        reviewRepository.delete(reviewToDelete);

        // If we get here without an exception
        // we deleted the review successfully.
        assertThat(true).isTrue();
    }
}
