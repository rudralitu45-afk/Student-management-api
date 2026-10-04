package com.rnr.SMS.Service;


import com.rnr.SMS.Dto.StudentRequestDto;
import com.rnr.SMS.Dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    StudentResponseDto saveStudent(StudentRequestDto dto);
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto getStudentById(Long id);
    void deleteStudent(Long id);
    StudentResponseDto updateStudent(Long id, StudentRequestDto dto);
    StudentResponseDto patchStudent(Long id, StudentRequestDto dto);
}