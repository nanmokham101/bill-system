package com.test.student.dto;

import com.test.student.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    @NotNull(message = "Please enter student name.")
    private String name;
    @NotNull(message = "Please enter student father name.")
    private String fatherName;
    private String address;
    @NotNull(message = "Please enter email.")
    private String email;
    @NotNull(message = "Please enter student phone number.")
    @Phone
    private String phoneNumber;
}
