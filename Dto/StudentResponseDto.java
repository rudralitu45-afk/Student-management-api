package com.rnr.Student_m_system.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String course;
}