package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Reviews entity.
 *
 * @author UNO TEAM
 */
public interface ReviewRepository extends JpaRepository <Review, Integer> { }
