package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.CourseRequestDto;
import com.rnr.SMS.Dto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto dto);
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto getCourseById(Long id);
}