package com.coding.assignment.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;
 @Getter
 @Setter
public class SignupRequest {
    @NotBlank(message = "Please enter user name.")
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank(message = "Please enter email.")
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank(message = "Please enter password.")
    @Size(min = 6, max = 40)
    private String password;
  
 }
