package com.application.workmanagement.service.rest.v1.model;

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
	private String path;
	
	@Override
	public String toString() {
		return "ProfileDto [id=" + id + ", name=" + name + ", primarySkill=" + primarySkill + ", location=" + location
				+ ", availability=" + availability + ", proposedBy=" + proposedBy + ", source=" + source + ", path="
				+ path + "]";
	}

	
}
