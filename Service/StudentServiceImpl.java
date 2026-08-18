package com.rnr.Student_m_system.Service;

import com.rnr.Student_m_system.Dto.StudentRequestDto;
import com.rnr.Student_m_system.Dto.StudentResponseDto;
import com.rnr.Student_m_system.Repository.StudentRepository;
import com.rnr.Student_m_system.entity.Student;
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

    // Entity → Response DTO
    private StudentResponseDto mapToResponseDTO(Student student) {

        StudentResponseDto dto = new StudentResponseDto();

        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setCourse(student.getCourse());

        return dto;
    }

    // Request DTO → Entity
    private Student mapToEntity(StudentRequestDto dto) {

        Student student = new Student();

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setCourse(dto.getCourse());
        student.setPassword(dto.getPassword());
        student.setCreatedAt(LocalDateTime.now());

        return student;
    }

    @Override
    public StudentResponseDto saveStudent(StudentRequestDto dto) {

        Student student = mapToEntity(dto);

        Student savedStudent = studentRepository.save(student);

        return mapToResponseDTO(savedStudent);
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {

        Student student =
                studentRepository.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        return mapToResponseDTO(student);
    }

    @Override
    public StudentResponseDto updateStudent(
            Long id,
            StudentRequestDto dto) {

        Student existingStudent =
                studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setCourse(dto.getCourse());
        existingStudent.setPassword(dto.getPassword());

        Student updatedStudent =
                studentRepository.save(existingStudent);

        return mapToResponseDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {

        studentRepository.deleteById(id);
    }
}
