package com.majorproject.re_listing.repositories;

import com.majorproject.re_listing.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property,Long> {
    List<Property> findByCityContaining(String keyword);

    List<Property> findByStateContaining(String keyword);



    List<Property> findByListedBy(String email);
}
