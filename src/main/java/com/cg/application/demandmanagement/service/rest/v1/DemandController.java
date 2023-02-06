package com.cg.application.demandmanagement.service.rest.v1;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.application.demandmanagement.logic.DemandService;
import com.cg.application.demandmanagement.service.rest.v1.model.DemandDto;


@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/demands")
public class DemandController {

	@Autowired
	private DemandService demandService;
	
	//create demand
	@PostMapping("/")
	public ResponseEntity<DemandDto> createDemand(@Valid @RequestBody DemandDto demandDto){
		
		DemandDto createDemandDto = this.demandService.createDemand(demandDto);
		return new ResponseEntity<>(createDemandDto,HttpStatus.CREATED);
	}
	
	
	//update demand 
	
	@PutMapping("/{demandId}")
	public ResponseEntity<DemandDto> updateDemand(@Valid @RequestBody DemandDto demandDto,@PathVariable("demandId") Integer demandId ){
		DemandDto updatedDemand = this.demandService.updateDemand(demandDto, demandId);
		
		return ResponseEntity.ok(updatedDemand);
		
	}
	
	//Delete Demand
	@DeleteMapping("/{demandId}")
	public ResponseEntity<?> deleteDemand(@PathVariable("demandId") Integer demandId){
		this.demandService.deleteDemand(demandId);
		return new ResponseEntity(Map.of("Message","Demand Deleted Successfully"),HttpStatus.OK);
		
	}
	
	//Get All Demands
	@GetMapping("/")
	public ResponseEntity<List<DemandDto>> getAllDemands(){
		return ResponseEntity.ok(this.demandService.getAllDemands());
	}
	
	//Get single Demand
	@GetMapping("/{demandId}")
	public ResponseEntity<DemandDto> getSingleDemand(@PathVariable("demandId") Integer demandId){
		
		return ResponseEntity.ok(this.demandService.getDemandById(demandId));
	}
}
	

