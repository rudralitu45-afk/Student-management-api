package com.rnr.SMS.Controller;

import com.rnr.SMS.Dto.StudentRequestDto;
import com.rnr.SMS.Dto.StudentResponseDto;
import com.rnr.SMS.Service.StudentService;
import com.rnr.SMS.payload.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudents() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        ApiResponse<List<StudentResponseDto>> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Students fetched successfully",
                students
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@PathVariable Long id) {
        StudentResponseDto student = studentService.getStudentById(id);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfully",
                student
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> createStudent(@Valid @RequestBody StudentRequestDto dto) {
        StudentResponseDto saved = studentService.saveStudent(dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Student created successfully",
                saved
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto dto) {
        StudentResponseDto updated = studentService.updateStudent(id, dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> patchStudent(
            @PathVariable Long id,
            @RequestBody StudentRequestDto dto) {
        StudentResponseDto updated = studentService.patchStudent(id, dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        ApiResponse<Object> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}