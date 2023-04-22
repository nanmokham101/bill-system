package com.test.student.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "Please enter username.")
	private String username;

	@NotBlank(message = "Please enter password.")
	private String password;

}
