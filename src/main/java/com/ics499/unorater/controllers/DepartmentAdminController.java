package com.ics499.unorater.controllers;

import com.ics499.unorater.exceptions.AppException;
import com.ics499.unorater.models.Department;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.models.User;
import com.ics499.unorater.payloads.ApiResponse;
import com.ics499.unorater.repositories.DepartmentRepository;
import com.ics499.unorater.repositories.ServiceRepository;
import com.ics499.unorater.repositories.UserRepository;
import com.ics499.unorater.security.CurrentUser;
import com.ics499.unorater.models.UserPrincipal;
import org.apache.logging.log4j.message.StringFormattedMessage;
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

    @RequestMapping(value = "/departmentadmin/addservice/{serviceName}/{serviceDescription}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> addService (@PathVariable String serviceName, @PathVariable String serviceDescription, @CurrentUser UserPrincipal currentUser) {

        Service service = new Service();

        service.setServiceName(serviceName);
        service.setServiceDescription(serviceDescription);
        User departmentAdminUser = userRepository.findById(currentUser.getId()).get();;
        logger.info("Current User: " + currentUser.toString());
        service.setDepartmentID(departmentAdminUser.getDepartmentNum());
        service.setDateCreated(new Date());
        serviceRepository.save(service);
        return ResponseEntity.ok(new ApiResponse(true, "Service Added Successfully"));
    }

    @GetMapping("/departmentadmin/services")
    public List<Service> viewDepartmentServices(@CurrentUser UserPrincipal currentUser) {
        User departmentAdminUser = userRepository.findByuserNameOrEmail(currentUser.getUsername(), currentUser.getEmail());
        Department department =  departmentRepository.findById(departmentAdminUser.getDepartmentNum()).get();

        return department.getServices();
    }

    @PutMapping()
    @RequestMapping(value = "/departmentadmin/update/{serviceID}/{newServiceName}/{newServiceDescription}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateService (@PathVariable Integer serviceID, @PathVariable String newServiceName, @PathVariable String newServiceDescription ) {
        Service service = new Service();
        service.setServiceID(serviceID);
        service.setServiceName(newServiceName);
        service.setServiceDescription(newServiceDescription);
        Service oldService = serviceRepository.findById(service.getServiceID()).get();
        oldService.setDateCreated(new Date());
        oldService.setServiceName(service.getServiceName());
        oldService.setServiceDescription(service.getServiceDescription());
        serviceRepository.save(oldService);

        return ResponseEntity.ok(new ApiResponse(true, "Service " + service.getServiceID() + " successfully updated"));
    }

    @RequestMapping(value = "/departmentadmin/delete/{serviceID}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteService (@CurrentUser UserPrincipal currentUser, @PathVariable Integer serviceID) {
        Service service = serviceRepository.findById(serviceID).get();
        User user = userRepository.findByuserName(currentUser.getUsername());
        if (user.getDepartmentNum() > service.getDepartmentID()) {
            serviceRepository.deleteById(serviceID);
        } else {
            throw  new AppException("You do not have the permission to delete this resource");
        }
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
