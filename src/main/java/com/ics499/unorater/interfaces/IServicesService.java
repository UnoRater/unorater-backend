package com.ics499.unorater.interfaces;

import com.ics499.unorater.models.Review;
import com.ics499.unorater.models.Service;

import java.util.List;

/**
 *  Specifies operations for service's service.
 *
 * @author UNO TEAM
 */
public interface IServicesService {
    List<Service> listAllServices ();
    Service getOneService (Integer serviceID);
    List <Review> listServiceReviews (Integer serviceID);
}
