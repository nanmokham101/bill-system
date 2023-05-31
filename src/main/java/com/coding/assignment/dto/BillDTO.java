package com.coding.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private String date_time;
    private String status_message;

   // private User userId;
}
