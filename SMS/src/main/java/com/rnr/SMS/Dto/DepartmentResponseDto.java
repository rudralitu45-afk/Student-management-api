package com.rnr.SMS.Dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    private Long id;
    private String departmentName;

    private List<StudentResponseDto> students;
}