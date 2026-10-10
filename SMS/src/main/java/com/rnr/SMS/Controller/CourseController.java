package com.rnr.SMS.Controller;

import com.rnr.SMS.Dto.CourseRequestDto;
import com.rnr.SMS.Dto.CourseResponseDto;
import com.rnr.SMS.Service.CourseService;
import com.rnr.SMS.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(
            @Valid @RequestBody CourseRequestDto dto) {
        CourseResponseDto response = courseService.createCourse(dto);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Course created successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getAllCourses() {
        List<CourseResponseDto> list = courseService.getAllCourses();
        ApiResponse<List<CourseResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Courses fetched successfully",
                list
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(@PathVariable Long id) {
        CourseResponseDto response = courseService.getCourseById(id);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Course fetched successfully",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }
}