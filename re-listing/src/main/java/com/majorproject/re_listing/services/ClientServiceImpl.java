package com.majorproject.re_listing.services;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.repositories.Clientrepo;
import com.majorproject.re_listing.response.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private final Clientrepo clientrepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public ClientServiceImpl(Clientrepo clientrepo, PasswordEncoder passwordEncoder) {
        this.clientrepo = clientrepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param client
     * @return
     */
    @Override
    public Client createClient(Client client) {

        String encodedPassword = passwordEncoder.encode(client.getPassword());
        Client client1= new Client(client.getName(),client.getEmail(),encodedPassword, client.getAddress(),client.getPhone());

        return clientrepo.save(client1);
    }



    /**
     * @param email
     * @return
     */
    @Override
    public ClientDto getmyprofile(String email) {
        Client client=clientrepo.findByEmail(email);
        ClientDto clientDto= clientTodto(client);
        return clientDto;
    }




    public ClientDto clientTodto(Client client)
    {
        ClientDto clientDto = this.modelMapper.map(client, ClientDto.class);
        return clientDto;
    }

    public Client dtoToclient(ClientDto clientDto)
    {
        Client client = this.modelMapper.map(clientDto, Client.class);
        return client;

    }
}
