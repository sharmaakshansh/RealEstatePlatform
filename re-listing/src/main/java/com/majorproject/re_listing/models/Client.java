package com.majorproject.re_listing.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique=true)
    private String email;
    @NotNull
    private String password;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "client")
    private List<Property> MyListings;

    public Client(String name, String email, String password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}
