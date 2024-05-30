package com.majorproject.re_listing.repositories;

import com.majorproject.re_listing.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Clientrepo extends JpaRepository<Client,Long> {

    Client findByEmail(String email);
}
