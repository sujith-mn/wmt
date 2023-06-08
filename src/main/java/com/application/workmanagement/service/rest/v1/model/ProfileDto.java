package com.application.workmanagement.service.rest.v1.model;

import java.util.ArrayList;
import java.util.List;

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
	private String ProfileStatus;
	private List<Integer> demandRejectedStatus= new ArrayList<>();
	
	

	
}
