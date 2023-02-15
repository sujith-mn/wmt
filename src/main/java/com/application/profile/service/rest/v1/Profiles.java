package com.application.profile.service.rest.v1;

import com.xlm.util.XLColumn;
import com.xlm.util.XLSheet;

@XLSheet(name="Profiles")
public class Profiles {
	
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
	
	
	public Profiles(long id, String name, String primarySkill, String location, String availability, String proposedBy,
			String source) {
		super();
		this.id = id;
		this.name = name;
		this.primarySkill = primarySkill;
		this.location = location;
		this.availability = availability;
		this.proposedBy = proposedBy;
		this.source = source;
	}
	
	
	public Profiles() {
		
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrimarySkill() {
		return primarySkill;
	}
	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getProposedBy() {
		return proposedBy;
	}
	public void setProposedBy(String proposedBy) {
		this.proposedBy = proposedBy;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}


	@Override
	public String toString() {
		return "Profiles [id=" + id + ", name=" + name + ", primarySkill=" + primarySkill + ", location=" + location
				+ ", availability=" + availability + ", proposedBy=" + proposedBy + ", source=" + source + "]";
	}
	
	
	
	
	

}