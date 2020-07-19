package com.ics499.unorater.controllers;


import com.ics499.unorater.exceptions.ResourceNotFoundException;
import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.User;
import com.ics499.unorater.payloads.*;
import com.ics499.unorater.repositories.ReviewRepository;
import com.ics499.unorater.repositories.ServiceRepository;
import com.ics499.unorater.repositories.UserRepository;
import com.ics499.unorater.security.CurrentUser;
import com.ics499.unorater.models.UserPrincipal;
import com.ics499.unorater.utils.BadWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Defines the RESTFul API endpoints available to a Regular User.
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('REGULAR_USER')")
public class RegularUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegularUserController.class);

    /**
     * Gets the User summary info: (Username and User Id)
     * @param currentUser The user currently logged in
     * @return The User summary
     */
    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail());
    }

    /**
     * Gets public facing services
     * @return List of all the public facing services.
     *         This is done so that the reviews attached
     *         to public facing services can be displayed
     *         on the frontend.
     */
    @GetMapping("/user/viewpublicservices")
    public List <Service> getAllPublicServices () {
        return serviceRepository.findPublicServices();
    }

    /**
     * Gets all services
     * @return a list of all services
     */
    @GetMapping("/user/viewallservices")
    public List <Service> getAllServices () {
        return serviceRepository.findAll();
    }

    /**
     * Checks if a username is available in case
     * a user wants to change their email.
     * @param userName The username to check for
     * @return True if available, false if not.
     */
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String userName) {
        Boolean isAvailable = !userRepository.existsByuserName(userName);
        return new UserIdentityAvailability(isAvailable);
    }

    /**
     * Checks if a email is available in case
     * a user wants to change their email.
     * @param email The username to check for
     * @return True if available, false if not.
     */
    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }


    /**
     * Gets the full user profile which total number of reviews,
     * approved reviews.
     * @param userPrincipal The current user logged in
     * @return
     */
    @GetMapping("/user/me/profile")
    public UserProfile getUserProfile(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findByuserName(userPrincipal.getUsername());

        if (user != null) {
            return new UserProfile(user.getUserID(), user.getUserName(), user.getReviews().size(),  user.getReviews());
        }else {
            throw new ResourceNotFoundException("User", "username", userPrincipal.getUsername());
        }
    }

    /**
     * Posts user's review
     * @param review
     * @return Message Payload based operation success/failure.
     *         The major reason this would fail is if bad words
     *         were in the review.
     */
    @PostMapping ("user/me/postreview")
    public ResponseEntity <?> makeReview (@RequestBody Review review) {
        logger.info(review.toString());
        BadWordFilter.loadConfigs();
        List<String> badWordsFound = BadWordFilter.badWordsFound(review.getReviewText());

        if (badWordsFound.size() > 0) {
            return ResponseEntity.ok(new ApiResponse(false, "Bad words found: " + badWordsFound.toString() + ". Please modify your review"));
        }

        review.setDateCreated(new Date());
        reviewRepository.save(review);
        return ResponseEntity.ok("Review Created : " + review.getDateCreated());
    }

    /**
     * Updates a user's review
     * @param newReview The new replacing old one.
     * @return Message Payload based operation success/failure.
     *         The major reason this would fail is if bad words
     *         were in the review
     */
    @PutMapping ("user/me/updatereview")
    public ResponseEntity <?> updateReview (@RequestBody Review newReview) {

        logger.info(newReview.toString());
        BadWordFilter.loadConfigs();
        Review oldReview = reviewRepository.findById(newReview.getReviewID()).get();
        List<String> badWordsFound = BadWordFilter.badWordsFound(newReview.getReviewText());

        if (badWordsFound.size() > 0) {
            return ResponseEntity.ok(new ApiResponse(true, "Bad words found: " + badWordsFound.toString() + ". Please modify your review"));
        }

        oldReview.setReviewText(newReview.getReviewText());
        oldReview.setDateModified(new Date());
        reviewRepository.save(oldReview);
        return ResponseEntity.ok("Review Modified : " + oldReview.getDateModified());
    }

    /**
     * Deletes a review
     * @param reviewID
     */
    @DeleteMapping("user/me/deletereview/{reviewID}")
    public void deleteReview (@PathVariable Integer reviewID) {
        reviewRepository.deleteById(reviewID);
    }

    /**
     * Searches for services that match the name specified
     * @param serviceName
     * @return
     */
    @GetMapping("/regularuser/search/{serviceName}")
    public List<Service> searchServices (@PathVariable String serviceName) {
        return serviceRepository.searchServices(serviceName);
    }
}
