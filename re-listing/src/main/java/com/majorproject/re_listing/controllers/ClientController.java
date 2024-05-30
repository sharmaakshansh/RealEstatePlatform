package com.majorproject.re_listing.controllers;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.models.JwtResponse;
import com.majorproject.re_listing.security.JwtHelper;
import com.majorproject.re_listing.services.ClientService;
import com.majorproject.re_listing.services.DefaultClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ClientController {

    @Autowired
    private DefaultClientService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    // private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Client> createAdmin(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);

        if (createdClient != null) {
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Client request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String login_id, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login_id, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


}
