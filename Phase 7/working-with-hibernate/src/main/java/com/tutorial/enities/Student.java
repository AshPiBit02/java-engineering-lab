package com.tutorial.enities;

import javax.annotation.processing.Generated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @Column(name="student_name",length = 100,nullable = false)
    private String name;
    
    @Column(name="student_clg",length = 150,nullable = false)
    private String college;
    
    @Column(name="student_clg",length = 150)
    private String contact;

    private String email;

    private boolean active = true;

    @Lob 
    private String about;

    
}
