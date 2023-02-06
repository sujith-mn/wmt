package com.cg.application.demandmanagement.domain.model;

import java.sql.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Demand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
//	@Column(name = "DemandID")
//	private Integer demandId;
	
	@Column(name = "Manager")
	private String Manager;
	
	@Column(name = "Created")
	private Date created;
	
	//new start
	@Column(name="EndDate")
	private Date EndDate;
	//new end
	
	private Integer ageing;
	
	private Integer priority;
	
	private String skill;
	
	@Column(name = "status")
	private String status;
}
