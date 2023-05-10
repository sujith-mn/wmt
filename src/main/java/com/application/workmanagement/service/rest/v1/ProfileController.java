package com.application.workmanagement.service.rest.v1;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.application.common.exception.ResumeAlreadyExistsException;
import com.application.common.exception.ResumeNotFoundException;
import com.application.common.exception.ResumeSizeLimitExceededException;
import com.application.workmanagement.domain.repository.ProfileRepository;
import com.application.workmanagement.logic.ProfileService;
import com.application.workmanagement.service.rest.v1.model.DemandDto;
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
	
	@Autowired
	private ProfileRepository profileRepo;
	

	@Autowired
	private ModelMapper modelMapper;

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
	public ResponseEntity<List<ProfileDto>> getAllProfiles(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

		List<ProfileDto> profilesList = profileService.getAllprofiles(page,size);

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
	
	@GetMapping("/resume")
	public ResponseEntity<List<ProfileDto>> getProfilesHasNoResume() {
		List<ProfileDto> profilesList = profileService.getProfilesHasNoResume();
		return new ResponseEntity<> (profilesList,HttpStatus.OK);
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
	public ResponseEntity<ResponseInfo> resumeUpload(@RequestParam("resume") MultipartFile file) {

		String message = "";
		ResponseInfo responseInfo;

		if (PDF.equals(file.getContentType()) || DOC.equals(file.getContentType())) {
			try {
				 profileService.localSave(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				responseInfo = new ResponseInfo(HttpStatus.CREATED, file.getOriginalFilename(), message);

				return new ResponseEntity<>(responseInfo, HttpStatus.CREATED);
			}
			catch(ResumeSizeLimitExceededException e) {
				message = "Maximum Upload size exceeded";
				responseInfo = new ResponseInfo(HttpStatus.EXPECTATION_FAILED, file.getOriginalFilename(), message);
				return new ResponseEntity<>(responseInfo, HttpStatus.EXPECTATION_FAILED);
			}
			catch(ResumeAlreadyExistsException e) {
				message = "file already exists";
				responseInfo = new ResponseInfo(HttpStatus.EXPECTATION_FAILED, file.getOriginalFilename(), message);
				return new ResponseEntity<>(responseInfo, HttpStatus.EXPECTATION_FAILED);
			}
			catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				responseInfo = new ResponseInfo(HttpStatus.EXPECTATION_FAILED, file.getOriginalFilename(), message);
				return new ResponseEntity<>(responseInfo, HttpStatus.EXPECTATION_FAILED);
			}
		}

		message = "Please upload file with docx or pdf format";
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
	@GetMapping("/download/{id}")
	public ResponseEntity<Object> downloadFileFromLocal(@PathVariable long id) {
		
		 ProfileDto profile = profileService.getProfileById(id);
		
		 if(profile.getPath()!=null) {
			String fileName=profile.getPath();

		Path path = Paths.get(fileName);
		String format = null;
		if(fileName.endsWith("pdf")) {
			format=PDF;
		}
		else if(fileName.endsWith("docx")) {
			format=DOC;
		}
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(format))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	else {
		throw new ResumeNotFoundException("Resume Not Available ");
	}
	}
/*---------------------SEARCH---------------------*/
	@GetMapping("/getProfilesBySearch")
	public ResponseEntity<List<ProfileDto>> getProfileBySearch(
			@RequestParam("availability") Optional<String> availability,
			@RequestParam("location") Optional<String> location,
			@RequestParam("name") Optional<String> name,
			@RequestParam("primary_skill") Optional<String> primary_skill,
			@RequestParam("proposed_by") Optional<String> proposed_by,
			@RequestParam("source") Optional<String> source
			
			){
		
		List<ProfileDto>	result =null;
		if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(name.isEmpty()) && !(primary_skill.isEmpty())
				&& !(proposed_by.isEmpty()) && !(source.isEmpty())) {
			result = profileRepo
					.getByAllFields(availability.get(), location.get(), name.get(), primary_skill.get(), proposed_by.get(), source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(name.isEmpty()) && !(primary_skill.isEmpty())
				&& !(proposed_by.isEmpty()) && !(source.isEmpty())) {
			result = profileRepo
					.getByAllFields(availability.get(), location.get(), name.get(), primary_skill.get(), proposed_by.get(), source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(name.isEmpty()) && !(primary_skill.isEmpty()) && !(source.isEmpty())) {
			result = profileRepo
					.getByAvailableLocationNameSkillSourceFields(availability.get(), location.get(), name.get(), primary_skill.get(), source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
			
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(primary_skill.isEmpty()) && !(source.isEmpty())) {
			result = profileRepo
					.getByAvailabilityAndLocationAndSkillAndSourceFields(availability.get(), location.get(), primary_skill.get(),  source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
			
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(name.isEmpty()) && !(primary_skill.isEmpty())
				&& !(proposed_by.isEmpty())) {
			result = profileRepo
					.getByAvailableLocationNameSkillProposedFields(availability.get(), location.get(), name.get(), primary_skill.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
			
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty())&& !(primary_skill.isEmpty())
				) {
			result = profileRepo
					.getByAvailabilityAndLocationAndSkillFields(availability.get(), location.get(), primary_skill.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				&& !(proposed_by.isEmpty())) {
			result = profileRepo
					.getByAvailabilityAndLocationAndProposedFields(availability.get(), location.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty())  && !(source.isEmpty())) {
			result = profileRepo
					.getByAvailabilityAndLocationAndSourceFields(availability.get(), location.get(), source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				 && !(primary_skill.isEmpty())
				&& !(proposed_by.isEmpty()) ) {
			result = profileRepo
					.getByAvailabilityAndLocationAndSkillAndProposedFields(availability.get(), location.get(), primary_skill.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
			
		}
		
		else if(!(availability.isEmpty())&& !(location.isEmpty()) 
				) {
			result = profileRepo
					.getByAvailabilityAndLocationFields(availability.get(), location.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty()) && !(primary_skill.isEmpty())
				) {
			result = profileRepo
					.getByAvailabilityAndPrimarySkillFields(availability.get(), primary_skill.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(proposed_by.isEmpty()) ) {
			result = profileRepo
					.getByAvailabilityAndProposedByFields(availability.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(availability.isEmpty())&& !(source.isEmpty())) {
			result = profileRepo
					.getByAvailabilityAndSourceFields(availability.get(), source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(location.isEmpty()) && !(primary_skill.isEmpty())
				) {
			result = profileRepo
					.getByLocationAndSkillFields(location.get(),primary_skill.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(location.isEmpty()) && !(proposed_by.isEmpty()) ) {
			result = profileRepo
					.getByLocationAndProposedByFields( location.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(location.isEmpty())  && !(source.isEmpty())) {
			result = profileRepo
					.getByLocationAndSourceFields( location.get(),  source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(name.isEmpty()) && !(primary_skill.isEmpty())) {
			result = profileRepo
					.getByNameAndSkillFields(name.get(), primary_skill.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if(!(primary_skill.isEmpty())
				&& !(proposed_by.isEmpty()) ) {
			result = profileRepo
					.getByPrimarySkillAndProposedByFields( primary_skill.get(), proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(primary_skill.isEmpty()) && !(source.isEmpty())) {
			result = profileRepo
					.getByPrimarySkillAndSourceFields( primary_skill.get(),  source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
	
		else if(!(availability.isEmpty())) {
			result = profileRepo
					.getByAvailableFields(availability.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(location.isEmpty()) ) {
			result = profileRepo
					.getByLocationFields( location.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(name.isEmpty()) ) {
			result = profileRepo
					.getByNameFields( name.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(primary_skill.isEmpty())
				) {
			result = profileRepo
					.getByPrimarySkillFields(primary_skill.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(proposed_by.isEmpty()) ) {
			result = profileRepo
					.getByProposedFields( proposed_by.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		else if( !(source.isEmpty())) {
			result = profileRepo
					.getBySourceFields( source.get())
					.stream()
					.map(profile-> modelMapper.map(profile,ProfileDto.class))
					.collect(Collectors.toList());
					
		
		}
		
		
		
		
		
		
		

		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	
}
	
	
	
