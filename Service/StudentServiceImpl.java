package com.rnr.Student_m_system.Service;

import com.rnr.Student_m_system.Repository.StudentRepository;
import com.rnr.Student_m_system.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {

        student.setCreatedAt(LocalDateTime.now());

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    @Override
    public Student getStudentById(Long id) {

        return studentRepository.findById(id).orElse(null);
    }


    @Override
    public Student updateStudent(Long id, Student student) {

        Student existingStudent =
                studentRepository.findById(id).orElse(null);

        if (existingStudent != null) {

            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setCourse(student.getCourse());
            existingStudent.setPassword(student.getPassword());

            return studentRepository.save(existingStudent);
        }

        return null;
    }

    @Override
    public void deleteStudent(Long id) {

        studentRepository.deleteById(id);
    }
}
