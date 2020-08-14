package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Reviews entity.
 *
 * @author UNO TEAM
 */
@Repository
public interface ReviewRepository extends JpaRepository <Review, Integer> {
    List<Review> findByuser(User user);
}
