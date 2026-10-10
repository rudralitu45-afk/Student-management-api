package com.rnr.SMS.Dto;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressResponseDto address;
    private String departmentName;
    private List<CourseResponseDto> courses;
}