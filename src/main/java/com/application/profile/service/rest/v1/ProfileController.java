package com.application.profile.service.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.profile.domain.model.Profiles;
import com.application.profile.logic.ProfileService;
import com.application.profile.service.rest.v1.model.ProfileDto;



@CrossOrigin(origins="*")
@RestController
@RequestMapping("/profiles")
public class ProfileController {
	
	public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/add")
	public ResponseEntity<Profiles> addProfile(@RequestBody ProfileDto profile) {
		
		Profiles addedProfile = new Profiles();
		
		addedProfile = profileService.save(profile);
		
		return new ResponseEntity<> (addedProfile,HttpStatus.CREATED);
	}
	
	@PostMapping("/excel/upload")
	public ResponseEntity<ResponseInfo> handleFileUpload(
			@RequestParam("file") MultipartFile file)
			throws Exception {
		String message ="";
		ResponseInfo responseInfo;
		if (TYPE.equals(file.getContentType())) {
			message = "upload success";
			profileService.save(file);

			responseInfo = new ResponseInfo(HttpStatus.OK, file.getOriginalFilename(), message);
			return new ResponseEntity<>(responseInfo, HttpStatus.OK);
			
		}
		
		else {

		message = "Please upload an excel file!";
		responseInfo = new ResponseInfo(HttpStatus.BAD_REQUEST, file.getOriginalFilename(), message);
		return new ResponseEntity<>(responseInfo, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping()
	public ResponseEntity<List<Profiles>> getAllProfiles(){
		
		List<Profiles> profilesList = profileService.getAllprofiles();
		
		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/primaryskill/{primaryskill}")
	public ResponseEntity<List<Profiles>> getAllProfiles(@PathVariable("primaryskill") String skill){
		
		List<Profiles> profilesList = profileService.getAllprofilesByPrimarySkill(skill);
	
		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/availability/{availability}")
	public ResponseEntity<List<Profiles>> getAllProfilesByAvailability(@PathVariable("availability") String availability){
		
		List<Profiles> profilesList = profileService.getAllprofilesByAvailability(availability);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/location/{location}")
	public ResponseEntity<List<Profiles>> getAllProfilesByLocation(@PathVariable("location") String location){
		
		List<Profiles> profilesList = profileService.getAllprofilesBylocation(location);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/search/{search}")
	public ResponseEntity<List<Profiles>> getAllProfilesBySearch(@PathVariable("search") String search) {
		List<Profiles> profilesList = null;
		
		if(!profileService.getAllprofilesByPrimarySkill(search).isEmpty()) {
			profilesList = profileService.getAllprofilesByPrimarySkill(search);
		}
		else if(!profileService.getAllprofilesByAvailability(search).isEmpty()) {
			profilesList = profileService.getAllprofilesByAvailability(search);
		}
		else if(!profileService.getAllprofilesBylocation(search).isEmpty()) {
			profilesList = profileService.getAllprofilesBylocation(search);
		}
		else if(!profileService.getAllProfilesByName(search).isEmpty()) {
			profilesList = profileService.getAllProfilesByName(search);
		}
		else if(!profileService.getAllProfilesByProposedBy(search).isEmpty()) {
			profilesList = profileService.getAllProfilesByProposedBy(search);
		}
		else if(!profileService.getAllProfilesBySource(search).isEmpty()) {
			profilesList = profileService.getAllProfilesBySource(search);
		}
		
		return new ResponseEntity<>(profilesList, HttpStatus.OK);

		
	}
	
	
 
}