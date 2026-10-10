package com.rnr.SMS.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    private String duration;
    private Double fees;
    private String instructorName;


    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}