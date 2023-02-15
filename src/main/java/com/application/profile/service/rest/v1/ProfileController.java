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

import com.application.profile.domain.model.Profile;
import com.application.profile.logic.ProfileService;
import com.application.profile.service.rest.v1.model.ProfileDto;



@CrossOrigin(origins="*")
@RestController
@RequestMapping("/profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/add")
	public ResponseEntity<Profile> addProfile(@RequestBody ProfileDto profile) {
		
		Profile addedProfile = new Profile();
		
		addedProfile = profileService.save(profile);
		
		return new ResponseEntity<> (addedProfile,HttpStatus.CREATED);
	}
	
	@PostMapping("/excel/upload")
	public ResponseEntity<ResponseInfo> handleFileUpload(
			@RequestParam("file") MultipartFile file)
			throws Exception {
		String message = "upload success";
		ResponseInfo responseInfo;
		profileService.save(file);

		responseInfo = new ResponseInfo(HttpStatus.OK, file.getOriginalFilename(), message);
		return new ResponseEntity<>(responseInfo, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Profile>> getAllProfiles(){
		
		List<Profile> profilesList = profileService.getAllprofiles();
		
		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/primaryskill/{primaryskill}")
	public ResponseEntity<List<Profile>> getAllProfiles(@PathVariable("primaryskill") String skill){
		
		List<Profile> profilesList = profileService.getAllprofilesByPrimarySkill(skill);
	
		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/availability/{availability}")
	public ResponseEntity<List<Profile>> getAllProfilesByAvailability(@PathVariable("availability") String availability){
		
		List<Profile> profilesList = profileService.getAllprofilesByAvailability(availability);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/location/{location}")
	public ResponseEntity<List<Profile>> getAllProfilesByLocation(@PathVariable("location") String location){
		
		List<Profile> profilesList = profileService.getAllprofilesBylocation(location);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);
		
	}
	
	@GetMapping("/by/search/{search}")
	public ResponseEntity<List<Profile>> getAllProfilesBySearch(@PathVariable("search") String search) {
		List<Profile> profilesList = null;
		
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