package com.rnr.SMS.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private String city;
    private String state;
    private String country;
}