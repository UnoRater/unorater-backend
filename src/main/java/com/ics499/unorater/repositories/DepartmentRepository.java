package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Departments entity.
 *
 * @author UNO TEAM
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> { }
