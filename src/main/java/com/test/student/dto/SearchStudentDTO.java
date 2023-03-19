package com.test.student.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchStudentDTO {
    private String name;
    private String fatherName;
    private String address;
    private String email;
}
