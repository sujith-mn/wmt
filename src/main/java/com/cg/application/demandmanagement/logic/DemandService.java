package com.cg.application.demandmanagement.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.application.common.exceptions.ResourceNotFoundException;
import com.cg.application.demandmanagement.domain.model.Demand;
import com.cg.application.demandmanagement.domain.repository.DemandRepo;
import com.cg.application.demandmanagement.service.rest.v1.model.DemandDto;

@Service
public class DemandService {

	@Autowired
	private DemandRepo demandRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//create demand

	public DemandDto createDemand(DemandDto demandDto) {
		Demand demand = this.dtoToDemand(demandDto);
		Demand savedDemand = this.demandRepo.save(demand);
		
		return this.demandToDto(savedDemand);
	}
	
	//Update demand.
   
	public DemandDto updateDemand(DemandDto demandDto, Integer demandId) {
		// TODO Auto-generated method stub
		Demand demand = this.demandRepo.findById(demandId)
							.orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
		
		//demand.setDemandId(demandDto.getDemandId());
		demand.setManager(demandDto.getManager());
		demand.setCreated(demandDto.getCreated());
		demand.setEndDate(demandDto.getEndDate());
		demand.setAgeing(demandDto.getAgeing());
		demand.setPriority(demandDto.getPriority());
		demand.setSkill(demandDto.getSkill());
		demand.setStatus(demandDto.getStatus());
		
		Demand updatedDemand = this.demandRepo.save(demand);
		DemandDto resultDemandDto = this.demandToDto(updatedDemand);
		return resultDemandDto;
	}
	
    
    //Read Single Demand.
    
	public DemandDto getDemandById(Integer demandId) {
		
    	Demand demand = this.demandRepo.findById(demandId)
    								   .orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
    	
		return this.demandToDto(demand);
	}

    //Read All Demands.
	
	public List<DemandDto> getAllDemands() {
		
		List<Demand> demands = this.demandRepo.findAll();
		List<DemandDto> demandDtos = demands.stream()
											.map(demand->this.demandToDto(demand))
											.collect(Collectors.toList());
		return demandDtos;
	}

	//Delete Demand By Id.
	
	public void deleteDemand(Integer demandId) {
		Demand demand = this.demandRepo.findById(demandId)
									   .orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
		this.demandRepo.delete(demand);
	}

    private Demand dtoToDemand(DemandDto demandDto) {
		
		Demand demand = this.modelMapper.map(demandDto, Demand.class);
//		Demand demand = new Demand();
//		demand.setId(demandDto.getId());
//		demand.setDemandId(demandDto.getDemandId());
//		demand.setCustomerManager(demandDto.getCustomerManager());
//		demand.setCreatedOn(demandDto.getCreatedOn());
//		demand.setAgeing(demandDto.getAgeing());
//		demand.setPriority(demandDto.getPriority());
//		demand.setSkill(demandDto.getSkill());
//		demand.setStatus(demandDto.getStatus());
		
		return demand;
		
	}
	          
	public DemandDto demandToDto(Demand demand) {
		
		DemandDto demandDto = this.modelMapper.map(demand, DemandDto.class);
		
//		DemandDto demandDto = new DemandDto();
//		demandDto.setId(demand.getId());
//		demandDto.setDemandId(demand.getDemandId());
//		demandDto.setCustomerManager(demand.getCustomerManager());
//		demandDto.setCreatedOn(demand.getCreatedOn());
//		demandDto.setAgeing(demand.getAgeing());
//		demandDto.setPriority(demand.getPriority());
//		demandDto.setSkill(demand.getSkill());
//		demandDto.setStatus(demand.getStatus());
		
		return demandDto;
	}

	

	
	
}