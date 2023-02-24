package com.application.workmanagement.domain.model;

import java.sql.Date;

import com.xlm.util.XLColumn;
import com.xlm.util.XLSheet;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XLSheet(name="Demands")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemandsExcel {
	@Id
	@XLColumn(name="Id")
	private int Id;
	@XLColumn(name="Manager")
	private String Manager;
	@XLColumn(name="Created")
	private Date created;
	@XLColumn(name="EndDate")
	private Date EndDate;
	@XLColumn(name="Ageing")
	private int ageing;
	@XLColumn(name="Priority")
	private int priority;
	@XLColumn(name="Skill")
	private String skill;
	@XLColumn(name="Status")
	private String status;

}
