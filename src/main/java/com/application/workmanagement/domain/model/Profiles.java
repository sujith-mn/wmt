package com.application.workmanagement.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profiles {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;

	private String primarySkill;

	private String location;

	private String availability;

	private String proposedBy;

	private String source;
	
	private String path;
	
	private String ProfileStatus;
	
	
	private List<Integer> demandRejectedStatus = new ArrayList<>();
	
//	@Lob
//	private byte[] resume;

}