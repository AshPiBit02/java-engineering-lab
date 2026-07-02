package com.tutorial.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "students_certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long certificate;
    private String title;
    private String about;
    private String link;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
