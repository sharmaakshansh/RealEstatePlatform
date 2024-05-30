package com.majorproject.re_listing.services;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.models.Property;
import com.majorproject.re_listing.response.PropertyDto;
import com.majorproject.re_listing.response.PropertyResponse;

import java.util.List;

public interface PropertyService {

    public PropertyDto addProperty(PropertyDto property, Client client,String email);


    PropertyDto getPropertyById(Long id);

    PropertyResponse getAllProperty(Integer pageN, Integer pageSize, String sortBy, String sortDir);


    List<PropertyDto> searchPropertiesByCity(String keyword);

    List<PropertyDto> searchPropertiesByState(String keyword);

    List<PropertyDto> getMyListings(String email);

    boolean deleteProperty(Long propId, String email);

    PropertyDto updateProperty(Long propId, PropertyDto property, Client client, String email);
}
