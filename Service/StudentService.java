package com.rnr.Student_m_system.Service;

import com.rnr.Student_m_system.Dto.StudentRequestDto;
import com.rnr.Student_m_system.Dto.StudentResponseDto;

import java.util.List;

public interface StudentService {

    StudentResponseDto saveStudent(StudentRequestDto dto);

    List<StudentResponseDto> getAllStudents();

    StudentResponseDto getStudentById(Long id);

    StudentResponseDto updateStudent(Long id, StudentRequestDto dto);

    void deleteStudent(Long id);
}