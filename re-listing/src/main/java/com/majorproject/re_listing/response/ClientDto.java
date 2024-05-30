package com.majorproject.re_listing.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClientDto {

    private Long id;

    private String name;
    private String email;
    //private String password;
    private String address;
    private String phone;
}
