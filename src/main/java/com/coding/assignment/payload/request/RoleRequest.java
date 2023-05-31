package com.coding.assignment.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class RoleRequest {
    @NotBlank(message = "Please enter role name")
    private String name;
}
