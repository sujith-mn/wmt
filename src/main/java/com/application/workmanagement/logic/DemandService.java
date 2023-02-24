package com.application.workmanagement.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.common.exception.ResourceNotFoundException;
import com.application.workmanagement.domain.model.Demand;
import com.application.workmanagement.domain.model.DemandsExcel;
import com.application.workmanagement.domain.repository.DemandRepository;
import com.application.workmanagement.service.rest.v1.model.DemandDto;
import com.application.workmanagement.service.rest.v1.model.ProfilesListDto;
import com.xlm.reader.SheetReader;


@Service
public class DemandService {

	@Autowired
	private DemandRepository demandRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//create demand

	public DemandDto createDemand(DemandDto demandDto) {
		Demand demand = this.dtoToDemand(demandDto);
		Demand savedDemand = this.demandRepository.save(demand);
		
		return this.demandToDto(savedDemand);
	}
	
	//Update demand.
   
	public DemandDto updateDemand(DemandDto demandDto, Integer demandId) {
		// TODO Auto-generated method stub
		Demand demand = this.demandRepository.findById(demandId)
							.orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
		
		//demand.setDemandId(demandDto.getDemandId());
		demand.setManager(demandDto.getManager());
		demand.setCreated(demandDto.getCreated());
		demand.setEndDate(demandDto.getEndDate());
		demand.setAgeing(demandDto.getAgeing());
		demand.setPriority(demandDto.getPriority());
		demand.setSkill(demandDto.getSkill());
		demand.setStatus(demandDto.getStatus());
		
		Demand updatedDemand = this.demandRepository.save(demand);
		DemandDto resultDemandDto = this.demandToDto(updatedDemand);
		return resultDemandDto;
	}
	
    
    //Read Single Demand.
    
	public DemandDto getDemandById(Integer demandId) {
		
    	Demand demand = this.demandRepository.findById(demandId)
    								   .orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
    	
		return this.demandToDto(demand);
	}

    //Read All Demands.
	
	public List<DemandDto> getAllDemands() {
		
		List<Demand> demands = this.demandRepository.findAll();
		List<DemandDto> demandDtos = demands.stream()
											.map(demand->this.demandToDto(demand))
											.collect(Collectors.toList());
		return demandDtos;
	}

	//Delete Demand By Id.
	
	public void deleteDemand(Integer demandId) {
		Demand demand = this.demandRepository.findById(demandId)
									   .orElseThrow(()-> new ResourceNotFoundException("Demand", "id", demandId));
		this.demandRepository.delete(demand);
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

	public void addProfilesToDemand(DemandDto profilesList, int demandId) {
		
		 Demand demand = demandRepository.findById(demandId)
				.orElseThrow(() -> new ResourceNotFoundException("Demand", "Id", demandId));
		
		demand.setProfiles(profilesList.getProfilesList());
		
		demandRepository.save(demand);

	}

	public void addProfilesListToDemand(ProfilesListDto profilesList, int demandId) {
		

		 Demand demand = demandRepository.findById(demandId)
				.orElseThrow(() -> new ResourceNotFoundException("Demand", "Id", demandId));
		
		demand.setProfiles(profilesList.getProfilesList());
		
		demandRepository.save(demand);

		
	}
	@Async
	public void save(MultipartFile file) {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet datatypeSheet = workbook.getSheetAt(0);
			SheetReader<DemandsExcel> sr = new SheetReader<DemandsExcel>(workbook);
			sr.setHeaderRow(0);

			List<DemandsExcel> excels = sr.retrieveRows(datatypeSheet, DemandsExcel.class);
			
			List<Demand> excel = excels.stream().map(exc -> modelMapper.map(exc, Demand.class))
					.collect(Collectors.toList());
			demandRepository.saveAll(excel);
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
		
	}
	

	
	
}