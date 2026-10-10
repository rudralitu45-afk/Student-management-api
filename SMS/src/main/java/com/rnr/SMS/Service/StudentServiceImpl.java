package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.AddressResponseDto;
import com.rnr.SMS.Dto.CourseResponseDto;
import com.rnr.SMS.Dto.StudentRequestDto;
import com.rnr.SMS.Dto.StudentResponseDto;
import com.rnr.SMS.Entity.Address;
import com.rnr.SMS.Entity.Course;
import com.rnr.SMS.Entity.Department;
import com.rnr.SMS.Entity.Student;
import com.rnr.SMS.Repository.CourseRepository;
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
    private final CourseRepository courseRepository;

    private StudentResponseDto mapToResponseDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());

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

        if (student.getCourses() != null) {
            List<CourseResponseDto> courseDtos = student.getCourses().stream().map(c ->
                    new CourseResponseDto(c.getId(), c.getCourseName(), c.getDuration(), c.getFees(), c.getInstructorName())
            ).toList();
            dto.setCourses(courseDtos);
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

        if (dto.getCourseIds() != null && !dto.getCourseIds().isEmpty()) {
            List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
            student.setCourses(courses);
        }

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

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());

        if (dto.getCourseIds() != null) {
            List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
            existing.setCourses(courses);
        }

        Student updated = studentRepository.save(existing);
        return mapToResponseDto(updated);
    }

    @Override
    public StudentResponseDto patchStudent(Long id, StudentRequestDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getCourseIds() != null) {
            existing.setCourses(courseRepository.findAllById(dto.getCourseIds()));
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