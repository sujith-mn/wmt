package com.application.workmanagement.service.rest.v1;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.workmanagement.logic.ProfileService;
import com.application.workmanagement.service.rest.v1.model.ProfileDto;


@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {

	public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String PDF = "application/pdf";
	public static final String DOC = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";


	@Autowired
	private ProfileService profileService;
	


	@PostMapping("/add")
	public ResponseEntity<ProfileDto> addProfile(@RequestBody ProfileDto profile) {
		
		profileService.save(profile);
	
	return new ResponseEntity<> (profile,HttpStatus.CREATED);
	}

	@PostMapping("/excel/upload")
	public ResponseEntity<ResponseInfo> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		String message = "";
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

	@GetMapping("/{Id}")
	public ResponseEntity<ProfileDto> getProfileById(@PathVariable("Id") long id) {

		ProfileDto profile = profileService.getProfileById(id);
		return new ResponseEntity<>(profile, HttpStatus.OK);

	}

	@DeleteMapping("/{Id}")
	public ResponseEntity<String> deleteProfilesById(@PathVariable("Id") long id) {

		profileService.deleteProfilesByid(id);
		return new ResponseEntity<>("Profile Deleted", HttpStatus.ACCEPTED);

	}

	@PutMapping("/{Id}")
	public ResponseEntity<ResponseInfo> updateProfileById(@PathVariable("Id") long id,@RequestBody ProfileDto profile) {
		
		profileService.updateProfilesById(id,profile);
		ResponseInfo info = new ResponseInfo(HttpStatus.OK, profile, "profile Updated");
		return new ResponseEntity<> (info,HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<ProfileDto>> getAllProfiles() {

		List<ProfileDto> profilesList = profileService.getAllprofiles();

		return new ResponseEntity<>(profilesList, HttpStatus.OK);

	}

	@GetMapping("/by/primaryskill/{primaryskill}")
	public ResponseEntity<List<ProfileDto>> getAllProfiles(@PathVariable("primaryskill") String skill) {

		List<ProfileDto> profilesList = profileService.getAllprofilesByPrimarySkill(skill);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);

	}

	@GetMapping("/by/availability/{availability}")
	public ResponseEntity<List<ProfileDto>> getAllProfilesByAvailability(
			@PathVariable("availability") String availability) {

		List<ProfileDto> profilesList = profileService.getAllprofilesByAvailability(availability);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);

	}

	@GetMapping("/by/location/{location}")
	public ResponseEntity<List<ProfileDto>> getAllProfilesByLocation(@PathVariable("location") String location) {

		List<ProfileDto> profilesList = profileService.getAllprofilesBylocation(location);

		return new ResponseEntity<>(profilesList, HttpStatus.OK);

	}

	@GetMapping("/by/skill/{skill}/availability/{available}")
	public ResponseEntity<List<ProfileDto>> getProfilesBasedOnSkillAndAvailability(@PathVariable("skill") String skill,
			@PathVariable("available") String available) {
		List<ProfileDto> profileList = profileService.getProfilesBasedOnSkillAndAvailability(skill, available);
		return new ResponseEntity<>(profileList, HttpStatus.OK);
	}

	@GetMapping("/by/search/{search}")
	public ResponseEntity<List<ProfileDto>> getAllProfilesBySearch(@PathVariable("search") String search) {
		List<ProfileDto> profilesList = null;

		if (!profileService.getAllprofilesByPrimarySkillIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllprofilesByPrimarySkillIgnoreCase(search);
		} else if (!profileService.getAllprofilesByAvailabilityIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllprofilesByAvailabilityIgnoreCase(search);
		} else if (!profileService.getAllprofilesBylocationIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllprofilesBylocationIgnoreCase(search);
		} else if (!profileService.getAllProfilesByNameIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllProfilesByNameIgnoreCase(search);
		} else if (!profileService.getAllProfilesByProposedByIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllProfilesByProposedByIgnoreCase(search);
		} else if (!profileService.getAllProfilesBySourceIgnoreCase(search).isEmpty()) {
			profilesList = profileService.getAllProfilesBySourceIgnoreCase(search);
		}

		return new ResponseEntity<>(profilesList, HttpStatus.OK);

	}
	
	@PostMapping("/local/upload")
	public ResponseEntity<ResponseInfo> excelUpload(@RequestParam("resume") MultipartFile file) {

		String message = "";
		ResponseInfo responseInfo;

		if (PDF.equals(file.getContentType()) || DOC.equals(file.getContentType())) {
			try {
				 profileService.localSave(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				responseInfo = new ResponseInfo(HttpStatus.CREATED, file.getOriginalFilename(), message);

				return new ResponseEntity<>(responseInfo, HttpStatus.CREATED);
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				responseInfo = new ResponseInfo(HttpStatus.EXPECTATION_FAILED, file.getOriginalFilename(), message);
				return new ResponseEntity<>(responseInfo, HttpStatus.EXPECTATION_FAILED);
			}
		}

		message = "Please upload an excel file!";
		responseInfo = new ResponseInfo(HttpStatus.BAD_REQUEST, file.getOriginalFilename(), message);
		return new ResponseEntity<>(responseInfo, HttpStatus.BAD_REQUEST);
	}
	
//	@PutMapping("/{id}/resume")
//	public ResponseEntity<String> uploadResume(@RequestParam("resume") MultipartFile file, @PathVariable("id") long id)
//			throws IOException {
//		String message ="";
//		if (PDF.equals(file.getContentType()) || DOC.equals(file.getContentType())) {
//			try {
//				profileService.saveResume(file, id);
//				message = "Uploaded the file successfully: " + file.getOriginalFilename();
//				return new ResponseEntity<>(message, HttpStatus.OK);
//			} catch (Exception e) {
//				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//				return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
//			}
//		} else {
//
//			message = "Please upload an excel file!";
//			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//
//		}
//
//	}
//	
//	@GetMapping("/files/{id}")
//	  public ResponseEntity<byte[]> getFile(@PathVariable("id") long id) {
//	    Profiles profile = profileService.getFile(id);
//
//	    return ResponseEntity.ok()
//	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getName() + "\"")
//	        .body(profile.getResume());
//	  }
	}
