package com.application.workmanagement.service.rest.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.workmanagement.logic.DemandService;
import com.application.workmanagement.service.rest.v1.model.DemandDto;
import com.application.workmanagement.service.rest.v1.model.ProfileDto;
import com.application.workmanagement.service.rest.v1.model.ProfilesListDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/demands")
@CrossOrigin(origins = "*") 
public class DemandController {

	public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	@Autowired
	private DemandService demandService;
	
	private ResponseInfo responseInfo;
	
	//create demand
	@PostMapping("/")
	public ResponseEntity<DemandDto> createDemand(@Valid @RequestBody DemandDto demandDto){
		
		DemandDto createDemandDto = this.demandService.createDemand(demandDto);
		return new ResponseEntity<>(createDemandDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/excel/upload")
	public ResponseEntity<ResponseInfo> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		String message = "";
		ResponseInfo responseInfo;
		if (TYPE.equals(file.getContentType())) {
			message = "upload success";
			demandService.save(file);

			responseInfo = new ResponseInfo(HttpStatus.OK, file.getOriginalFilename(), message);
			return new ResponseEntity<>(responseInfo, HttpStatus.OK);

		}

		else {

			message = "Please upload an excel file!";
			responseInfo = new ResponseInfo(HttpStatus.BAD_REQUEST, file.getOriginalFilename(), message);
			return new ResponseEntity<>(responseInfo, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//update demand 
	
	@PutMapping("/{demandId}")
	public ResponseEntity<DemandDto> updateDemand(@Valid @RequestBody DemandDto demandDto,@PathVariable("demandId") Integer demandId ){
		DemandDto updatedDemand = this.demandService.updateDemand(demandDto, demandId);
		
		return ResponseEntity.ok(updatedDemand);
		
	}
	
	//Delete Demand
	@DeleteMapping("/{demandId}")
	public ResponseEntity<Object> deleteDemand(@PathVariable("demandId") Integer demandId){
		this.demandService.deleteDemand(demandId);
		return new ResponseEntity<>(Map.of("Message","Demand Deleted Successfully"),HttpStatus.OK);
		
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

	@GetMapping("/profilelist/{Id}")
	public ResponseEntity<List<ProfileDto>> getProfilesFromDemand(@PathVariable("Id") int demandId){
		List<ProfileDto> profiles = demandService.getProfilesFromDemand(demandId);
		return new ResponseEntity<> (profiles,HttpStatus.OK);
	}
	
	@PatchMapping("/profileslist/{Id}")
	public ResponseEntity<ResponseInfo> addProfiles(@RequestBody DemandDto profilesList, @PathVariable("Id") int demandId){
		
		demandService.addProfilesToDemand(profilesList,demandId);
		
		responseInfo = new ResponseInfo(HttpStatus.OK,profilesList,"Profiles added to demand");
		
		return new ResponseEntity<>(responseInfo,HttpStatus.OK);
	}
	@PutMapping("/profilelist/{Id}")
	public ResponseEntity<ResponseInfo> addProfiles(@RequestBody ProfilesListDto profilesList, @PathVariable("Id") int demandId){
		
		demandService.addProfilesListToDemand(profilesList,demandId);
		
		responseInfo = new ResponseInfo(HttpStatus.OK,profilesList,"Profiles added to demand");
		
		return new ResponseEntity<>(responseInfo,HttpStatus.OK);
	}
}
	

