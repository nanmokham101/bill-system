package com.test.student.models;

import com.test.student.validation.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="student")
public class Student implements Serializable {
    private static final long serialVersionUID = -2575029885669493400L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String fatherName;
    private String address;
    private String phoneNumber;
    private String email;
}
