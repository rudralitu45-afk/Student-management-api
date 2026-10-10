package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.AddressResponseDto;
import com.rnr.SMS.Dto.DepartmentRequestDto;
import com.rnr.SMS.Dto.DepartmentResponseDto;
import com.rnr.SMS.Dto.StudentResponseDto;
import com.rnr.SMS.Entity.Department;
import com.rnr.SMS.Repository.DepartmentRepository;
import com.rnr.SMS.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private DepartmentResponseDto mapToResponseDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());

        List<StudentResponseDto> studentDtos = Collections.emptyList();
        if (department.getStudents() != null) {
            studentDtos = department.getStudents().stream().map(student -> {
                StudentResponseDto sDto = new StudentResponseDto();
                sDto.setId(student.getId());
                sDto.setFirstName(student.getFirstName());
                sDto.setLastName(student.getLastName());
                sDto.setEmail(student.getEmail());

                sDto.setDepartmentName(department.getDepartmentName());

                if (student.getAddress() != null) {
                    AddressResponseDto addrDto = new AddressResponseDto();
                    addrDto.setCity(student.getAddress().getCity());
                    addrDto.setState(student.getAddress().getState());
                    addrDto.setCountry(student.getAddress().getCountry());
                    sDto.setAddress(addrDto);
                }
                return sDto;
            }).toList();
        }

        dto.setStudents(studentDtos);
        return dto;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setDepartmentName(requestDto.getDepartmentName());
        Department saved = departmentRepository.save(department);
        return mapToResponseDto(saved);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return mapToResponseDto(department);
    }
}