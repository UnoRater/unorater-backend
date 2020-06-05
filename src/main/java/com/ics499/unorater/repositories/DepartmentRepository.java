package com.ics499.unorater.repositories;

import com.ics499.unorater.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines all CRUD(CREATE, READ, UPDATE, DELETE)
 * operations available for the Departments entity.
 *
 * @author UNO TEAM
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> { }
