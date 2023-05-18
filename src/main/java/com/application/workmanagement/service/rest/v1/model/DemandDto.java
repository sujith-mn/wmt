package com.application.workmanagement.service.rest.v1.model;
import java.sql.Date;
import java.util.List;

import com.application.workmanagement.domain.model.Profiles;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandDto {
	
	private int Id;
	
	//@NotNull(message = "Demand ID Field can't be null")
	//private Integer DemandId;
	
	@NotNull(message = "Customer Manager Field can't be null")
	private String manager;
	
	@NotNull(message = "Created On Field can't be null")
	private Date created;
	
	//new start
	private Date endDate;
	
	//new end
	
	private int ageing;
	
	@NotNull(message = "Priority Field can't be null")
	private Integer priority;
	
	@NotNull(message = " Skills Field can't be null")
	private String skill;
	
	@NotNull(message = "status Field can't be null")
	private String status;
	
	@NotNull(message = "department Field can't be null")
	private String department;
	
	private List<Profiles> profilesList;

	

	

}
