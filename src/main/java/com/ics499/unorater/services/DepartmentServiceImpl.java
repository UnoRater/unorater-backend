package com.ics499.unorater.services;

import com.ics499.unorater.interfaces.IDepartmentService;
import com.ics499.unorater.models.Department;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Implementation for DepartmentService.
 * Allows for CRUD operations on Departments entity.
 *
 * @author UNO TEAM
 */
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> listAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getOne(Integer departmentID) {
        return departmentRepository.getOne(departmentID);
    }

    @Override
    public List<Service> getDepartmentServices(Integer departmentID) {
        return departmentRepository.getOne(departmentID).getServices();
    }


}
