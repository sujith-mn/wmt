package com.application.profile.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	
	@Id
	private long id;
	private String name;
	private String primarySkill;
	private String location;
	private String availability;
	private String proposedBy;
	private String source;

}
