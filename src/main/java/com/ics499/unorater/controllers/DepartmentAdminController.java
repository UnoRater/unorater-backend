package com.ics499.unorater.controllers;

import com.ics499.unorater.models.Department;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.User;
import com.ics499.unorater.payloads.ApiResponse;
import com.ics499.unorater.repositories.DepartmentRepository;
import com.ics499.unorater.repositories.ServiceRepository;
import com.ics499.unorater.repositories.UserRepository;
import com.ics499.unorater.security.CurrentUser;
import com.ics499.unorater.models.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Defines the RESTFul API endpoints available to a Department Admin User.
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('DEPARTMENT_ADMIN')")
public class DepartmentAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegularUserController.class);

    @PostMapping("/departmentadmin/addservice")
    public ResponseEntity<?> addService (@RequestBody Service service, @CurrentUser UserPrincipal currentUser) {

        User departmentAdminUser = userRepository.findById(currentUser.getId()).get();;
        logger.info("Current User: " + currentUser.toString());
        service.setDepartmentID(departmentAdminUser.getDepartmentNum());
        service.setDateCreated(new Date());
        serviceRepository.save(service);
        return ResponseEntity.ok(new ApiResponse(true, "Service Added Successfully"));
    }

    @GetMapping("/departmentadmin/services")
    public Department viewDepartmentServices(@CurrentUser UserPrincipal currentUser) {
        User departmentAdminUser = userRepository.findByuserNameOrEmail(currentUser.getUsername(), currentUser.getEmail());
        return departmentRepository.findById(departmentAdminUser.getDepartmentNum()).get();
    }

    @PutMapping("/departmentadmin/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateService (@RequestBody Service service) {
        Service oldService = serviceRepository.findById(service.getServiceID()).get();
        oldService.setDateCreated(new Date());
        oldService.setServiceName(service.getServiceName());
        oldService.setServiceDescription(service.getServiceDescription());
        serviceRepository.save(oldService);

        return ResponseEntity.ok(new ApiResponse(true, "Service " + service.getServiceID() + " successfully updated"));
    }

    @DeleteMapping("/departmentadmin/delete/{serviceID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteService (@PathVariable Integer serviceID) {
        serviceRepository.deleteById(serviceID);
        return ResponseEntity.ok(new ApiResponse(true, "Service " + serviceID + " successfully deleted"));
    }

    /**
     * Searches for services that match the name specified
     * @param serviceName
     * @return Service Object
     */
    @GetMapping("/departmentadmin/search/{serviceName}")
    public List<Service> searchServices (@PathVariable String serviceName) {
        return serviceRepository.searchServices(serviceName);
    }
}
