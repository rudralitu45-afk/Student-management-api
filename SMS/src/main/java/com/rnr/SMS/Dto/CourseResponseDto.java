package com.rnr.SMS.Dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseName;
    private String duration;
    private Double fees;
    private String instructorName;
}