package com.ics499.unorater.services;

import com.ics499.unorater.interfaces.IReviewService;
import com.ics499.unorater.models.Review;
import com.ics499.unorater.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Implementation for ReviewService.
 * Allows for CRUD operations on Reviews entity.
 *
 * @author UNO TEAM
 */
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    ReviewRepository reviewRepositiory;

    @Override
    public List<Review> listAllReviews() {
        return reviewRepositiory.findAll();
    }

    @Override
    public Review getOneReview(Integer reviewID) {
        return reviewRepositiory.getOne(reviewID);
    }
}
