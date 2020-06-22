package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Reviews entity.
 *
 * @author UNO TEAM
 */
public interface ReviewRepository extends JpaRepository <Review, Integer> {}
