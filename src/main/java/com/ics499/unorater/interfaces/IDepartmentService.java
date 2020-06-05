package com.ics499.unorater.interfaces;

import com.ics499.unorater.models.Department;
import com.ics499.unorater.models.Service;

import java.util.List;

/**
 * Interface for Department Service
 * @author UNO TEAM
 */
public interface IDepartmentService {
    List <Department> listAllDepartments ();
    Department getOne(Integer departmentID);
    List <Service> getDepartmentServices (Integer departmentID);
}
