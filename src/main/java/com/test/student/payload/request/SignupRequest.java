package com.test.student.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 
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
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
}
