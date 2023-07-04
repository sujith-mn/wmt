package com.application.workmanagement.service.rest.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandProfileStatusDto {

	private int id;
	private int demandid;
	private int profileid;
	private String status;
}
