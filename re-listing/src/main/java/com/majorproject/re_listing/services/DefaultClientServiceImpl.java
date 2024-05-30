package com.majorproject.re_listing.services;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.repositories.Clientrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DefaultClientServiceImpl implements DefaultClientService{

    @Autowired
    private Clientrepo clientrepo;

    /**
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientrepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(client.getEmail(), client.getPassword(), new ArrayList<>());

    }
}
