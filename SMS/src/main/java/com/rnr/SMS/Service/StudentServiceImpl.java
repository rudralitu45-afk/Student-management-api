package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.AddressResponseDto;
import com.rnr.SMS.Dto.StudentRequestDto;
import com.rnr.SMS.Dto.StudentResponseDto;
import com.rnr.SMS.Entity.Address;
import com.rnr.SMS.Entity.Department;
import com.rnr.SMS.Entity.Student;
import com.rnr.SMS.Repository.DepartmentRepository;
import com.rnr.SMS.Repository.StudentRepository;
import com.rnr.SMS.exception.DuplicateEmailException;
import com.rnr.SMS.exception.ResourceNotFoundException;
import com.rnr.SMS.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    private StudentResponseDto mapToResponseDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setCourse(student.getCourse());

        if (student.getAddress() != null) {
            AddressResponseDto addrDto = new AddressResponseDto();
            addrDto.setCity(student.getAddress().getCity());
            addrDto.setState(student.getAddress().getState());
            addrDto.setCountry(student.getAddress().getCountry());
            dto.setAddress(addrDto);
        }

        if (student.getDepartment() != null) {
            dto.setDepartmentName(student.getDepartment().getDepartmentName());
        }

        return dto;
    }

    @Override
    public StudentResponseDto saveStudent(StudentRequestDto dto) {
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("Student already exists with email: " + dto.getEmail());
        }

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setCourse(dto.getCourse());
        student.setPassword(dto.getPassword());
        student.setCreatedAt(LocalDateTime.now());

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setCountry(dto.getAddress().getCountry());
            student.setAddress(address);
        }


        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
        student.setDepartment(department);

        Student saved = studentRepository.save(student);
        return mapToResponseDto(saved);
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return mapToResponseDto(student);
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (studentRepository.existsByEmail(dto.getEmail()) && !existing.getEmail().equals(dto.getEmail())) {
            throw new DuplicateEmailException("Student already exists with email: " + dto.getEmail());
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setCourse(dto.getCourse());
        existing.setPassword(dto.getPassword());

        if (dto.getAddress() != null) {
            Address address = existing.getAddress();
            if (address == null) address = new Address();
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setCountry(dto.getAddress().getCountry());
            existing.setAddress(address);
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
        existing.setDepartment(department);

        Student updated = studentRepository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public StudentResponseDto patchStudent(Long id, StudentRequestDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (dto.getEmail() != null && studentRepository.existsByEmail(dto.getEmail()) && !existing.getEmail().equals(dto.getEmail())) {
            throw new DuplicateEmailException("Student already exists with email: " + dto.getEmail());
        }

        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getCourse() != null) existing.setCourse(dto.getCourse());
        if (dto.getPassword() != null) existing.setPassword(dto.getPassword());

        if (dto.getAddress() != null) {
            Address address = existing.getAddress();
            if (address == null) address = new Address();
            if (dto.getAddress().getCity() != null) address.setCity(dto.getAddress().getCity());
            if (dto.getAddress().getState() != null) address.setState(dto.getAddress().getState());
            if (dto.getAddress().getCountry() != null) address.setCountry(dto.getAddress().getCountry());
            existing.setAddress(address);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            existing.setDepartment(department);
        }

        Student updated = studentRepository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }
}