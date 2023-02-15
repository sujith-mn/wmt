package com.application.profile.service.rest.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
	
	private long id;
	private String name;
	private String primarySkill;
	private String location;
	private String availability;
	private String proposedBy;
	private String source;

}
