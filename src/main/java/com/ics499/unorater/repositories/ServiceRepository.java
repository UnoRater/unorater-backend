package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Services entity.
 *
 * @author UNO TEAM
 */
public interface ServiceRepository extends JpaRepository <Service, Integer> { }
