package com.ics499.unorater.services;

import com.ics499.unorater.interfaces.IServicesService;
import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Service;
import com.ics499.unorater.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Implementation for Service's Service.
 * Allows for CRUD operations on Services entity.
 *
 * @author UNO TEAM
 */
public class ServicesServiceImpl implements IServicesService {

    @Autowired
    ServiceRepository serviceRepository;


    @Override
    public List<Service> listAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getOneService(Integer serviceID) {
        return serviceRepository.getOne(serviceID);
    }

    @Override
    public List<Review> listServiceReviews(Integer serviceID) {
        return serviceRepository.getOne(serviceID).getReviews();
    }
}
