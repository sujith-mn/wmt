package com.application.workmanagement.domain.model;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Demand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int Id;
	private String manager;
	private Date created;
	private Date EndDate;
	private int ageing;
	private int priority;
	private String skill;
	private String status;
	private String Department;
	@OneToMany
	private List<Profiles> profiles;
}
