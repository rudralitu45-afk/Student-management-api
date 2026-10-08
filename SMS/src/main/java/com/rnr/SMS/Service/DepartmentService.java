package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.DepartmentRequestDto;
import com.rnr.SMS.Dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);
    List<DepartmentResponseDto> getAllDepartments();
    DepartmentResponseDto getDepartmentById(Long id);
}