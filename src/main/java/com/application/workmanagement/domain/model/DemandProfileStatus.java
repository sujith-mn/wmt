package com.application.workmanagement.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemandProfileStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
 	private int id;
	private int demandid;
	private long profileid;
	private String status;
}
