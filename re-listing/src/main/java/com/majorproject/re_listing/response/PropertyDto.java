package com.majorproject.re_listing.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PropertyDto {

    private Long propId;

    private String OwnerNames;

    private int NumberOfOwners;
    private String ListedBy;

    private Long Price;
    private String Location;
    private String City;
    private String State;
    private String ContactPersonName;
    private String ContactPersonPhn;
    private String ContactPersonMail;
    private String imageURL;
}
