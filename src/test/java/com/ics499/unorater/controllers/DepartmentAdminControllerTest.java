package com.ics499.unorater.controllers;

import com.ics499.unorater.models.Department;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.User;
import com.ics499.unorater.repositories.DepartmentRepository;
import com.ics499.unorater.repositories.ServiceRepository;
import com.ics499.unorater.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

/**
 * Tests that the repositories are fetching
 * and creating the right data for department admincontroller.
 */
@SpringBootTest
public class DepartmentAdminControllerTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    public void testServiceRepo() {
        Service testService = serviceRepository.findByserviceName("Test Service");

        if (testService != null) {
            serviceRepository.delete(testService);
        }

        User departmentAdminUser = userRepository.findById(3).get();

        Service service = new Service();
        service.setServiceName("Test Service");
        service.setPublicService(true);
        service.setDepartmentID(departmentAdminUser.getDepartmentNum());
        service.setDateCreated(new Date());
        Service result = serviceRepository.save(service);

        assertThat(result.getServiceName().equalsIgnoreCase("Test Service"));
    }

    @Test
    public void testAdminRepo() {
        Department testDepartment = departmentRepository.findById(1).get();
        assertThat(testDepartment).isNotNull();
    }

}
