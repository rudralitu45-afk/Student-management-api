package com.rnr.SMS.Service;
import com.rnr.SMS.Dto.StudentRequestDto;
import com.rnr.SMS.Dto.StudentResponseDto;
import com.rnr.SMS.Entity.Student;
import com.rnr.SMS.Repository.StudentRepository;
import com.rnr.SMS.exception.DuplicateEmailException;
import com.rnr.SMS.exception.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private StudentResponseDto mapToResponseDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setCourse(student.getCourse());
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
