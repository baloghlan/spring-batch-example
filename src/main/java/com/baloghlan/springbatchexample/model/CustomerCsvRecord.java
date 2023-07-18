package com.baloghlan.springbatchexample.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCsvRecord {
    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String country;
    private String postalCode;
}
