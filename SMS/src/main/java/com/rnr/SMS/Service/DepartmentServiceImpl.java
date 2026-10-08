package com.rnr.SMS.Service;

import com.rnr.SMS.Dto.DepartmentRequestDto;
import com.rnr.SMS.Dto.DepartmentResponseDto;
import com.rnr.SMS.Entity.Department;
import com.rnr.SMS.Repository.DepartmentRepository;
import com.rnr.SMS.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDepartmentName(requestDto.getDepartmentName());
        Department saved = departmentRepository.save(department);
        return new DepartmentResponseDto(saved.getId(), saved.getDepartmentName());
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(dept -> new DepartmentResponseDto(dept.getId(), dept.getDepartmentName()))
                .toList();
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return new DepartmentResponseDto(department.getId(), department.getDepartmentName());
    }
}