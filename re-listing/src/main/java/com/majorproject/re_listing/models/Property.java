package com.majorproject.re_listing.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propId;

    private String ownerNames;

    private int numberOfOwners;

    private Long price;

    private String location;
    private String city;
    private String state;
    private String listedBy;
    private String contactPersonName;
    private String contactPersonPhn;
    private String contactPersonMail;
    private String imageURL;

    @ManyToOne
    private Client client;
}
