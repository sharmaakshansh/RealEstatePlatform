package com.majorproject.re_listing.services;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.response.ClientDto;

public interface ClientService {

    Client createClient(Client client);

    ClientDto getmyprofile(String email);
}
