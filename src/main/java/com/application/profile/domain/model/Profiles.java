package com.application.profile.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.xlm.util.XLColumn;
import com.xlm.util.XLSheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XLSheet(name="Profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profiles {
	
	@Id
	@XLColumn(name="Id")
	private long id;
	@XLColumn(name="Name")
	private String name;
	@XLColumn(name="Primary_skill")
	private String primarySkill;
	@XLColumn(name="Location")
	private String location;
	@XLColumn(name="Availability")
	private String availability;
	@XLColumn(name="Proposed_By")
	private String proposedBy;
	@XLColumn(name="Source")
	private String source;
	
	

}