package com.application.workmanagement.service.rest.v1.model;

import java.util.List;

import com.application.workmanagement.domain.model.Profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilesListDto {
	
	private List<Profiles> profilesList;

}
