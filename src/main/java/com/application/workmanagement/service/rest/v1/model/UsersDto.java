package com.application.workmanagement.service.rest.v1.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

	@NotNull
	@Email
	private String username;
	@NotNull
	private String password;
	
}
