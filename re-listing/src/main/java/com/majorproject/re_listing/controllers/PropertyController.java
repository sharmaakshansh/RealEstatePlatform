package com.majorproject.re_listing.controllers;

import com.majorproject.re_listing.models.Client;
import com.majorproject.re_listing.repositories.Clientrepo;
import com.majorproject.re_listing.response.ClientDto;
import com.majorproject.re_listing.response.PropertyDto;
import com.majorproject.re_listing.response.PropertyResponse;
import com.majorproject.re_listing.services.ClientService;
import com.majorproject.re_listing.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/prop")
public class PropertyController {


 @Autowired
 private final PropertyService propertyService;

 @Autowired
 private Clientrepo clientrepo;

 @Autowired
 private ClientService clientService;

    @Value("${project.image}")
    private String FOLDER_PATH;


    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @PostMapping("/add")
    public ResponseEntity<PropertyDto> createProperty(@ModelAttribute PropertyDto property,@RequestParam("image") MultipartFile file, Principal principal) throws IOException {
        String email=principal.getName();
        Client client=clientrepo.findByEmail(email);
        if (file.isEmpty())
        {
           System.out.println("No image file");
        }
        else{
            String filePath = FOLDER_PATH + file.getOriginalFilename();
            file.transferTo(new File(filePath));
           property.setImageURL(FOLDER_PATH + file.getOriginalFilename());

        }
        PropertyDto createdProperty = propertyService.addProperty(property,client,email);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }



    @GetMapping("/myprofile")
    public ResponseEntity<ClientDto> getprofile(Principal principal)
    {
        String email= principal.getName();
        ClientDto clientDto=clientService.getmyprofile(email);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("/{prop_id}")
    public ResponseEntity<PropertyDto> getCustomerById(@PathVariable Long prop_id) {
        PropertyDto propertyDto = propertyService.getPropertyById(prop_id);
        if (propertyDto != null) {
            return new ResponseEntity<>(propertyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<PropertyResponse> getAllProperty(
            @RequestParam(value = "pageNumber",defaultValue ="0",required = false) Integer pageN,
            @RequestParam(value = "pageSize",defaultValue ="5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "propId", required = false ) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
    ) {
        PropertyResponse propertyResponse = this.propertyService.getAllProperty(pageN,pageSize,sortBy,sortDir);
        return new ResponseEntity<PropertyResponse>(propertyResponse , HttpStatus.OK);
    }


    @GetMapping("/searchByCity/{keyword}")
    public ResponseEntity<List<PropertyDto>> searchpropertyByCity(
            @PathVariable("keyword") String keyword) {
        List<PropertyDto> result = propertyService.searchPropertiesByCity(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/searchByState/{keyword}")
    public ResponseEntity<List<PropertyDto>> searchpropertyByState(
            @PathVariable("keyword") String keyword) {
        List<PropertyDto> result = propertyService.searchPropertiesByState(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/myListings")
    public ResponseEntity<List<PropertyDto>> getMylistings(Principal principal)
    {
        String email= principal.getName();
        List<PropertyDto> myListings= propertyService.getMyListings(email);
        return new ResponseEntity<>(myListings, HttpStatus.OK);
    }

    @DeleteMapping("/{propId}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long propId,Principal principal) {
        String email= principal.getName();
        boolean deleted= propertyService.deleteProperty(propId,email);
        if(deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/update/{propId}")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable Long propId, @ModelAttribute PropertyDto property
            , @RequestParam(value = "image", required = false) MultipartFile file, Principal principal) throws IOException {
        String email = principal.getName();
        Client client = clientrepo.findByEmail(email);
        if (file != null && !file.isEmpty()) {
            String filePath = FOLDER_PATH + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            property.setImageURL(FOLDER_PATH + file.getOriginalFilename());
        }
        PropertyDto updatedProperty = propertyService.updateProperty(propId, property, client, email);
        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }
}






