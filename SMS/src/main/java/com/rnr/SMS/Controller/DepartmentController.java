package com.rnr.SMS.Controller;
import com.rnr.SMS.Dto.DepartmentRequestDto;
import com.rnr.SMS.Dto.DepartmentResponseDto;
import com.rnr.SMS.Service.DepartmentService;
import com.rnr.SMS.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(
            @Valid @RequestBody DepartmentRequestDto requestDto) {
        DepartmentResponseDto response = departmentService.createDepartment(requestDto);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Department created successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getAllDepartments() {
        List<DepartmentResponseDto> list = departmentService.getAllDepartments();
        ApiResponse<List<DepartmentResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Departments fetched successfully",
                list
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDto response = departmentService.getDepartmentById(id);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Department fetched successfully",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }
}