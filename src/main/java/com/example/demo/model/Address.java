package com.example.demo.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Address {

    @NotBlank
    @Size(max = 100)
    private String street;

    @NotBlank @Size(max = 50)
    private String city;

    @NotBlank @Size(max = 20)
    private String postalCode;

    @NotBlank @Size(min = 2, max = 2)
    private String countryCode;
}



