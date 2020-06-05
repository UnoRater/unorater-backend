package com.ics499.unorater.interfaces;

import com.ics499.unorater.models.Review;

import java.util.List;

/**
 *  Specifies operations for review service.
 *
 * @author UNO TEAM
 */
public interface IReviewService {
    List <Review> listAllReviews ();
    Review getOneReview (Integer reviewID);
}
