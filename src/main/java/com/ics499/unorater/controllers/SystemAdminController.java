package com.ics499.unorater.controllers;

import com.ics499.unorater.exceptions.AppException;
import com.ics499.unorater.models.*;
import com.ics499.unorater.models.enums.RoleName;
import com.ics499.unorater.payloads.ApiResponse;
import com.ics499.unorater.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Defines the RESTFul API endpoints for a System Admin User.
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('SYSTEM_ADMIN')")
public class SystemAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    private static final Logger logger = LoggerFactory.getLogger(RegularUserController.class);

    /**
     * Grants system admin role to user.
     * @param userID The userID
     * @return Response payload based on operation success/failure.
     */
    @RequestMapping(value = "/grantsysadminrole/{userID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity <?> grantSysAdminRole (@PathVariable Integer userID) {
        User user = userRepository.findById(userID).get();
        Role userRole = roleRepository.findByName(RoleName.ROLE_SYSTEM_ADMIN)
                .orElseThrow(() -> new AppException("Role not set."));

        for (Role role : user.getRoles()) {
            if (role.getName().compareTo(RoleName.ROLE_SYSTEM_ADMIN) == 0){
                throw new AppException("Role already assigned to user");
            }
        }

        user.getRoles().add(userRole);
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Role " + RoleName.ROLE_SYSTEM_ADMIN + " Successfully Assigned to User " + user.getUserName()));
    }


    /**
     * Removes system admin role from user.
     * @param userID The userID
     * @return Response payload based on operation success/failure.
     */
    @RequestMapping(value = "/removesysadminrole/{userID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity <?> removeSysAdminRole (@PathVariable Integer userID) {
        User user = userRepository.findById(userID).get();
        Role userRole = roleRepository.findByName(RoleName.ROLE_SYSTEM_ADMIN)
                .orElseThrow(() -> new AppException("Role not set."));

        user.getRoles().remove(userRole);
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Role " + RoleName.ROLE_SYSTEM_ADMIN + " Successfully Removed from User " + user.getUserName()));
    }

    /**
     * Grants department admin role to user.
     * @param userID The userID
     * @param departmentID The departmentID to be granted admin authority on.
     * @return Response payload based on operation success/failure.
     */
    @RequestMapping(value = "/grantdepadminrole/{userID}/{departmentID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity <?> grantDepartmentAdminRole (@PathVariable Integer userID, @PathVariable Integer departmentID) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_DEPARTMENT_ADMIN)
                .orElseThrow(() -> new AppException("Role not set."));

        User user = userRepository.findById(userID).get();
        for (Role role : user.getRoles()) {
            if (role.getName().compareTo(RoleName.ROLE_DEPARTMENT_ADMIN) == 0){
                throw new AppException("Role already assigned to user");
            }
        }
        user.setDepartmentNum(departmentID);
        user.getRoles().add(userRole);

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Role " + RoleName.ROLE_DEPARTMENT_ADMIN + " Successfully Assigned to User " + user.getUserName()));
    }

    /**
     * Removes department admin role from user.
     * @param userID The userID
     * @return Response payload based on operation success/failure.
     */
    @RequestMapping(value = "/removedepadminrole/{userID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity <?> removeDepartmentAdminRole (@PathVariable Integer userID) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_DEPARTMENT_ADMIN)
                .orElseThrow(() -> new AppException("Role not set."));

        User user = userRepository.findById(userID).get();

        user.setDepartmentNum(0);
        user.getRoles().remove(userRole);

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Role " + RoleName.ROLE_DEPARTMENT_ADMIN + " Successfully Removed from User " + user.getUserName()));
    }

    /**
     * Gets the List of all available departments
     * @return List of all departments
     */
    @GetMapping ("/systemadmin/departments")
    public List<Department> getAllDepartments () {
        return departmentRepository.findAll();
    }

    /**
     * Creates a new department
     * @param departmentName The department name to be persisted
     * @return Response payload based on operation success/failure.
     */
    @RequestMapping(value = "/systemadmin/createdepartment/{departmentName}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> addDepartment (@PathVariable String departmentName) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        departmentRepository.save(department);
        return ResponseEntity.ok(new ApiResponse(true, "Department Added Successfully"));
    }

    /**
     * Deletes a department
     * @param departmentID The departmentID
     */
    @RequestMapping(value = "/systemadmin/delete/{departmentID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> deleteDepartment (@PathVariable Integer departmentID) {
        departmentRepository.deleteById(departmentID);
        return ResponseEntity.ok(new ApiResponse(true, "Department Deleted Successfully"));
    }

    /**
     * Renames a department.
     * @param departmentID The departmentID.
     * @param newName The new name to assign to department.
     */
    @RequestMapping(value ="/systemadmin/department/rename/{departmentID}/{newName}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> updateDepartmentName (@PathVariable Integer departmentID, @PathVariable String newName) {
        Department department = departmentRepository.findById(departmentID).get();
        department.setDepartmentName(newName);
        departmentRepository.save(department);

        return ResponseEntity.ok(new ApiResponse(true, "Department Successfully Renamed to: " + newName));
    }

    /**
     * Gets all the users in the system
     * @return List of all users
     */
    @GetMapping ("/systemadmin/users")
    public List <User> getUsers () {
        return userRepository.findAll();
    }

    /**
     * Deletes a user
     * @param userID The userID
     */
    @RequestMapping(value = "/systemadmin/user/delete/{userID}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> deleteUser (@PathVariable Integer userID) {

        User userToDelete = userRepository.findById(userID).get();

        //usersRolesRepository.deleteUserRoles(userToDelete.getUserID());
        if (!userToDelete.getRoles().isEmpty()) {
            userToDelete.getRoles().clear();
        }
        userRepository.delete(userToDelete);

        return ResponseEntity.ok(new ApiResponse(true, "User: " + userToDelete.getUserName() +  " has been successfully deleted from the system"));
    }


    /**
     * Deletes a review
     */
    @DeleteMapping("/systemadmin/reviews/delete/{reviewID}")
    public void deleteReview (@PathVariable Integer reviewID) {
        reviewRepository.deleteById(reviewID);
    }
}
