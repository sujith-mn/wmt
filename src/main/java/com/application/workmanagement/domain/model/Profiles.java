package com.application.workmanagement.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profiles {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private String primarySkill;

	private String location;

	private String availability;

	private String proposedBy;

	private String source;
	
	@Lob
	private byte[] resume;

}