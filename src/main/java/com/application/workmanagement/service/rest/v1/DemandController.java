package com.application.workmanagement.service.rest.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.application.workmanagement.domain.repository.DemandRepository;
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
	
	@Autowired
	private DemandRepository demandRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	public ResponseEntity<List<DemandDto>> getAllDemands(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size){
		
		return ResponseEntity.ok(this.demandService.getAllDemands(page,size));
		
	}
	
	@GetMapping("/status/")
	public ResponseEntity<List<DemandDto>> getAllDemandsWithOpenAndInprogress(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10" )int size){
		
		List<DemandDto> demands =demandService.getAllDemands(page,size).stream().filter(demand -> demand.getStatus().equalsIgnoreCase("open")|| demand.getStatus().equalsIgnoreCase("in-progress")).collect(Collectors.toList());
		return new ResponseEntity<>(demands,HttpStatus.OK);
		
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
	
//	@PatchMapping("/profileslist/{Id}")
//	public ResponseEntity<ResponseInfo> addProfiles(@RequestBody DemandDto profilesList, @PathVariable("Id") int demandId){
//		
//		demandService.addProfilesToDemand(profilesList,demandId);
//		
//		responseInfo = new ResponseInfo(HttpStatus.OK,profilesList,"Profiles added to demand");
//		
//		return new ResponseEntity<>(responseInfo,HttpStatus.OK);
//	}
	
	/**
	 * profiles assigning to demand
	 * from Demand ->assign button clicked.
	 * @param profilesList
	 * @param demandId
	 * @return
	 */
	@PutMapping("/profilelist/{Id}")
	public ResponseEntity<ResponseInfo> addProfiles(@RequestBody ProfilesListDto profilesList, @PathVariable("Id") int demandId){
		
		demandService.addProfilesListToDemand(profilesList,demandId);
		
		responseInfo = new ResponseInfo(HttpStatus.OK,profilesList,"Profiles added to demand");
		
		return new ResponseEntity<>(responseInfo,HttpStatus.OK);
	}
	
	/**
	 * Profiles either to accepted or rejected by demand 
	 * 
	 * @param profilesList
	 * @param demandId
	 * @return profilesList
	 */
	@PutMapping("/profileStatus/{demandId}")
	public ResponseEntity<ResponseInfo> addProfilesStatus(@RequestBody ProfilesListDto profilesList, @PathVariable("demandId") int demandId){
		
		demandService.addProfilesListStatus(profilesList,demandId);
		
		responseInfo = new ResponseInfo(HttpStatus.OK,profilesList,"Profiles added to demand");
		
		return new ResponseEntity<>(responseInfo,HttpStatus.OK);
	}
	

	//Filtering with All values
	@GetMapping("/getByAllFields/{skill}/{status}/{startDate}/{endDate}")
	public ResponseEntity<List<DemandDto>> getDemandsByAdvanceSearch(
			@PathVariable("skill")String skill,
			@PathVariable("status")String status,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate
			)
			{
		List<DemandDto>	result = demandRepo.getByAllFields(skill,status,startDate,endDate)
				.stream()
				.map(demand-> modelMapper.map(demand,DemandDto.class))

				.collect(Collectors.toList());
		
		return new ResponseEntity<>(result , HttpStatus.OK);
		}
	
	//Filtering with Date
		@GetMapping("/getByDate/{startDate}/{endDate}")
		public ResponseEntity<List<DemandDto>> getDemandsByDateSearch(
				
				@PathVariable("startDate") String startDate,
				@PathVariable("endDate") String endDate
				)
				{
			List<DemandDto>	result = demandRepo.getByDateField(startDate,endDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
	
		//Filtering with skill values
		@GetMapping("/getBySkillField/{skill}")
		public ResponseEntity<List<DemandDto>> getDemandsBySkillSearch(
				@PathVariable("skill")String skill)
				{
			List<DemandDto>	result = demandRepo.getBySkillField(skill).stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
		//Filtering with status values
		@GetMapping("/getByStatusField/{status}")
		public ResponseEntity<List<DemandDto>> getDemandsByStatusSearch(@PathVariable("status")String status)
				{
			List<DemandDto>	result = demandRepo.getByStatusField(status).stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		//********
		//Filtering with status and skill
		@GetMapping("/getBySkillAndStatusFields/{skill}/{status}")
		public ResponseEntity<List<DemandDto>> getDemandsBySkillAndStatusSearch(
				@PathVariable("skill")String skill,
				@PathVariable("status")String status
				)
				{
			List<DemandDto>	result = demandRepo.getBySkillAndStatusFields(skill,status)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		//Filtering with Date and status
		@GetMapping("/getByStatusAndDateFields/{status}/{startDate}/{endDate}")
		public ResponseEntity<List<DemandDto>> getDemandsByStatusAndDateSearch(
				@PathVariable("status")String status,
				@PathVariable("startDate") String startDate,
				@PathVariable("endDate") String endDate
				)
				{
			List<DemandDto>	result = demandRepo.getByStatusAndDateFields(status,startDate,endDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
		//Filtering with Date and skill
		@GetMapping("/getDemandsBySkillAndDateFields/{skill}/{status}/{startDate}/{endDate}")
		public ResponseEntity<List<DemandDto>> getDemandsBySkillAndDateSearch(
				@PathVariable("skill")String skill,
				@PathVariable("startDate") String startDate,
				@PathVariable("endDate") String endDate
				)
				{
			List<DemandDto>	result = demandRepo.getDemandsBySkillAndDateFields(skill,startDate,endDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
//Filtering with first date 
		@GetMapping("/getByStartDateFields/{startDate}")
		public ResponseEntity<List<DemandDto>> getByStartDateFields(
				
				@PathVariable("startDate") String startDate
				)
				{
			List<DemandDto>	result = demandRepo.getByStartDateFields(startDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
//Filtering with firstdate ,status , skill
		@GetMapping("/getBySkillStatusStartDateFields/{skill}/{status}/{startDate}")
		public ResponseEntity<List<DemandDto>> getBySkillStatusStartDateFields(
				@PathVariable("skill")String skill,
				@PathVariable("status")String status,
				@PathVariable("startDate") String startDate
				)
				{
			List<DemandDto>	result = demandRepo.getBySkillStatusStartDateFields(skill,status,startDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
//Filtering with firstdate and status 
		@GetMapping("/getByStatusDateFields/{status}/{startDate}")
		public ResponseEntity<List<DemandDto>> getStatusAndDate(
				@PathVariable("status")String status,
				@PathVariable("startDate") String startDate
				)
				{
			List<DemandDto>	result = demandRepo.getByStatusStartDateFields(status,startDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
//Filtering with first date and skill
		@GetMapping("/getBySkillStartDateFields/{skill}/{startDate}")
		public ResponseEntity<List<DemandDto>> GetDateAndSkill(
				@PathVariable("skill")String skill,
				@PathVariable("startDate") String startDate
				)
				{
			List<DemandDto>	result = demandRepo.getBySkillStartDateFields(skill,startDate)
					.stream()
					.map(demand-> modelMapper.map(demand,DemandDto.class))

					.collect(Collectors.toList());
			
			return new ResponseEntity<>(result , HttpStatus.OK);
			}
		
		@PostMapping("/getDemandsBySearch")
        public ResponseEntity<List<DemandDto>> getDemandsBySearch(
                    @RequestParam("skill") Optional<String> skill,
                    @RequestParam("status")Optional<String> status,
                    @RequestParam("startDate") Optional<String> startDate,
                    @RequestParam("endDate") Optional<String> endDate
                    ) {
              List<DemandDto>   result =null;
              if(!(skill.isEmpty())&& !(status.isEmpty()) && !(startDate.isEmpty()) && !(endDate.isEmpty())) {
                    result = demandRepo.getByAllFields(skill.get(),status.get(),startDate.get(),endDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(status.isEmpty()) && !(startDate.isEmpty()) && !(endDate.isEmpty())) {
                    result = demandRepo.getByStatusAndDateFields(status.get(),startDate.get(),endDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty()) && !(startDate.isEmpty()) && !(endDate.isEmpty())) {
                    result = demandRepo.getDemandsBySkillAndDateFields(skill.get(),startDate.get(),endDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty())&& !(status.isEmpty()) && !(startDate.isEmpty()) ) {
                    result = demandRepo.getBySkillStatusStartDateFields(skill.get(),status.get(),startDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty())&& !(startDate.isEmpty())) {
                    result = demandRepo.getBySkillStartDateFields(skill.get(),startDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(status.isEmpty()) && !(startDate.isEmpty())) {
                    result = demandRepo.getByStatusStartDateFields(status.get(),startDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty())&& !(status.isEmpty())) {
                    result = demandRepo.getBySkillAndStatusFields(skill.get(),status.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(startDate.isEmpty()) && !(endDate.isEmpty())) {
                    result = demandRepo.getByDateField(startDate.get(),endDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty())&& !(status.isEmpty())) {
                    result = demandRepo.getBySkillAndStatusFields(skill.get(),status.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              else if(!(skill.isEmpty())) {
                    result = demandRepo.getBySkillField(skill.get()).stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
                                }
              else if(!(status.isEmpty())) {
                    result = demandRepo.getByStatusField(status.get()).stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              
              
              else if( !(startDate.isEmpty()) ) {
                    result = demandRepo.getByStartDateFields(startDate.get())
                                .stream()
                                .map(demand-> modelMapper.map(demand,DemandDto.class))

                                .collect(Collectors.toList());
              }
              
              
              

              return new ResponseEntity<>(result, HttpStatus.OK);

        }


}
	

