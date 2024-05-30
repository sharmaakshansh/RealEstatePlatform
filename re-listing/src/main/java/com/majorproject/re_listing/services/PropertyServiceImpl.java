package com.majorproject.re_listing.services;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.models.Property;
import com.majorproject.re_listing.repositories.Clientrepo;
import com.majorproject.re_listing.repositories.PropertyRepo;
import com.majorproject.re_listing.response.PropertyDto;
import com.majorproject.re_listing.response.PropertyResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService{


    @Autowired
    PropertyRepo propertyRepo;

    @Autowired
    Clientrepo clientrepo;

    @Autowired
    private ModelMapper modelMapper;



    /**
     * @param id
     * @return
     */
    @Override
    public PropertyDto getPropertyById(Long id) {
        Optional<Property> optionalProperty = propertyRepo.findById(id);
        return optionalProperty.map(this::propertyTodto).orElse(null);
    }

    /**
     * @param property
     * @param client
     * @return
     */
    @Override
    public PropertyDto addProperty(PropertyDto property, Client client, String email) {
        Property property1= dtoToProperty(property);
        property1.setClient(client);
        property1.setListedBy(email);

        propertyRepo.save(property1) ;
        return  propertyTodto(property1);

    }


    public PropertyDto propertyTodto(Property property)
    {
        PropertyDto propertyDto = this.modelMapper.map(property, PropertyDto.class);
        return propertyDto;
    }

    public Property dtoToProperty(PropertyDto propertyDto)
    {
        Property property = this.modelMapper.map(propertyDto, Property.class);
        return property;

    }


    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PropertyResponse getAllProperty(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else
        {
            sort=Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize,sort );
        Page<Property> pagecustomers = this.propertyRepo.findAll(p);
        List<Property> allcustomers =pagecustomers.getContent();
        List<PropertyDto> propertydtos = allcustomers.stream()
                .map(customer -> this.modelMapper.map(customer, PropertyDto.class))
                .collect(Collectors.toList());

        PropertyResponse propertyResponse = new PropertyResponse();
        propertyResponse.setContent(propertydtos);
        propertyResponse.setPageNumber(pagecustomers.getNumber());
        propertyResponse.setPageSize(pagecustomers.getSize());
        propertyResponse.setTotalElements(pagecustomers.getTotalElements());
        propertyResponse.setTotalPages(pagecustomers.getTotalPages());
        propertyResponse.setLastPage(pagecustomers.isLast());


        return propertyResponse;
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<PropertyDto> searchPropertiesByCity(String keyword) {
        List<Property> properties = propertyRepo.findByCityContaining(keyword);
        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDto.class))
                .collect(Collectors.toList());
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<PropertyDto> searchPropertiesByState(String keyword) {
        List<Property> properties = propertyRepo.findByStateContaining(keyword);
        return properties.stream()
                .map(customer -> modelMapper.map(customer, PropertyDto.class))
                .collect(Collectors.toList());
    }


    /**
     * @param email
     * @return
     */
    @Override
    public List<PropertyDto> getMyListings(String email) {

        List<Property> properties= propertyRepo.findByListedBy(email);

        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDto.class))
                .collect(Collectors.toList());
    }

    /**
     * @param propId
     */
    @Override
    public boolean deleteProperty(Long propId, String email) {
        Optional<Property> property= propertyRepo.findById(propId);
        if(property.get().getListedBy().equals(email))
        {
            propertyRepo.deleteById(propId);
            return true;
        }
        return  false;

    }


    /**
     * @param propId
     * @param propertyDto
     * @return
     */
    @Override
    public PropertyDto updateProperty(Long propId, PropertyDto propertyDto,Client client, String email) {
        // Fetch the existing property
        Optional<Property> existingProperty = Optional.ofNullable(propertyRepo.findById(propId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propId)));

        // Update the property details

        existingProperty.get().setOwnerNames(propertyDto.getOwnerNames());
        existingProperty.get().setNumberOfOwners(propertyDto.getNumberOfOwners());
        existingProperty.get().setListedBy(email); // Always set the listedBy to the current user's email
        existingProperty.get().setPrice(propertyDto.getPrice());
        existingProperty.get().setLocation(propertyDto.getLocation());
        existingProperty.get().setCity(propertyDto.getCity());
        existingProperty.get().setState(propertyDto.getState());
        existingProperty.get().setContactPersonName(propertyDto.getContactPersonName());
        existingProperty.get().setContactPersonPhn(propertyDto.getContactPersonPhn());
        existingProperty.get().setContactPersonMail(propertyDto.getContactPersonMail());
        if (propertyDto.getImageURL() != null) {
            existingProperty.get().setImageURL(propertyDto.getImageURL());
        }
        existingProperty.get().setClient(client);

        // Save the updated property
        propertyRepo.save(existingProperty.get());

        return propertyTodto(existingProperty.orElse(null));
    }
}
