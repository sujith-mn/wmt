package com.cg.application.demandmanagement.service.rest.v1.model;
import java.sql.Date;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DemandDto {
	
	private int Id;
	
	//@NotNull(message = "Demand ID Field can't be null")
	//private Integer DemandId;
	
	@NotNull(message = "Customer Manager Field can't be null")
	private String Manager;
	
	@NotNull(message = "Created On Field can't be null")
	private Date created;
	
	//new start
	@NotNull(message = "Ending Date Field can't be null")
	private Date endDate;
	
	//new end
	
	@NotNull(message = "Ageing Field can't be null")
	private Integer ageing;
	
	@NotNull(message = "Priority Field can't be null")
	private Integer priority;
	
	@NotNull(message = " Skills Field can't be null")
	private String skill;
	
	@NotNull(message = "status Field can't be null")
	private String status;

}
