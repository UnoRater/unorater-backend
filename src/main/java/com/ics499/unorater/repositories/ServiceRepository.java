package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Services entity.
 *
 * @author UNO TEAM
 */
public interface ServiceRepository extends JpaRepository <Service, Integer> {

    /**
     * Custom queries to find all public
     * facing services in the repository.
     * @return A list of flagged reviews.
     */
    @Query("select s from Service s where s.publicService = true")
    List<Service> findPublicServices();

    @Query("select s from Service s where s.serviceName like %:serviceName")
    List <Service> searchServices(@Param("serviceName") String serviceName);
}
