package com.rnr.SMS.Service;


import com.rnr.SMS.Dto.CourseRequestDto;
import com.rnr.SMS.Dto.CourseResponseDto;
import com.rnr.SMS.Entity.Course;
import com.rnr.SMS.Repository.CourseRepository;
import com.rnr.SMS.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private CourseResponseDto mapToResponseDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getCourseName(),
                course.getDuration(),
                course.getFees(),
                course.getInstructorName()
        );
    }


    @Override
    public CourseResponseDto createCourse(CourseRequestDto dto) {
        Course course = new Course();
        course.setCourseName(dto.getCourseName());
        course.setDuration(dto.getDuration());
        course.setFees(dto.getFees());
        course.setInstructorName(dto.getInstructorName());

        Course saved = courseRepository.save(course);
        return mapToResponseDto(saved);
    }


    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return mapToResponseDto(course);
    }
}